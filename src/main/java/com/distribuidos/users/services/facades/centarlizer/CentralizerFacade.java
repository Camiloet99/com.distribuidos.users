package com.distribuidos.users.services.facades.centarlizer;


import com.distribuidos.users.config.EnvironmentConfig;
import com.distribuidos.users.exceptions.UnregisterCitizenException;
import com.distribuidos.users.services.facades.centarlizer.models.UnregisterCitizenRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import static com.distribuidos.users.exceptions.ErrorCodes.CENTRALIZER_UPSTREAM_ERROR;
import static reactor.core.publisher.Mono.error;
import static reactor.core.publisher.Mono.just;

@Slf4j
@Component
@AllArgsConstructor
public class CentralizerFacade {

    private final WebClient webClient;
    private final EnvironmentConfig environmentConfig;

    private static final String UNREGISTER_ROUTE = "/apis/unregisterCitizen";

    private UnregisterCitizenRequest getUnregisterCitizenRequest(Long citizenId) {
        return UnregisterCitizenRequest.builder()
                .id(citizenId)
                .operatorId(environmentConfig.getOperatorId())
                .operatorName(environmentConfig.getOperatorName())
                .build();
    }

    public Mono<Boolean> unregisterCitizen(Long citizenId) {

        val resourceUri = environmentConfig.getDomains().getCentralizerDomain() + UNREGISTER_ROUTE;
        UnregisterCitizenRequest request = getUnregisterCitizenRequest(citizenId);

        return webClient
                .method(HttpMethod.DELETE)
                .uri(resourceUri)
                .bodyValue(request)
                .exchangeToMono(unregisterCitizenResponse -> {

                    HttpStatus httpStatus = HttpStatus.valueOf(unregisterCitizenResponse.statusCode().value());

                    if (HttpStatus.CREATED.equals(httpStatus)) {
                        log.info("User successfully removed");
                        return just(true);
                    }

                    if (HttpStatus.NO_CONTENT.equals(httpStatus)) {
                        log.info("User not found on centralizer database");
                        return just(false);
                    }

                    HttpHeaders responseHeaders = unregisterCitizenResponse.headers().asHttpHeaders();
                    return unregisterCitizenResponse.bodyToMono(String.class)
                            .flatMap(responseBody -> {
                                log.error("{} - The centralizer service responded with "
                                                + "an unexpected failure response for: {}"
                                                + "\nStatus Code: {}\nResponse Headers: {}\nResponse Body: {}",
                                        CENTRALIZER_UPSTREAM_ERROR, resourceUri, httpStatus, responseHeaders,
                                        responseBody);
                                return error(new UnregisterCitizenException(responseBody));
                            });
                })
                .retryWhen(Retry
                        .max(environmentConfig.getServiceRetry().getMaxAttempts())
                        .filter(UnregisterCitizenException.class::isInstance)
                        .onRetryExhaustedThrow((ignore1, ignore2) -> ignore2.failure()));
    }

}
