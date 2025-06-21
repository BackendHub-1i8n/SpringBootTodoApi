package org.task.springboottodoapi.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.task.springboottodoapi.model.Greeting;

import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/demo")
public class DemoController {
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/hello")
    public Greeting hello() {
        return new Greeting(counter.incrementAndGet(), "Hello " + counter.get());
    }

}
