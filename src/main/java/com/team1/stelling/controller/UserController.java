package com.team1.stelling.controller;

import com.team1.stelling.domain.vo.UserVO;
import com.team1.stelling.service.CertifiedPhoneService;
import com.team1.stelling.service.SendEmailService;
import com.team1.stelling.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@Slf4j
@RequestMapping("user/*")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final CertifiedPhoneService certifiedPhoneService;
    private final SendEmailService sendEmailService;
    private final BCryptPasswordEncoder passwordEncoder;

    @GetMapping("findId")
    public String findId() {
        return "user/userFindId";
    }

    @GetMapping("userLoginFail")
    public String loginFail() {
        return "user/userLoginFail";
    }

    @GetMapping("findPw")
    public String findPw() {
        return "user/userFindPw";
    }

    @GetMapping("join")
    public String join() {
        return "user/userJoin";
    }

    @GetMapping("login")
    public String login() {
        return "user/userLogin";
    }

    @GetMapping("agree")
    public String agree() {
        return "etc/agree";
    }

    @GetMapping("privacy")
    public String privacy() {
        return "etc/privacy";
    }

    @PostMapping("joinUs.do")
    public String joinUs(UserVO vo) {
        userService.joinUser(vo);
        return "user/userLogin";
    }

    @GetMapping("logOut")
    public String logOut(HttpServletRequest request){
        HttpSession session = request.getSession();
        if(session != null){
            session.removeAttribute("userNumber");
            session.removeAttribute("user");
        }
        return "redirect:/main/index";
    }


    @PostMapping("loginCheck")
    public String login(HttpServletRequest request, String userId, String userPw) {
        UserVO userVO = userService.getByUserId(userId);
        int idCheck = userService.idCheck(userId);

        if (idCheck == 0) {
            return "user/userLoginFail";
        }

        if (!passwordEncoder.matches(userPw, userVO.getUserPw())) {
            return "user/userLoginFail";
        }else {
            HttpSession session = request.getSession();
            session.setAttribute("userNumber", userVO.getUserNumber());
            session.setAttribute("user",userService.get(userVO.getUserNumber()));
            return "redirect:/main/index";
        }
    }

    @ResponseBody
    @PostMapping("idCheck")
    public int overlappedID(String userId){
        int count = 0;
        count = userService.idCheck(userId);
        return count;
    }

    @ResponseBody
    @PostMapping("emailCheck")
    public int overlappedEmail(String userEmail){
        int count = 0;
        count = userService.emailCheck(userEmail);
        return count;
    }
    @GetMapping("sendSMS")
    public @ResponseBody String sendSMS (String phoneNumber) {
        Random rand  = new Random();
        String numStr = "";

        for(int i=0; i<4; i++) {
            String ran = Integer.toString(rand.nextInt(10));
            numStr += ran;
        }
        certifiedPhoneService.certifiedPhoneNumber(phoneNumber,numStr);
        return numStr;
    }

    @PostMapping("userFindId")
    @ResponseBody
    public String userFindId(String userNick, String phoneNum){
        String result = userService.getSearchId(userNick, phoneNum);
        return result;
    }

    @PostMapping("userFindPw")
    @ResponseBody
    public String sendEmail(String userId, String userEmail){
        String result = userService.findPw(userId, userEmail);
        if (result != "") {
            sendEmailService.sendEmail(result, userEmail);
            return result;
        }
        return result;
    }
}