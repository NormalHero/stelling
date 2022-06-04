package com.team1.stelling.controller;

import com.team1.stelling.aspect.annotation.LogStatus;
import com.team1.stelling.domain.dto.PageDTO;
import com.team1.stelling.domain.dto.PageableDTO;
import com.team1.stelling.domain.dto.SubNovelDeleteDTO;
import com.team1.stelling.domain.dto.SupportUserDTO;
import com.team1.stelling.domain.repository.NovelRepository;
import com.team1.stelling.domain.repository.SubNovelRepository;
import com.team1.stelling.domain.vo.*;
import com.team1.stelling.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

@Controller
@Slf4j
@RequestMapping("/novel/*")
@RequiredArgsConstructor
public class novelRoundController {
    private final SubNovelService subNovelService;
    private final NovelService novelService;
    private final UserService userService;
    private final MyPickService myPickService;
    private  final BuyChapterService buyChapterService;
    private final SupportService supportService;


    //저장된 소설 표지 가져오기
    @GetMapping("novelRegisterImg")
    @ResponseBody
    public byte[] getFile(String fileName) throws IOException{
        return FileCopyUtils.copyToByteArray(new File("/home/ubuntu/stelling/upload/" + fileName));
    }



    @GetMapping("novelRoundList")
    public void getSubNovel(Long novelNumber, @PageableDefault(page = 0, size = 10, sort = "subNovelNumber" ,direction = Sort.Direction.ASC) Pageable pageable, HttpServletRequest request, Model model){
        NovelVO novelVO = novelService.get(novelNumber);
        Page<SubNovelVO> subNovelVOS = subNovelService.getListByNovelNumber(novelNumber,pageable);
        PageableDTO pageableDTO = new PageableDTO((int)subNovelVOS.getTotalElements(), pageable);
        int listSize = subNovelService.getListByNovelNumber(novelNumber).size();
        String writerName = userService.get(novelVO.getUserVO().getUserNumber()).getUserNickName();
        HttpSession session = request.getSession();
        List<SupportVO> supportVOList= supportService.getSupportListWithNovelNumber(novelNumber);
        List<Long> userNums = supportVOList.stream().map(v->v.getUserNumber()).collect(Collectors.toList());
        List<UserVO> userVOS =new ArrayList<>();

        List<SupportUserDTO> supportUserDTOS = new ArrayList<>();
        for(Long userNumber : userNums){
            userVOS.add(userService.get(userNumber));
        }
        for (int i = 0; i<supportVOList.size(); i++){
            SupportUserDTO supportUserDTO = new SupportUserDTO();
            supportUserDTO.setCoin(supportVOList.get(i).getSupportCoin());
            supportUserDTO.setUserNickName(userVOS.get(i).getUserNickName());
            supportUserDTOS.add(supportUserDTO);
        }


        //조회수 올리기 
        List<SubNovelVO> subNovelVOList = subNovelService.getListByNovelNumber(novelNumber);
        novelVO.setNovelViewCountTotal(subNovelVOList.stream().map(v->v.getSubNovelViewCount()).mapToInt(v->v).sum());
        novelService.register(novelVO);


        if(novelService.get(novelNumber).getNovelFileName()!=null){
            //샘플이미지일 때
            if(novelService.get(novelNumber).getNovelFileName().contains("sampleImg")){
                String novelImgSrc = "/images/"+novelService.get(novelNumber).getNovelFileName();
                model.addAttribute("novelImgSrc", novelImgSrc);
            }
        }

        ArrayList<Long> sNumbers = new ArrayList<>();
        for (SubNovelVO sub : subNovelVOS){
            sNumbers.add(sub.getSubNovelNumber());
        }

        //결제한 subNovelNumber리스트
        if(session.getAttribute("userNumber")!=null){
            Long userNumber = (Long)session.getAttribute("userNumber");
            List<Long> subNumList=buyChapterService.getSubNumByNovelNum(novelNumber, userNumber);
            MyPickVO myPickVO = myPickService.getByNovelNumAndUserNum(novelNumber, userNumber);
            if(myPickVO!=null){
                int myPickStatus = myPickVO.getMyPickPick();
                model.addAttribute("myPickStatus",myPickStatus);
            }

            model.addAttribute("subNumList",subNumList);


        }


        model.addAttribute("novelNumber", novelNumber);
        model.addAttribute("subnovelVOList",subNovelVOS);
        model.addAttribute("listSize",listSize);
        model.addAttribute("novelVO", novelVO);
        model.addAttribute("pageableDTO", pageableDTO);
        model.addAttribute("writerName", writerName);
        model.addAttribute("supportUserDTOS", supportUserDTOS);

        if(sNumbers.size()!=0){
            model.addAttribute("firstSNumber",  sNumbers.get(0));
        }


    }

    @PostMapping(value = {"/deleteSubNovel"}, consumes = "application/json")
    @ResponseBody
    public String deleteSubNovel(@RequestBody SubNovelDeleteDTO subNovelDeleteDTO){

        for(Long SubNovelNumber :  subNovelDeleteDTO.getDeleteNumber()){
            subNovelService.removeSubNovelVO(SubNovelNumber);
        }
        return "삭제성공";
    }

    @GetMapping("myPick")
    @ResponseBody
    public String novelPick(int num, Long novelNumber, Long userNumber){
        if(num==1) {
            myPickService.register(MyPickVO.builder()
                    .myPickPick(num)
                    .novelVO(novelService.get(novelNumber))
                    .userVO(userService.get(userNumber))
                    .build());
            return "찜하기추가";
        }else{
            myPickService.removeMyPick(myPickService.getByNovelNumAndUserNum(novelNumber, userNumber).getMyPickNumber());
            return "찜하기삭제";
        }
    }




}
