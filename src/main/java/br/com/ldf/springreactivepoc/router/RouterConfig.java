package br.com.ldf.springreactivepoc.router;

import br.com.ldf.springreactivepoc.api.dto.error.InputValidationErrorResponse;
import br.com.ldf.springreactivepoc.exception.InputValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@Configuration
@RequiredArgsConstructor
public class RouterConfig {

    private final RequestHandler requestHandler;

    /**
     * Criando router config para criação de endpoint dinamicamente ao inves de usar anotações
     */
    @Bean
    public RouterFunction<ServerResponse> serverResponseRouterFunction() {
        return RouterFunctions.route()
                // Expondo endpoint @GetMapping
                .GET("router/square/{input}", requestHandler::squareHandler)
                .GET("router/table/{input}", requestHandler::tableHandler)
                .GET("router/table/{input}/stream", requestHandler::tableStreamHandler)
                .POST("router/multiply", requestHandler::multiplyHandler)
                .GET("router/square/{input}/validation", requestHandler::squareHandlerWithValidation)
                .onError(InputValidationException.class, exceptionHandler())
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
