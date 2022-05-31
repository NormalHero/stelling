package com.team1.stelling.controller;


import com.team1.stelling.aspect.annotation.LogStatus;
import com.team1.stelling.domain.dto.IllustProfileDTO;
import com.team1.stelling.domain.dto.PageableDTO;
import com.team1.stelling.domain.vo.IllustProfileVO;
import com.team1.stelling.domain.vo.IllustVO;
import com.team1.stelling.service.IllustProfileService;
import com.team1.stelling.service.IllustService;
import com.team1.stelling.service.UserService;
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
@RequestMapping("/illust/*")
@RequiredArgsConstructor
public class IllustController {

    private final IllustProfileService illustProfileService;
    private final IllustService illustService;
    private final UserService userService;

//  일러스트 리스트 페이지 ----------------------------
    @GetMapping("illustList")
    public void illustList(Long illustNumber, Model model, @PageableDefault(page = 0, size = 10, sort = "illustNumber" ,direction = Sort.Direction.DESC)Pageable pageable){

        List<IllustProfileDTO> profile = illustProfileService.list();
        Page<IllustVO> list = illustService.getList(pageable);
        PageableDTO pageableDTO = new PageableDTO((int) list.getTotalElements(), pageable);

        model.addAttribute("illustNumber", illustNumber);
        model.addAttribute("profile", profile);
        model.addAttribute("list", list);
        model.addAttribute("pageableDTO", pageableDTO);
    }

    @GetMapping("illustList/viewCount")
    public String illustListViewCount(Long illustNumber, Model model, @PageableDefault(page = 0, size = 10, sort = "illustViewCount" ,direction = Sort.Direction.DESC)Pageable pageable){

        List<IllustProfileDTO> profile = illustProfileService.list();
        Page<IllustVO> list = illustService.getList(pageable);
        PageableDTO pageableDTO = new PageableDTO((int) list.getTotalElements(), pageable);

        model.addAttribute("illustNumber", illustNumber);
        model.addAttribute("profile", profile);
        model.addAttribute("list", list);
        model.addAttribute("pageableDTO", pageableDTO);

        return "illust/illustListViewCount";
    }

    @GetMapping("illustList/like")
    public String illustListLike(Long illustNumber, Model model, @PageableDefault(page = 0, size = 10, sort = "illustLike" ,direction = Sort.Direction.DESC)Pageable pageable){

        List<IllustProfileDTO> profile = illustProfileService.list();
        Page<IllustVO> list = illustService.getList(pageable);
        PageableDTO pageableDTO = new PageableDTO((int) list.getTotalElements(), pageable);

        model.addAttribute("illustNumber", illustNumber);
        model.addAttribute("profile", profile);
        model.addAttribute("list", list);
        model.addAttribute("pageableDTO", pageableDTO);

        return "illust/illustListLike";
    }

    // ---------------------------------

//  채팅 페이지
    @GetMapping("illustChatPage")
    public void chatForm(){
    }

//  일러스트 카테고리
    @GetMapping("illustCategoryList")
    public String illustCategoryList(Long illustNumber, String keyword, Model model, @PageableDefault(page = 0, size = 10, sort = "illustNumber" ,direction = Sort.Direction.DESC)Pageable pageable){
        Page<IllustVO> list = illustService.CategoryList(keyword, pageable);
        PageableDTO pageableDTO = new PageableDTO( (int)list.getTotalElements(),pageable);

        model.addAttribute("illustNumber", illustNumber);
        model.addAttribute("list", list);
        model.addAttribute("keyword", keyword);
        model.addAttribute("pageableDTO", pageableDTO);

        return "illust/illustCategoryList";
    }

//  작품 등록---------------------------
    @GetMapping("illustPostingPage") public void illustList(){}

    @PostMapping("illustPostingPage")
    public RedirectView illustRegister(IllustVO illustVO,HttpServletRequest request){

        HttpSession session = request.getSession();
        Long userNumber = (Long) session.getAttribute("userNumber");
        illustVO.setUserVO(userService.get(userNumber));
        illustService.illustRegister(illustVO);

        return new RedirectView("illustList");
    }
//    ----------------------------------

//    이미지 파일 관련----------------------------------

    /*이미지 저장*/
    @PostMapping("uploadAjaxAction")
    @ResponseBody
    public List<IllustVO> uploadAjaxPost(MultipartFile[] uploadFile) {
        String uploadFolder = "/home/ubuntu/stelling/upload/";
        List<IllustVO> fileList = new ArrayList<>();

        UUID uuid = UUID.randomUUID();
        String uploadFileName = null;
        String uploadFolderPath = getPath();
        File uploadPath = new File(uploadFolder, uploadFolderPath);

        if (!uploadPath.exists()) {
            uploadPath.mkdirs();
        }

        for (MultipartFile file : uploadFile) {

            uploadFileName = uuid.toString() + "_" + file.getOriginalFilename();
            File saveFile = new File(uploadPath, uploadFileName);

            try {

                file.transferTo(saveFile);
                fileList.add(IllustVO.builder().illustFileName(uploadFileName).illustUuid(uuid.toString()).illustFilePath(uploadFolderPath).build());

            } catch (IOException e) {;}
        }
        return fileList;
    }



