package com.team1.stelling.controller;

import com.team1.stelling.domain.dto.PageDTO;
import com.team1.stelling.domain.vo.Criteria;
import com.team1.stelling.domain.vo.PayVO;
import com.team1.stelling.service.PayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/myLibrary/*")
@RequiredArgsConstructor
public class MyLibraryController {

    private final PayService payService;

    @RequestMapping("")
    public void myLibrary(){
        log.info("libaray");
    }

    // 소장함 -> 결제
    @GetMapping("/myLibraryCollection")
    public String myBook(){
        log.info("myLibraryCollection");
        return "myLibrary/myLibraryCollection";
    }

    // 최근 본 작품
    @GetMapping("/myLibraryRecentView")
    public String myLibraryRecentView(){
        log.info("myLibraryRecentView");
        return "myLibrary/myLibraryRecentView";
    }
    
    // 찜목록 -> 그냥찜
    @GetMapping("/myLibraryPick")
    public String myLibraryPick(){
        log.info("myLibraryPick");
        return "myLibrary/myLibraryPick";
    }

    @GetMapping("/coinShop")
    public String coinShop(){
        log.info("coinShop");
        return "cash/coinShop";
    }

    //결제 내역 등록
    @PostMapping("/coinShop")
    public String register(PayVO payVO){
        payService.register(payVO);
        return "cash/coinShop";
    }

    //결제 리스트(마이페이지)
    @GetMapping("/payList")
    public String payList(Long userNumber, Criteria criteria, Model model){
            model.addAttribute("payList", payService.getList(criteria, userNumber));
            model.addAttribute("pageDTO", new PageDTO(criteria, payService.getSearchTotal(criteria)));
            model.addAttribute("payDTO", payService.getTotal(userNumber));
        return "myPage/myPagePayList";
    }

//    @GetMapping("/payList/{userNumber}")
//    public String payList(@PathVariable Long userNumber, String startDate, String endDate, Model model){
//        if(startDate == null & endDate == null) {
//            model.addAttribute("payList", payService.getList(userNumber));
//        }
//        return "myPage/myPagePayList";
//    }
    //날짜 검색(마이페이지)
//    @GetMapping("/dateSelect")
//    public void dateSelect(String startDate, String endDate)
    //결제 내역 등록
//    @PostMapping("/register/{userNumber}/{payCharge}/{payCoinCount}")
//    public String register(@PathVariable Long userNumber, @PathVariable Long payCharge, @PathVariable Long payCoinCount){
//        payService.register(userNumber, payCharge, payCoinCount);
//        return "cash/coinShop";
//    }

}
