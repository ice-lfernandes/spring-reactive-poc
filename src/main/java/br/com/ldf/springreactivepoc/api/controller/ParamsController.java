package br.com.ldf.springreactivepoc.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/jobs")
@RequiredArgsConstructor
public class ParamsController {

    @GetMapping("/search")
    public Flux<Integer> searchJobs(@RequestParam("count") int count, @RequestParam("page") int page) {
        return Flux.just(count, page);
    }
}
