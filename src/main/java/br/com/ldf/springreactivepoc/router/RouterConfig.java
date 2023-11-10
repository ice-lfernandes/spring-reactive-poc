package br.com.ldf.springreactivepoc.router;

import br.com.ldf.springreactivepoc.api.dto.error.InputValidationErrorResponse;
import br.com.ldf.springreactivepoc.exception.InputValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@Configuration
@RequiredArgsConstructor
public class RouterConfig {

    private final RequestHandler requestHandler;

    /**
     * Criando router config para criação de endpoint dinamicamente ao inves de usar anotações
     */
    public RouterFunction<ServerResponse> serverResponseRouterFunction() {
        return RouterFunctions.route()
                // Expondo endpoint @GetMapping
                .GET("square/{input}", RequestPredicates.path("*/1?"), requestHandler::squareHandler)
                .GET("square/{input}", request -> ServerResponse.badRequest().bodyValue("Invalid input"))
                .GET("table/{input}", requestHandler::tableHandler)
                .GET("table/{input}/stream", requestHandler::tableStreamHandler)
                .POST("multiply", requestHandler::multiplyHandler)
                .onError(InputValidationException.class, exceptionHandler())
                .build();
    }


    /**
     * Agrupando router functions
     */
    @Bean
    public RouterFunction<ServerResponse> highLevelRouter() {
        return RouterFunctions.route()
                .path("router", this::serverResponseRouterFunction)
                .build();
    }

    /**
     * Similar ao @RestControllerAdvice
     */
    private BiFunction<Throwable, ServerRequest, Mono<ServerResponse>> exceptionHandler() {
        return (error, req) -> {
            InputValidationException exception = (InputValidationException) error;
            var inputValidationErrorResponse = new InputValidationErrorResponse(exception.getErrorCode(), exception.getInput(), exception.getMessage());
            return ServerResponse.badRequest().bodyValue(inputValidationErrorResponse);
        };
    }

}
