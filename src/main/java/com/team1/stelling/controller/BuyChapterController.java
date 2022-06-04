package com.team1.stelling.controller;

import com.team1.stelling.domain.vo.BuyChapterVO;
import com.team1.stelling.domain.vo.UserVO;
import com.team1.stelling.service.BuyChapterServiceImpl;
import com.team1.stelling.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/buyChapter/*")
@RequiredArgsConstructor
public class BuyChapterController {

    private final BuyChapterServiceImpl buyChapterService;
    private final UserService userService;


    @GetMapping("checkBalanceAndBuy")
    @ResponseBody
    public HashMap<String, Object> checkBalanceAndBuy(Long novelNumber, Long subNovelNumber,Long userNumber,HttpServletRequest request){
        HashMap<String, Object> status=new HashMap<>();
        HttpSession session = request.getSession();

        UserVO userVO = userService.get(userNumber);
        int userCoinBalance=userVO.getUserCoinBalance();

        if(userCoinBalance>=200){
            userVO.setUserCoinBalance(userCoinBalance-200);
            userService.modify(userVO);

            BuyChapterVO buyChapterVO = new BuyChapterVO();
            buyChapterVO.setBuyChapterCoin(200);
            buyChapterVO.setNovelNumber(novelNumber);
            buyChapterVO.setUserNumber(userNumber);
            buyChapterVO.setSubNovelNumber(subNovelNumber);
            buyChapterService.register(buyChapterVO);
            status.put("balance",userVO.getUserCoinBalance());
            status.put("msg","success");
            session.setAttribute("user",userVO);

        }else{
            status.put("balance",userVO.getUserCoinBalance());
            status.put("msg","fail");
        }

        return status;

    }



}
