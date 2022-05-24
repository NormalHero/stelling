package com.team1.stelling.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/main/*")
public class MainController {


    @GetMapping("index")
    public void index(){}

    //임시로 작업중인 메서드, 위 컨트롤러에 쓸 예정
    @GetMapping("/showMain")
    public void showMain(){

    }
}
