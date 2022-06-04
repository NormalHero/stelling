package com.team1.stelling.controller;

import com.team1.stelling.aspect.annotation.LogStatus;
import com.team1.stelling.domain.dto.PageableDTO;
import com.team1.stelling.domain.vo.MyPickVO;
import com.team1.stelling.domain.vo.RecentViewVO;
import com.team1.stelling.service.MyLibraryService;
import com.team1.stelling.service.RecentViewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@Slf4j
@RequestMapping("/myLibrary/myLibraryCollection/*")
@RequiredArgsConstructor
public class MyLibraryCollectionController {
    private final MyLibraryService myLibraryService;
    private final RecentViewService recentViewService;

    @GetMapping("myPick")
    public String myLibraryCollectionMyPick(Model model, @PageableDefault(page = 0, size = 10, sort = "myPickNumber" ,direction = Sort.Direction.DESC) Pageable pageable, HttpServletRequest request){
        HttpSession session =  request.getSession();
        Long userNum = (Long)session.getAttribute("userNumber");
        Page<MyPickVO> list = myLibraryService.getMyPickList(userNum,pageable);
        PageableDTO pageableDTO = new PageableDTO( (int)list.getTotalElements(),pageable);
        model.addAttribute("list",list);
        model.addAttribute("total",list.getTotalElements());
        model.addAttribute("pageableDTO", pageableDTO);
        return "myLibrary/myLibraryCollection";
    }

    @GetMapping("myPick/search")
    public String myLibraryCollectionMyPickSearch(String keyword,Model model, @PageableDefault(page = 0, size = 10, sort = "myPickNumber" ,direction = Sort.Direction.DESC) Pageable pageable,HttpServletRequest request){
        HttpSession session =  request.getSession();
        Long userNum = (Long)session.getAttribute("userNumber");
        Page<MyPickVO> searchList = myLibraryService.getMyPickTagSearch(userNum,keyword,pageable);
        PageableDTO  pageableDTO = new PageableDTO( (int)searchList.getTotalElements(),pageable);
        pageableDTO.setKeyword(keyword);
        model.addAttribute("list",searchList);
        model.addAttribute("total",searchList.getTotalElements());
        model.addAttribute("pageableDTO", pageableDTO);
        return "myLibrary/myLibraryCollectionSearch";
    }

    @LogStatus
    @ResponseBody
    @DeleteMapping( value = "/myPick/delete/{myPickViewNumber}")
    public String myPickRemove(@PathVariable("myPickViewNumber") Long myPickViewNumber){
        log.info("remove............" + myPickViewNumber);
        return myLibraryService.remove(myPickViewNumber) ? "찜 삭제 성공" : "찜  삭제 실패";
    }


    @GetMapping("myRecentView")
    public String myRecentView(Model model, @PageableDefault(page = 0, size = 10, sort = "recentViewNumber" ,direction = Sort.Direction.DESC) Pageable pageable,HttpServletRequest request){
        HttpSession session =  request.getSession();
        Long userNum = (Long)session.getAttribute("userNumber");
        Page<RecentViewVO> list = recentViewService.getMyView(userNum,pageable);
        PageableDTO  pageableDTO = new PageableDTO( (int)list.getTotalElements(),pageable);
        model.addAttribute("list",list);
        model.addAttribute("total",list.getTotalElements());
        model.addAttribute("pageableDTO", pageableDTO);
        return "myLibrary/myLibraryRecentView";
    }

    @GetMapping("myRecentView/search")
    public String myRecentViewSearch(String keyword,Model model, @PageableDefault(page = 0, size = 10, sort = "recentViewNumber" ,direction = Sort.Direction.DESC) Pageable pageable,HttpServletRequest request){
        HttpSession session =  request.getSession();
        Long userNum = (Long)session.getAttribute("userNumber");
        Page<RecentViewVO> list = recentViewService.getMyViewSearch(userNum,keyword,pageable);
        PageableDTO  pageableDTO = new PageableDTO( (int)list.getTotalElements(),pageable);
        pageableDTO.setKeyword(keyword);
        model.addAttribute("list",list);
        model.addAttribute("total",list.getTotalElements());
        model.addAttribute("pageableDTO", pageableDTO);
        return "myLibrary/myLibraryRecentViewSearch";
    }


    @LogStatus
    @ResponseBody
    @DeleteMapping( value = "/myRecentView/delete/{recentViewNumber}")
    public String recentRemove(@PathVariable("recentViewNumber") Long recentViewNumber){
        return recentViewService.remove(recentViewNumber) ? "최근 본 작품 삭제 성공" : "최근 본 작품 삭제 실패";
    }
}
