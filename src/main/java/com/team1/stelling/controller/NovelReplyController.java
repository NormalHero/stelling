package com.team1.stelling.controller;


import com.team1.stelling.domain.dto.PageableDTO;
import com.team1.stelling.domain.dto.ReplyDTO;
import com.team1.stelling.domain.dto.ReplyListDTO;
import com.team1.stelling.domain.dto.ReplyUserDTO;
import com.team1.stelling.domain.vo.*;
import com.team1.stelling.service.NovelService;
import com.team1.stelling.service.ReplyService;
import com.team1.stelling.service.SubNovelService;
import com.team1.stelling.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/reply/*")
@RequiredArgsConstructor
public class NovelReplyController {
    private final ReplyService replyService;
    private final UserService userService;
    private final NovelService novelService;
    private final SubNovelService subNovelService;


    //댓글 추가하기
    @PostMapping(value = "/add", consumes = "application/json", produces = "text/plain; charset=utf-8")
    public String replyRegister(@RequestBody ReplyDTO replyDTO){
        replyService.register(ReplyVO.builder().novelVO(novelService.get(replyDTO.getNovelNumber()))
                .subNovelVO(subNovelService.get(replyDTO.getSubNovelNumber()))
                .userVO(userService.get(replyDTO.getUserNumber()))
                .replyContent(replyDTO.getReplyContent()).build());
        return "등록완료";
    }

    //reply+user 가져오기
    @Transactional
    @GetMapping("list/{subNovelNumber}")
    public ReplyListDTO replygetList(@PathVariable("subNovelNumber") Long subNovelNumber){
        return new ReplyListDTO(replyService.getList(subNovelNumber),replyService.getUserList(subNovelNumber));
    }

    //소설 회차 번호로 댓글 가져오기
    @GetMapping("repliesTest/{subNovelNumber}")
    public List<ReplyVO> getList(@PathVariable Long subNovelNumber){
        return replyService.getList(subNovelNumber);
    }

    //댓글 좋아요 기능
    @GetMapping("{replyNum}/{num}")
    public void replyUp(@PathVariable("replyNum") Long replyNum, @PathVariable("num")int num){
        ReplyVO replyVO = replyService.get(replyNum);
        replyVO.updateReplyUp(num);
        replyService.register(replyVO);
    }


    //댓글 최신순
    @GetMapping("getReplyUserDTO")
    public ReplyUserDTO getreplys(Long novelNumber, @PageableDefault(page =0 ,size =5 ,sort ="replyUploadDate" ,direction = Sort.Direction.DESC) Pageable pageable){
        Page<ReplyVO> replyVOS = replyService.getReplyListByNovelNumber(novelNumber,pageable);
        Page<UserVO> userVOS = replyVOS.map(v->v.getUserVO());
        ReplyUserDTO replyUserDTO= new ReplyUserDTO(replyVOS, userVOS, pageable);
        return replyUserDTO;
    }

    //댓글 추천순
    @GetMapping("getReplyUpUserDTO")
    public ReplyUserDTO getreplyUps(Long novelNumber, @PageableDefault(page =0 ,size =5 ,sort ="replyUp" ,direction = Sort.Direction.DESC) Pageable pageable){
        Page<ReplyVO> replyVOS = replyService.getReplyListByNovelNumber(novelNumber,pageable);
        Page<UserVO> userVOS = replyVOS.map(v->v.getUserVO());
        ReplyUserDTO replyUserDTO= new ReplyUserDTO(replyVOS, userVOS, pageable);
        return replyUserDTO;
    }

    //소설번호로 가져온 댓글 최신순
    @GetMapping("getReplyUserDTOBySubNum")
    public ReplyUserDTO getReplyUserDTOBySubNum(Long subNovelNumber, @PageableDefault(page=0, size = 5, sort ="replyUploadDate", direction = Sort.Direction.DESC) Pageable pageable){
        Page<ReplyVO> replyVOS = replyService.getReplyListBySubNovelNumber(subNovelNumber, pageable);
        Page<UserVO> userVOS = replyVOS.map(v->v.getUserVO());
        ReplyUserDTO replyUserDTO= new ReplyUserDTO(replyVOS, userVOS, pageable);
        return replyUserDTO;
    }

    //소설번호로 가져온 댓글 최신순
    @GetMapping("getReplyUpUserDTOBySubNum")
    public ReplyUserDTO getReplyUpUserDTOBySubNum(Long subNovelNumber, @PageableDefault(page=0, size = 5, sort ="replyUp", direction = Sort.Direction.DESC) Pageable pageable){
        Page<ReplyVO> replyVOS = replyService.getReplyListBySubNovelNumber(subNovelNumber, pageable);
        Page<UserVO> userVOS = replyVOS.map(v->v.getUserVO());
        ReplyUserDTO replyUserDTO= new ReplyUserDTO(replyVOS, userVOS, pageable);
        return replyUserDTO;
    }

    @GetMapping("deleteReply")
    public void deleteReply(Long replyNumber){
        replyService.removeReply(replyNumber);
    }




}


