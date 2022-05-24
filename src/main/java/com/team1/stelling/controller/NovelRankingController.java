package com.team1.stelling.controller;

import com.team1.stelling.aspect.annotation.LogStatus;
import com.team1.stelling.domain.criteria.NovelRankingCriteria;
import com.team1.stelling.domain.dao.NovelDAO;
import com.team1.stelling.domain.dto.NovelCategoryDTO;
import com.team1.stelling.domain.dto.NovelRankingDTO;
import com.team1.stelling.domain.dto.PageableDTO;
import com.team1.stelling.domain.vo.NovelVO;
import com.team1.stelling.service.NovelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
@RequestMapping("/novel/ranking/*")
@RequiredArgsConstructor
public class NovelRankingController {
    private  final  NovelService  novelService;

    @LogStatus
    @GetMapping("novelRanking")
    public void novelRanking(NovelRankingCriteria novelRankingCriteria,Model model){
        List<NovelRankingDTO> rankingList = novelService.rankingSearch(novelRankingCriteria);
        rankingList.forEach(e -> log.info("^^^^^"+e.toString()));
        log.info("@@@@"+novelRankingCriteria.toString());
        model.addAttribute("rankingList",rankingList);
        model.addAttribute( "resultTotal", rankingList.size());
        model.addAttribute( "criteria", novelRankingCriteria);

    }

    @LogStatus
    @GetMapping("novelRanking/tagSearch")
    public String novelTagSearch(NovelRankingCriteria novelRankingCriteria,Model model){
//        Page<NovelCategoryDTO> rankingList = novelService.getList(pageable);
        List<NovelRankingDTO> rankingList = novelService.rankingSearch(novelRankingCriteria);
        rankingList.forEach(e -> log.info("^^^^^"+e.toString()));
        log.info("@@@@"+novelRankingCriteria.toString());

/*        List<NovelVO> rankingList = novelService.getTop50ByTag(novelRankingCriteria.getKeyword());*/
/*
        rankingList.forEach(e -> log.info("#########"+e.toString()));
        log.info("###### total:"+ rankingList.size());
        model.addAttribute("rankingList",rankingList);
        model.addAttribute( "resultTotal", rankingList.size());
        model.addAttribute( "criteria", novelRankingCriteria);*/


//        model.addAttribute("pageableDTO",pageableDTO);
        model.addAttribute("rankingList",rankingList);
        model.addAttribute( "resultTotal", rankingList.size());
        model.addAttribute( "criteria", novelRankingCriteria);
        return "novel/ranking/novelRanking";
    }

    @LogStatus
    @GetMapping("novelRanking/rankingSearchOfDay")
    public String rankingSearchOfDay(NovelRankingCriteria novelRankingCriteria,Model model){
//        Page<NovelCategoryDTO> rankingList = novelService.getList(pageable);
        List<NovelRankingDTO> rankingList = novelService.rankingSearch(novelRankingCriteria);
        rankingList.forEach(e -> log.info("^^^^^"+e.toString()));
        log.info("@@@@"+novelRankingCriteria.toString());

        /*        List<NovelVO> rankingList = novelService.getTop50ByTag(novelRankingCriteria.getKeyword());*/
/*
        rankingList.forEach(e -> log.info("#########"+e.toString()));
        log.info("###### total:"+ rankingList.size());
        model.addAttribute("rankingList",rankingList);
        model.addAttribute( "resultTotal", rankingList.size());
        model.addAttribute( "criteria", novelRankingCriteria);*/


//        model.addAttribute("pageableDTO",pageableDTO);
        model.addAttribute("rankingList",rankingList);
        model.addAttribute( "resultTotal", rankingList.size());
        model.addAttribute( "criteria", novelRankingCriteria);
        return "novel/ranking/novelRanking";
    }

    @LogStatus
    @GetMapping("novelRanking/changeModelOfDay")
    public String changeModelOfDay(NovelRankingCriteria novelRankingCriteria,Model model){
//        Page<NovelCategoryDTO> rankingList = novelService.getList(pageable);
        log.info("changeModelOfDay!!!!");
        List<NovelRankingDTO> rankingList = novelService.rankingSearch(novelRankingCriteria);
        rankingList.forEach(e -> log.info("^^^^^"+e.toString()));
        log.info("@@@@"+novelRankingCriteria.toString());

        /*        List<NovelVO> rankingList = novelService.getTop50ByTag(novelRankingCriteria.getKeyword());*/
/*
        rankingList.forEach(e -> log.info("#########"+e.toString()));
        log.info("###### total:"+ rankingList.size());
        model.addAttribute("rankingList",rankingList);
        model.addAttribute( "resultTotal", rankingList.size());
        model.addAttribute( "criteria", novelRankingCriteria);*/


//        model.addAttribute("pageableDTO",pageableDTO);
        model.addAttribute("rankingList",rankingList);
        model.addAttribute( "resultTotal", rankingList.size());
        model.addAttribute( "criteria", novelRankingCriteria);
        return "novel/ranking/novelRankingOfDay";
    }

/*    @LogStatus
    @ResponseBody
    @PostMapping( value =  {"/changeModelOfDay"}, consumes = "application/json")
    public void modify(@RequestBody NovelRankingCriteria novelRankingCriteria,Model model){
        log.info("changeModelOfDay.........." + novelRankingCriteria.toString());
        novelRankingCriteria.setDay(-1);
        log.info("@@@@@@@@@.........." + novelRankingCriteria.toString());
        model.addAttribute( "criteria", novelRankingCriteria);
    }*/

}