    /*파일저장경로(당일 날짜로)*/
    private String getPath() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date today = new Date();
        return sdf.format(today);
    }

    //저장된 이미지 가져오기
    @GetMapping("illustImg")
    @ResponseBody
    public byte[] getFile(String fileName) throws IOException{
        return FileCopyUtils.copyToByteArray(new File("/home/ubuntu/stelling/upload/" + fileName));

    }
//    ------------------------------------

//  프로필 등록 --------
    @GetMapping("illustUserInput")
    public void illustUserInput(Long userNumber, Model model, HttpServletRequest request){

        HttpSession session = request.getSession();
        session.getAttribute("userNumber");

        model.addAttribute("user", userService.get((Long) session.getAttribute("userNumber")));

    }

    @GetMapping("register") public void register(){}

    @PostMapping("register")
    public RedirectView register(IllustProfileVO illustProfileVO, HttpServletRequest request, Model model){

        HttpSession session = request.getSession();
        session.getAttribute("userNumber");

        model.addAttribute("user", userService.get((Long) session.getAttribute("userNumber")));
        illustProfileVO.setUserNumber((Long) session.getAttribute("userNumber"));


        illustProfileService.register(illustProfileVO);

        return new RedirectView("illustList");
    }
//    --------

//  유저 프로필 페이지
    @GetMapping("illustUserPage")
    public String illustUserPage(Long userNumber , Long illustNumber,  Model model, @PageableDefault(page = 0, size = 10, sort = "illustNumber" ,direction = Sort.Direction.DESC) Pageable pageable){


        Page<IllustVO> list = illustService.getUserIllustList(pageable, userNumber);
        PageableDTO pageableDTO = new PageableDTO((int) list.getTotalElements(), pageable);

        model.addAttribute("illustNumber", illustNumber);
        model.addAttribute("getLikeTotal", illustService.getLikeTotal(userNumber));
        model.addAttribute("illustProfile", illustProfileService.getProfile(userNumber));
        model.addAttribute("total", list.getTotalElements());
        model.addAttribute("pageableDTO", pageableDTO);
        model.addAttribute("list", list);

        return "illust/illustUserPage";
    }

//  작품 상세보기
    @LogStatus
    @GetMapping("illustViewDetail")
    public String read(Long illustNumber, Long userNumber, Model model, @PageableDefault(page = 0, size = 10, sort = "illustNumber" ,direction = Sort.Direction.DESC) Pageable pageable){

        List<IllustVO> list = illustService.getSixList(userNumber);

        illustService.updateViewCount(illustNumber);

        model.addAttribute("illustNumber", illustNumber);
        model.addAttribute("illust", illustService.get(illustNumber));
        model.addAttribute("total", list.size());
        model.addAttribute("list", list);

        return "illust/illustViewDetail";
    }

//  좋아요(하트)
    @ResponseBody
    @GetMapping("{illustNumber}/{num}")
    public int illustLike(@PathVariable("illustNumber")Long illustNumber, @PathVariable("num")int num){
        IllustVO illustVO = illustService.get(illustNumber);
        illustVO.updateIllustLike(num);
        illustService.illustRegister(illustVO);
        return illustVO.getIllustLike();
    }


//  프로필 유무 검사1 -> 작품 등록
    @GetMapping("illust/illustProfileCheck1")
    public String illustProfileCheck1(IllustVO illustVO,HttpServletRequest request, Model model){

        HttpSession session = request.getSession();
        session.getAttribute("userNumber");

        if( illustProfileService.checkProfile(((Long) session.getAttribute("userNumber"))) != null){

            illustVO.setUserVO(userService.get((Long) session.getAttribute("userNumber")));

            return "illust/illustPostingPage";

        } else {

            model.addAttribute("user", userService.get((Long) session.getAttribute("userNumber")));

            return "illust/illustUserInput";
        }
    }

//  프로필 유무 검사2 -> 본인 프로필 페이지
    @GetMapping("illust/illustProfileCheck2")
    public String illustProfileCheck2(Long userNumber, Long illustNumber,  Model model, @PageableDefault(page = 0, size = 10, sort = "illustNumber" ,direction = Sort.Direction.DESC) Pageable pageable, HttpServletRequest request){


        HttpSession session = request.getSession();
        session.getAttribute("userNumber");

        if(illustProfileService.checkProfile((Long) session.getAttribute("userNumber")) != null){

            Page<IllustVO> list = illustService.getUserIllustList(pageable, (Long) session.getAttribute("userNumber"));
            PageableDTO pageableDTO = new PageableDTO((int) list.getTotalElements(), pageable);


            model.addAttribute("illustNumber", illustNumber);
            model.addAttribute("getLikeTotal", illustService.getLikeTotal((long) session.getAttribute("userNumber")));
            model.addAttribute("illustProfile", illustProfileService.getProfile((Long) session.getAttribute("userNumber")));
            model.addAttribute("total", list.getTotalElements());
            model.addAttribute("pageableDTO", pageableDTO);
            model.addAttribute("list", list);

            return "illust/illustUserPage";

        } else {




            model.addAttribute("user", userService.get((Long) session.getAttribute("userNumber")));

            return "illust/illustUserInput";
        }
    }
}
