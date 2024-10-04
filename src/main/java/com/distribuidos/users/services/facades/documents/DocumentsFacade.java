package com.distribuidos.users.services.facades.documents;

import com.distribuidos.users.config.EnvironmentConfig;
import com.distribuidos.users.exceptions.DocumentsUpstreamFailure;
import com.distribuidos.users.models.ResponseBody;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@AllArgsConstructor
public class DocumentsFacade {

    private static final ParameterizedTypeReference<ResponseBody<Boolean>> RESPONSE_TYPE_DOCUMENTS =
            new ParameterizedTypeReference<>() {
            };

    private static final String DELETE_ALL_URI = "/delete/all/%s";
    EnvironmentConfig environmentConfig;
    WebClient webClient;

    public Mono<Boolean> removeUserDocuments(String userId) {

        String resourceUri = environmentConfig.getDomains().getDocumentsDomain()
                + String.format(DELETE_ALL_URI, userId);

        return webClient
                .delete()
                .uri(resourceUri)
                .exchangeToMono(deleteResponse -> {
                    HttpStatus httpStatus = HttpStatus.valueOf(deleteResponse.statusCode().value());
                    if (HttpStatus.OK.equals(httpStatus)) {
                        return Mono.just(true);
                    }
                    return Mono.error(new DocumentsUpstreamFailure("Error deleting documents"));
                });
    }
}
