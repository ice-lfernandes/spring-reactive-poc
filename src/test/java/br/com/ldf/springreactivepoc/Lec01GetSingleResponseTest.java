package br.com.ldf.springreactivepoc;

import br.com.ldf.springreactivepoc.api.dto.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec01GetSingleResponseTest extends BaseTest {

    @Autowired
    private WebClient webClient;

    @Test
    void blockTest() {
        var response = webClient.get()
                .uri("reactive-math/square/{input}", 5)
                .retrieve()
                .bodyToMono(Response.class)
                .block();
        System.out.println(response);
    }

    @Test
    void stepVerifierTest() {
        Mono<Response> response  = webClient.get()
                .uri("reactive-math/square/{input}", 5)
                .retrieve()
                .bodyToMono(Response.class);
        StepVerifier.create(response)
                .expectNextMatches(r -> r.getOutput() == 25)
                .verifyComplete();
    }

}
