package com.team1.stelling.controller;

import com.team1.stelling.domain.dto.PageDTO;
import com.team1.stelling.domain.repository.UserRepository;
import com.team1.stelling.domain.vo.*;
import com.team1.stelling.domain.dto.PageableDTO;
import com.team1.stelling.domain.dto.PagingDTO;
import com.team1.stelling.domain.vo.*;
import com.team1.stelling.mapper.PayMapper;
import com.team1.stelling.service.PayService;
import com.team1.stelling.service.SupportService;
import com.team1.stelling.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
@Slf4j
@RequestMapping("/myLibrary/*")
@RequiredArgsConstructor
public class MyLibraryController {

    private final PayService payService;
    private final SupportService supportService;
    private final UserService userService;


    @RequestMapping("")
    public void myLibrary(){
        log.info("libaray");
    }

    // 소장함 -> 결제
    @GetMapping("myLibraryCollection")
    public String myBook(){
        log.info("myLibraryCollection");
        return "myLibrary/myLibraryCollection";
    }

    // 최근 본 작품
    @GetMapping("myLibraryRecentView")
    public String myLibraryRecentView(){
        log.info("myLibraryRecentView");
        return "myLibrary/myLibraryRecentView";
    }

    // 찜목록 -> 그냥찜
    @GetMapping("myLibraryPick")
    public String myLibraryPick(){
        log.info("myLibraryPick");
        return "myLibrary/myLibraryPick";
    }

    //코인샵 이동
    @GetMapping("coinShop")
    public String coinShop(HttpServletRequest request, Model model){
        HttpSession session = request.getSession();
        Long userNumber = (Long)session.getAttribute("userNumber");
        log.info("유저 세션 번호 : " + String.valueOf(userNumber) + "******************");
        UserVO userVO = userService.findByUserNumber(userNumber);

        model.addAttribute("userNumber", userNumber);
        model.addAttribute("userVO", userVO);
        return "cash/coinShop";
    }

    //결제 내역 등록
    @PostMapping("coinShop")
    public String register(PayVO payVO){
        payService.register(payVO);
        return "cash/coinShop";
    }

    //결제 리스트(마이페이지)
    @GetMapping("payList")
    public String payList(Paging paging, Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        Long userNumber = (Long) session.getAttribute("userNumber");
        log.info("userNumber + " + userNumber + "********");
        NumberFormat numberFormat = NumberFormat.getInstance();
        String payChargeTotal = null;
        Long result = 0L;
        if(payService.getTotal(userNumber) != null){
            result = payService.getTotal(userNumber).getChargeTotal();
        }

        payChargeTotal = numberFormat.format(result);
        log.info("총 결제 금액" + payChargeTotal);
        log.info(paging.toString());

        int total = payService.getSearchTotal(paging);

        PagingDTO pageMaker = new PagingDTO(paging, total);

//        String startDate = criteria.getStartDate();
//        String endDate = criteria.getEndDate();
//        log.info("startDate : " + startDate);
//        log.info("endDate : " + endDate);
        model.addAttribute("payList", payService.getList(paging, userNumber));
        model.addAttribute("pageMaker", pageMaker);
        model.addAttribute("payChargeTotal", payChargeTotal);
//        model.addAttribute("pageDTO", new PageDTO(paging, payService.getSearchTotal(paging)));
        return "myPage/myPagePayList";
    }

    @GetMapping("supportList")
    public String supportList(Paging paging, Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        Long userNumber = (Long) session.getAttribute("userNumber");
        NumberFormat numberFormat = NumberFormat.getInstance();
        String  supportCoinTotal = "0";
        if(supportService.getSupportCoinTotal(userNumber) != null){
            supportCoinTotal = numberFormat.format(supportService.getSupportCoinTotal(userNumber).getSupportTotal());
        }

        int total = supportService.getSearchSupportTotal(paging);

        PagingDTO pageMaker = new PagingDTO(paging, total);

        model.addAttribute("supportList", supportService.getSupportList(paging, userNumber));
        model.addAttribute("pageMaker", pageMaker);
        model.addAttribute("supportCoinTotal", supportCoinTotal);
//        model.addAttribute("pageDTO", new PageDTO(criteria, supportService.getSearchSupportTotal(criteria)));
        return "myPage/myPageSupportList";
    }


}
