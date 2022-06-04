package com.team1.stelling.controller;

import com.team1.stelling.domain.vo.ReplyVO;
import com.team1.stelling.domain.vo.SubNovelVO;
import com.team1.stelling.domain.vo.SupportVO;
import com.team1.stelling.domain.vo.UserVO;
import com.team1.stelling.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;


@RestController
@Slf4j
@RequestMapping("/novelDetail/*")
@RequiredArgsConstructor
public class NovelDetailViewController {

    private  final NovelService novelService;
    private final UserService userService;
    private final SubNovelService subNovelService;
    private final NovelFileService novelFileService;
    private final ReplyService replyService;
    private final SupportService supportService;
    private final BuyChapterService buyChapterService;


    /*소설 회차 좋아요*/
    @GetMapping("{subNovelNumber}/{num}")
    public int subNovelLikeCounting(@PathVariable("subNovelNumber")Long subNovelNumber, @PathVariable("num") int num){
       SubNovelVO subNovelVO = subNovelService.get(subNovelNumber);
       subNovelVO.updateSubNovelLickCount(num);
       subNovelService.register(subNovelVO);
       return subNovelVO.getSubNovelLikeCount();
    }

    /*댓글 신고*/
    @GetMapping("siren")
    public String report(Long replyNumber){
        ReplyVO replyVO= replyService.get(replyNumber);
        replyVO.updatereplyReport();
        replyService.register(replyVO);
        return "신고완료";
    }

    /*후원하기*/
    @GetMapping("supporting")
    public HashMap<String, Object> supporting(Long novelNumber, Long SubNovelNumber, Long userNumber,int coin, HttpServletRequest request){
        HashMap<String, Object> status = new HashMap<>();
        UserVO userVO = userService.get(userNumber);
        int userCoinBalance=userVO.getUserCoinBalance();
        HttpSession session = request.getSession();



        if(userCoinBalance >= coin){
            userVO.setUserCoinBalance(userCoinBalance-coin);
            userService.register(userVO);

            SupportVO supportVO = new SupportVO();
            supportVO.setNovelNumber(novelNumber);
            supportVO.setSubNovelNumber(SubNovelNumber);
            supportVO.setSupportCoin(coin);
            supportVO.setUserNumber(userNumber);
            supportService.register(supportVO);

            UserVO receiver= userService.get(novelService.get(novelNumber).getUserVO().getUserNumber());
            receiver.setUserCoinBalance(receiver.getUserCoinBalance()+coin);

            status.put("balance",userService.get(userNumber).getUserCoinBalance());
            status.put("status", "success");

            session.setAttribute("user",userVO);
        }else{
            status.put("balance",userService.get(userNumber).getUserCoinBalance());
            status.put("status", "fail");
        }
    return status;
    }



}