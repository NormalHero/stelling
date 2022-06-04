package com.team1.stelling.controller;

import com.team1.stelling.domain.criteria.MainCriteria;
import com.team1.stelling.domain.dto.NewIllustDTO;
import com.team1.stelling.domain.dto.NovelDTO;
import com.team1.stelling.domain.dto.NovelRankingDTO;
import com.team1.stelling.domain.vo.Criteria;
import com.team1.stelling.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/main/*")
@RequiredArgsConstructor
public class MainController {

    private final MainService mainService;

    @GetMapping("bannerImg")
    @ResponseBody
    public List<NovelDTO> getBannerImg(){
        List<NovelDTO> novelDTO = mainService.getBannerImg();

        return novelDTO;
    }

    @GetMapping("illustImg")
    @ResponseBody
    public List<NewIllustDTO> getIllustImg(){
        List<NewIllustDTO> illustDTO = mainService.getNewIllustList();

        return illustDTO;
    }

    @GetMapping("index")
    public void index(Model model, MainCriteria mainCriteria){
        //소설 신작
        List<NovelDTO> newWorkList = mainService.getNewNovelList();
        log.info("********신작 리스트 : " + newWorkList.toString());
        model.addAttribute("newWorkList", newWorkList);

        //일러스트 신작
        List<NewIllustDTO> newIllustList = mainService.getNewIllustList();
        model.addAttribute("newIllustList", newIllustList);

        //완결 소설 리스트
        List<NovelDTO> endNovelList = mainService.getEndNovelList();
        model.addAttribute("endNovelList", endNovelList);

        //실시간 조회수 높은 작품
        List<NovelRankingDTO> viewCountList = mainService.getViewCountSearch(mainCriteria);
        model.addAttribute("viewCountList", viewCountList);

        //실시간 좋아요 수 높은 작품
        List<NovelRankingDTO> likeCountList = mainService.getLikeCountSearch(mainCriteria);
        model.addAttribute("likeCountList", likeCountList);

        //실시간 회차수 높은 작품
        List<NovelRankingDTO> roundCountList = mainService.getRoundCountSearch(mainCriteria);
        model.addAttribute("roundCountList", roundCountList);

        model.addAttribute("mainCriteria", mainCriteria);
    }

    @GetMapping("display")
    public ResponseEntity<byte[]> getImage(String fileName){

    File file = new File("c:\\stelling\\" + fileName);

    log.info("getImage()..........." + fileName);

    ResponseEntity<byte[]> result = null;

        try {
            HttpHeaders header = new HttpHeaders();
            header.add("Content-type", Files.probeContentType(file.toPath()));
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
