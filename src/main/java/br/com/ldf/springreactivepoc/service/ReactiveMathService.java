package br.com.ldf.springreactivepoc.service;

import br.com.ldf.springreactivepoc.api.dto.MultiplyRequestDTO;
import br.com.ldf.springreactivepoc.api.dto.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
@Slf4j
public class ReactiveMathService {

    public Mono<Response> findSquare(int input) {
        return Mono.fromSupplier(() -> input * input)
                .map(Response::new);
    }

    public Flux<Response> multiplicationTable(int input) {
        return Flux.range(1, 10)
                .delayElements(Duration.ofSeconds(5))
                .doOnNext(i -> log.info("reactive-math-service processing : {}", i))
                .map(i -> new Response(i * input));
    }

    public Mono<Response> multiply(Mono<MultiplyRequestDTO> requestDTOMono) {
        return requestDTOMono
                .map(dto -> dto.getFirst() * dto.getSecond())
                .map(Response::new);
    }

}
