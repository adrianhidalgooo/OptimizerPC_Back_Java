package com.optimizerpc.api.controller;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusController {

    @GetMapping("/")
    public Map<String, String> home() {
        return Map.of(
                "name", "Optimizer PC Java API",
                "status", "esto va perfecccto"
        );
    }
}