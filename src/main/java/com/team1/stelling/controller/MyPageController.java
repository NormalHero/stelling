package com.team1.stelling.controller;

import com.team1.stelling.domain.dto.PageableDTO;
import com.team1.stelling.domain.repository.NovelRepository;
import com.team1.stelling.domain.repository.UserRepository;
import com.team1.stelling.domain.vo.*;
import com.team1.stelling.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/myPage/*")
public class MyPageController {
    private final UserRepository userRepository;
    private final InquiryService inquiryService;
    private final NovelService novelService;
    private final NovelRepository novelRepository;


    //프로필수정
    @PostMapping("myPageEditProfile")
    public void modify(UserVO userVO, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long userNumber = (Long) session.getAttribute("userNumber");

        UserVO userProfile = userRepository.findById(userNumber).get();
        userProfile.setUserFileName(userVO.getUserFileName());
        userProfile.setUserFilePath(userVO.getUserFilePath());
        userProfile.setUserUuid(userVO.getUserUuid());
        userProfile.setUserEmail(userVO.getUserEmail());
        userProfile.setUserNickName(userVO.getUserNickName());
        userProfile.setUserPhoneNum(userVO.getUserPhoneNum());
        userRepository.save(userProfile);


        model.addAttribute("userProfile",userProfile);
        model.addAttribute("userVO", userProfile);
    }

    @GetMapping("myPageEditProfile")
    public String myPageEditProfile(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long userNumber = (Long) session.getAttribute("userNumber");
        UserVO sessionUser = userRepository.findById(userNumber).get();
        model.addAttribute("userVO", sessionUser);
        return "myPage/myPageEditProfile";
    }


    //---------


    //내 작품 관리
    @GetMapping("myPageMyWork")
    public String myPageMyWork(Model model, HttpServletRequest request, @PageableDefault(page = 0, size = 10, sort = "novelNumber", direction = Sort.Direction.DESC) Pageable pageable) {
        HttpSession session = request.getSession();
        Long userNumber = (Long) session.getAttribute("userNumber");
        NovelVO novelVO = novelRepository.getById(userNumber);

        Page<NovelVO> list = novelService.getPageList(userNumber, pageable);
        for (NovelVO novel : list) {
            if (novel.getNovelFileName().contains("sampleImg")) {
                String novelImgSrc = "/images/" + novel.getNovelFileName();
                novel.setNovelFileName(novelImgSrc);
            }
        }


        UserVO uservo = userRepository.findById(userNumber).get();
        PageableDTO pageableDTO = new PageableDTO((int) list.getTotalElements(), pageable);

        model.addAttribute("novelVO", novelVO);
        model.addAttribute("list", list);
        model.addAttribute("pageableDTO", pageableDTO);
        model.addAttribute("userVO", uservo);
        return "myPage/myPageMyWork";
    }
    //--------------------

    //비밀번호 변경----------------
    @GetMapping("myPageChangePw")
    public void myPageChangePw() {
        log.info("myPageChangePw");
    }


    @PostMapping("pwChangeForm")
    public RedirectView pwChangeForm(HttpServletRequest request, String userNewPw) {
        HttpSession session = request.getSession();
        Long userNumber = (Long) session.getAttribute("userNumber");
        UserVO uservo = userRepository.findById(userNumber).get();
        uservo.setUserPw(userNewPw);
        userRepository.save(uservo);
        return new RedirectView("myPage/myPageEditProfile/");
    }


    @PostMapping("pwCheck")
    @ResponseBody
    public String pwCheck(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long userNumber = (Long) session.getAttribute("userNumber");
        String userPw = userRepository.findById(userNumber).get().getUserPw();
        return userPw;
    }

    //--------------------------------
    //페이징-문의내역
    @GetMapping("myPageQuestion")
    public String myPageQuestion(Model model, HttpServletRequest request, @PageableDefault(page = 0, size = 10, sort = "inquiryNumber", direction = Sort.Direction.DESC) Pageable pageable) {
        HttpSession session = request.getSession();
        Long userNumber = (Long) session.getAttribute("userNumber");

        Page<InquiryVO> list = inquiryService.getPageList(pageable, userNumber);
        PageableDTO pageableDTO = new PageableDTO((int) list.getTotalElements(), pageable);
        model.addAttribute("list", list);
        model.addAttribute("pageableDTO", pageableDTO);
        return "myPage/myPageQuestion";
    }

    //-----------------
    //탈퇴(status 1->0으로 변경)
    @GetMapping("withDraw")
    public String withDraw(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long userNumber = (Long) session.getAttribute("userNumber");

        UserVO uservo = userRepository.findById(userNumber).get();
        uservo.setUserStatus(0);
        userRepository.save(uservo);
        return "main/index";
    }

    @GetMapping("myPageQuit")
    public void myPageQuit() {
        log.info("myPageQuit");
    }
    //------------

    @GetMapping("myPagePayList")
    public String myPagePayList() {
        log.info("myPagePayList");
        return "myPage/myPagePayList";
    }


    //파일 업로드
    @PostMapping("uploadAjaxAction")
    @ResponseBody //비동기를 처리하는 경우
    public List<UserVO> uploadAjaxPost(MultipartFile[] uploadFile) {
        String uploadFolder = "/home/ubuntu/stelling/upload/";
        List<UserVO> fileList = new ArrayList<>();

        UUID uuid = UUID.randomUUID();
        String uploadFileName = null;

        String uploadFolderPath = getPath();
        File uploadPath = new File(uploadFolder, uploadFolderPath);
        if (!uploadPath.exists()) {
            uploadPath.mkdirs();
        }
        for (MultipartFile file : uploadFile) {
            log.info("-------------------------");
            log.info("Upload File Name : " + file.getOriginalFilename());
            log.info("Upload File Size : " + file.getSize());

            UserVO userVO = new UserVO();

            uploadFileName = uuid.toString() + "_" + file.getOriginalFilename();

            userVO.setUserFileName(file.getOriginalFilename());
            userVO.setUserUuid(uuid.toString());
            userVO.setUserFilePath(uploadFolderPath);

            //저장할 경로와 파일의 이름을 File객체에 담는다.
            File saveFile = new File(uploadPath, uploadFileName);

            try {
                //설정한 경로에 해당 파일을 업로드한다.
                file.transferTo(saveFile);


                fileList.add(userVO);
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
        log.info("----------------------------------------------------------");
        log.info(fileList.toString());
        log.info("----------------------------------------------------------");
        return fileList;
    }

    //이미지 보여주기
    @GetMapping("display")
    @ResponseBody
    public byte[] getFile(String fileName) throws IOException {
        return FileCopyUtils.copyToByteArray(new File("/home/ubuntu/stelling/upload/" + fileName));
    }


    private String getPath() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date today = new Date();
        return sdf.format(today);
    }
}