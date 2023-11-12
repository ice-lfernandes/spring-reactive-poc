package br.com.ldf.springreactivepoc;

import br.com.ldf.springreactivepoc.api.dto.request.MultiplyRequestDTO;
import br.com.ldf.springreactivepoc.api.dto.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

public class Lec04HeadersTest extends BaseTest {

    @Autowired
    private WebClient webClient;

    @Test
    void headersTest() {
        var response = webClient.post()
                .uri("reactive-math/multiply")
                .bodyValue(buildRequest(5, 10))
                .headers(h -> h.set("someKey", "someValue"))
                .retrieve()
                .bodyToMono(Response.class)
                .doOnNext(System.out::println);

        StepVerifier.create(response)
                .expectNextMatches(r -> r.getOutput() == 50)
                .verifyComplete();
    }

    private MultiplyRequestDTO buildRequest(int number1, int number2) {
        MultiplyRequestDTO request = new MultiplyRequestDTO();
        request.setFirst(number1);
        request.setSecond(number2);
        return request;
    }

}
