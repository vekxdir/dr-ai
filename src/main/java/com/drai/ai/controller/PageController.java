package com.drai.ai.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/chat")
    public String chatPage() {
        return "chat"; // loads chat.html
    }

    @GetMapping("/symptom-checker")
    public String symptomChecker() { return "symptom-checker"; }

}
