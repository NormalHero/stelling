package com.team1.stelling.controller;

import com.team1.stelling.domain.dto.MyPickDTO;
import com.team1.stelling.service.MyPickService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/mypick")
public class MyPickController {

    private final MyPickService myPickService;

    @PostMapping("register")
    public void register(MyPickDTO myPickDTO) {
        myPickService.register(myPickDTO);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String exceptionHandler(MethodArgumentNotValidException e, Model model) {
        model.addAttribute("message", e.getMessage());
        return "errorPage";
    }

}
