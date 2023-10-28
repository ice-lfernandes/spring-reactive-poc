package br.com.ldf.springreactivepoc.api.controller;

import br.com.ldf.springreactivepoc.api.dto.response.Response;
import br.com.ldf.springreactivepoc.service.MathService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/math")
@RequiredArgsConstructor
public class MathController {

    final MathService mathService;

    @GetMapping(path = "/square/{input}")
    public Response findSquare(@PathVariable int input) {
        return mathService.findSquare(input);
    }

    @GetMapping(path = "/table/{input}")
    public List<Response> multiplicationTable(@PathVariable int input) {
        return mathService.multiplicationTable(input);
    }
}
