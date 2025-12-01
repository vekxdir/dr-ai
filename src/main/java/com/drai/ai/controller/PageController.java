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
    public String symptomChecker() {
        return "symptom-checker";
    }

    @GetMapping("/blood-checker")
    public String bloodChecker() {
        return "blood-checker";
    }

    @GetMapping("/diet")
    public String diet() {
        return "diet";
    }

    @GetMapping("/report-analyser")
    public String reportAnalyser() {
        return "report-analyser";
    }

    @GetMapping("/hospitals")
    public String hospitals() {
        return "hospitals";
    }

    @GetMapping("/first-aid")
    public String firstAid() {
        return "first-aid";
    }

    @GetMapping("/zigzag")
    public String zigzag() {
        return "zigzag";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

}
