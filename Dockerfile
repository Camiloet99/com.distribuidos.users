FROM openjdk:17-jdk-slim

WORKDIR /app

COPY ./target/users-1.0.0.jar /app

EXPOSE 8080

CMD ["java", "-jar", "users-1.0.0.jar", "--spring.main.class=com.distribuidos.users.Application"]
