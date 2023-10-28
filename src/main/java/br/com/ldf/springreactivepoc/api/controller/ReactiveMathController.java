package br.com.ldf.springreactivepoc.api.controller;

import br.com.ldf.springreactivepoc.api.dto.request.MultiplyRequestDTO;
import br.com.ldf.springreactivepoc.api.dto.response.Response;
import br.com.ldf.springreactivepoc.exception.InputValidationException;
import br.com.ldf.springreactivepoc.service.ReactiveMathService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/reactive-math")
@RequiredArgsConstructor
public class ReactiveMathController {

    final ReactiveMathService reactiveMathService;

    @GetMapping(path = "/square/{input}")
    public Mono<Response> findSquare(@PathVariable int input) {
        return reactiveMathService.findSquare(input);
    }

    @GetMapping(path = "/table/{input}")
    public Flux<Response> multiplicationTable(@PathVariable int input) {
        return reactiveMathService.multiplicationTable(input);
    }

    // produces ncessário para client receber a cada item emitido
    @GetMapping(path = "/table/{input}/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Response> multiplicationTableStream(@PathVariable int input) {
        return reactiveMathService.multiplicationTable(input);
    }

    // Bom padrão de reativo é: objetos complexos (não primitivos) devem ser informados como Mono ou Flux
    @PostMapping(path = "/multiply")
    public Mono<Response> multiply(@RequestBody Mono<MultiplyRequestDTO> request) {
        return reactiveMathService.multiply(request);
    }

    @GetMapping(path = "/square/{input}/throw")
    public Mono<Response> findSquareThrow(@PathVariable int input) {
        return Mono.just(input)
                .handle(((integer, sink) -> {
                    if (integer >= 10 && integer <= 20)
                        sink.next(integer);
                    else
                        sink.error(new InputValidationException(integer));
                }))
                .cast(Integer.class)
                .flatMap(this.reactiveMathService::findSquare);
    }
}
