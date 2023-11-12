package br.com.ldf.springreactivepoc;

import br.com.ldf.springreactivepoc.api.dto.response.Response;
import br.com.ldf.springreactivepoc.exception.InputValidationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec06ExchangeTest extends BaseTest {

    @Autowired
    private WebClient webClient;

    @Test
    void badRequestTest() {
        Mono<Object> response = webClient.get()
                .uri("reactive-math/square/{input}/throw", 5)
                .exchangeToMono(this::exchange) // exchange = retriave + aditional info http status code
                .doOnNext(System.out::println)
                .doOnError(error -> System.out.println(error.getMessage()));

        StepVerifier.create(response)
                .expectNextCount(1)
                .verifyComplete();
    }

    private Mono<Object> exchange(ClientResponse clientResponse) {
        if (clientResponse.statusCode().value() == 400) {
            return clientResponse.bodyToMono(InputValidationException.class);
        } else {
            return clientResponse.bodyToMono(Response.class);
        }
    }

}
