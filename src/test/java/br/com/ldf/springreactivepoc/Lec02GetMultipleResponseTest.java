package br.com.ldf.springreactivepoc;

import br.com.ldf.springreactivepoc.api.dto.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec02GetMultipleResponseTest extends BaseTest {

    @Autowired
    private WebClient webClient;

    @Test
    void fluxTest() {
        Flux<Response> response  = webClient.get()
                .uri("reactive-math/table/{input}", 5)
                .retrieve()
                .bodyToFlux(Response.class)
                .doOnNext(System.out::println);

        StepVerifier.create(response)
                .expectNextCount(10)
                .verifyComplete();
    }

}
