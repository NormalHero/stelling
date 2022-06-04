package com.team1.stelling.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/etc/*")
@RequiredArgsConstructor
public class EtcController {

    @GetMapping("agree")
    public void agree(){
    }

    @GetMapping("privacy")
    public void privacy(){
    }
}
