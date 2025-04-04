package com.joaopereira.renda_organziada.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/status")
public class StatusController {

    @GetMapping({"/", ""})
    public ResponseEntity<String> getStatus() {
        return ResponseEntity.ok("Server is running");
    }

}
