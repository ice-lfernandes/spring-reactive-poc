package br.com.ldf.springreactivepoc.router;

import br.com.ldf.springreactivepoc.api.dto.error.InputValidationErrorResponse;
import br.com.ldf.springreactivepoc.api.dto.request.MultiplyRequestDTO;
import br.com.ldf.springreactivepoc.api.dto.response.Response;
import br.com.ldf.springreactivepoc.exception.InputValidationException;
import br.com.ldf.springreactivepoc.service.ReactiveMathService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class RequestHandler {

    private final ReactiveMathService reactiveMathService;

    public Mono<ServerResponse> squareHandler(ServerRequest serverRequest) {
        var input = Integer.parseInt(serverRequest.pathVariable("input"));
        Mono<Response> responseMono = reactiveMathService.findSquare(input);
        return ServerResponse.ok().body(responseMono, Response.class);
    }

    public Mono<ServerResponse> tableHandler(ServerRequest serverRequest) {
        var input = Integer.parseInt(serverRequest.pathVariable("input"));
        Flux<Response> responseFlux = reactiveMathService.multiplicationTable(input);
        return ServerResponse.ok().body(responseFlux, Response.class);
    }

    public Mono<ServerResponse> tableStreamHandler(ServerRequest serverRequest) {
        var input = Integer.parseInt(serverRequest.pathVariable("input"));
        Flux<Response> responseFlux = reactiveMathService.multiplicationTable(input);
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(responseFlux, Response.class);
    }

    public Mono<ServerResponse> multiplyHandler(ServerRequest serverRequest) {
        var body = serverRequest.bodyToMono(MultiplyRequestDTO.class);
        Mono<Response> responseMono = reactiveMathService.multiply(body);
        return ServerResponse.ok().body(responseMono, Response.class);
    }

    public Mono<ServerResponse> squareHandlerWithValidation(ServerRequest serverRequest) {
        var input = Integer.parseInt(serverRequest.pathVariable("input"));
        if (input < 10 || input > 20) {
            return Mono.error(new InputValidationException((input)));
        }
        Mono<Response> responseMono = reactiveMathService.findSquare(input);
        return ServerResponse.ok().body(responseMono, Response.class);
    }
}

