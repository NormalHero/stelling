package com.team1.stelling.controller;

import com.team1.stelling.domain.dao.SupportDAO;
import com.team1.stelling.domain.vo.SupportVO;
import com.team1.stelling.service.SupportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/support/*")
@RequiredArgsConstructor
public class SupportController {
    private final SupportService supportService;

    @GetMapping("getSupportListWithNovelNumber")
    public List<SupportVO> getSupportListWithNovelNumber(Long novelNumber){
        List<SupportVO> supportVOList=supportService.getSupportListWithNovelNumber(novelNumber);

        return supportVOList;
    }


}
