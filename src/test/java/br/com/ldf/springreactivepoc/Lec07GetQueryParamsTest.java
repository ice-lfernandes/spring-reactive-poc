package br.com.ldf.springreactivepoc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class Lec07GetQueryParamsTest extends BaseTest {

    @Autowired
    private WebClient webClient;

    @Test
    void queryParamsTest() {
        Flux<Integer> integerFlux = webClient.get()
                .uri(p -> p.path("jobs/search").query("count={c}&page={p}").build(10, 20))
                .retrieve()
                .bodyToFlux(Integer.class)
                .doOnNext(System.out::println);

        StepVerifier.create(integerFlux)
                .expectNextCount(2)
                .verifyComplete();
    }


}
