package me.hyunjung.controller;

import ch.qos.logback.core.model.Model;
import org.springframework.web.bind.annotation.GetMapping;

public class ExampleController {
    @GetMapping("/thymeleaf/example")
    public String thymeleafExample(Model model) {
        Person examplePerson = new Person();

        return "example";
    }
}
