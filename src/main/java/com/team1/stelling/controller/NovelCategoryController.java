package com.team1.stelling.controller;

import com.team1.stelling.aspect.annotation.LogStatus;
import com.team1.stelling.domain.dto.NovelCategoryDTO;
import com.team1.stelling.domain.dto.PageableDTO;
import com.team1.stelling.service.NovelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Calendar;
import java.util.Date;

@Controller
@Slf4j
@RequestMapping("/novel/category/*")
@RequiredArgsConstructor
public class NovelCategoryController {
    private  final NovelService novelService;
    final int ALLNOVELSTATUS = 1;
    final int NEWNOVELSTATUS = 1;
    final int ENDNOVELSTATUS = 1;

    /* 모든 소설 ==========================================================================================*/
    // 기본정렬
    @LogStatus
    @GetMapping("novelCategory")
    public String novelCategory(@RequestParam(defaultValue="0") int categoryStatus, Model model, @PageableDefault(page = 0, size = 10, sort = "novelNumber" ,direction = Sort.Direction.DESC)Pageable pageable){
        Page<NovelCategoryDTO> list = null;
        log.info("들어옴1");
       if(categoryStatus == 1){
       // 신작
           Calendar cal = Calendar.getInstance();
           Date now = new Date();
           cal.setTime(now);
           cal.add(Calendar.DATE, -7);
           Date targetPast = cal.getTime();
           list = novelService.getNewNovelList(targetPast,now,pageable);
       }else if (categoryStatus == 2){
           list =   novelService.getEndNovelList(pageable);
       }else{ list = novelService.getList(pageable);}

        PageableDTO pageableDTO = new PageableDTO((int) list.getTotalElements(), pageable);
        pageableDTO.setCategoryStatus(categoryStatus);


        model.addAttribute( "list",list);
        model.addAttribute( "novelTotal", list.getTotalElements());
        model.addAttribute("pageableDTO", pageableDTO);
        return "novel/category/novelCategory";
    }

    // 좋아요 정렬
    @LogStatus
    @GetMapping("novelCategory/viewCount")
    public String novelCategoryViewCount(@RequestParam(defaultValue="0") int categoryStatus, Model model, @PageableDefault(page = 0, size = 10, sort = "novelViewCountTotal" ,direction = Sort.Direction.DESC)Pageable pageable){
        Page<NovelCategoryDTO> list = null;
        if(categoryStatus == 1){
            // 신작
            Calendar cal = Calendar.getInstance();
            Date now = new Date();
            cal.setTime(now);
            cal.add(Calendar.DATE, -7);
            Date targetPast = cal.getTime();
            list = novelService.getNewNovelList(targetPast,now,pageable);
        }else if (categoryStatus == 2){
            list =   novelService.getEndNovelList(pageable);
        }else{ list = novelService.getList(pageable);}
        PageableDTO pageableDTO = new PageableDTO( (int)list.getTotalElements(),pageable);
        pageableDTO.setCategoryStatus(categoryStatus);
        model.addAttribute( "list",list);
        model.addAttribute( "novelTotal", list.getTotalElements());
        model.addAttribute("pageableDTO", pageableDTO);
        return "novel/category/novelCategoryViewCount";
    }

    // 좋아요 정렬
    @LogStatus
    @GetMapping("novelCategory/likeCount")
    public String novelCategoryLikeCount(@RequestParam(defaultValue="0") int categoryStatus, Model model, @PageableDefault(page = 0, size = 10, sort = "novelLikeCountTotal" ,direction = Sort.Direction.DESC)Pageable pageable){
        Page<NovelCategoryDTO> list = null;
        if(categoryStatus == 1){
            // 신작
            Calendar cal = Calendar.getInstance();
            Date now = new Date();
            cal.setTime(now);
            cal.add(Calendar.DATE, -7);
            Date targetPast = cal.getTime();
            list = novelService.getNewNovelList(targetPast,now,pageable);
        }else if (categoryStatus == 2){
            list =   novelService.getEndNovelList(pageable);
        }else{ list = novelService.getList(pageable);}
        PageableDTO pageableDTO = new PageableDTO( (int)list.getTotalElements(),pageable);
        pageableDTO.setCategoryStatus(categoryStatus);
        model.addAttribute( "list",list);
        model.addAttribute( "novelTotal", list.getTotalElements());
        model.addAttribute("pageableDTO", pageableDTO);
        return "novel/category/novelCategoryLikeCount";
    }
    /* 모든 소설 끝 ==========================================================================================*/

    /* 태그 검색 ===========================================================================================*/
    @LogStatus
    @GetMapping("novelSearch")
    public String novelSearch (@RequestParam(defaultValue="0") int categoryStatus, String keyword, Model model, @PageableDefault(page = 0, size = 10, sort = "novelNumber" ,direction = Sort.Direction.DESC) Pageable pageable){
        Page<NovelCategoryDTO> searchList = null;
        if(categoryStatus == 1){
            Calendar cal = Calendar.getInstance();
            Date now = new Date();
            cal.setTime(now);
            cal.add(Calendar.DATE, -7);
            Date targetPast = cal.getTime();
            searchList = novelService.getNewNovelListSearch(targetPast,now,keyword,pageable);
        }else if(categoryStatus == 2){
            searchList = novelService.getEndNovelListSearch(keyword,pageable);
        }else{
            searchList = novelService.search(keyword, pageable);
        }
//        Page<NovelCategoryDTO> searchList = novelSearchService.search(keyword, pageable);
        PageableDTO pageableDTO = new PageableDTO( (int)searchList.getTotalElements(),pageable);
        pageableDTO.setCategoryStatus(categoryStatus);
        searchList.getSize();
        pageableDTO.setKeyword(keyword);
        model.addAttribute("list", searchList);
        model.addAttribute( "novelTotal", searchList.getTotalElements());
        model.addAttribute("keyword", keyword);
        model.addAttribute("pageableDTO", pageableDTO);
        return "novel/category/novelCategoryAllSearch";
    }

    @LogStatus
    @GetMapping("novelSearch/viewCount")
    public String novelSearchViewCount (@RequestParam(defaultValue="0") int categoryStatus, String keyword, Model model, @PageableDefault(page = 0, size = 10, sort = "novelViewCountTotal" ,direction = Sort.Direction.DESC) Pageable pageable){
        Page<NovelCategoryDTO> searchList = null;
        if(categoryStatus == 1){
            Calendar cal = Calendar.getInstance();
            Date now = new Date();
            cal.setTime(now);
            cal.add(Calendar.DATE, -7);
            Date targetPast = cal.getTime();
            searchList = novelService.getNewNovelListSearch(targetPast,now,keyword,pageable);
        }else if(categoryStatus == 2){
            searchList = novelService.getEndNovelListSearch(keyword,pageable);
        }else{
            searchList = novelService.search(keyword, pageable);
        }
//        Page<NovelCategoryDTO> searchList = novelSearchService.search(keyword, pageable);
        PageableDTO pageableDTO = new PageableDTO( (int)searchList.getTotalElements(),pageable);
        pageableDTO.setCategoryStatus(categoryStatus);
        searchList.getSize();
        pageableDTO.setKeyword(keyword);
        model.addAttribute("list", searchList);
        model.addAttribute( "novelTotal", searchList.getTotalElements());
        model.addAttribute("keyword", keyword);
        model.addAttribute("pageableDTO", pageableDTO);
        return "novel/category/novelCategoryAllSearchViewCount";
    }

    @LogStatus
    @GetMapping("novelSearch/likeCount")
    public String novelSearchViewCountLikeCount (@RequestParam(defaultValue="0") int categoryStatus, String keyword, Model model, @PageableDefault(page = 0, size = 10, sort = "novelLikeCountTotal" ,direction = Sort.Direction.DESC) Pageable pageable){
        Page<NovelCategoryDTO> searchList = null;
        if(categoryStatus == 1){
            Calendar cal = Calendar.getInstance();
            Date now = new Date();
            cal.setTime(now);
            cal.add(Calendar.DATE, -7);
            Date targetPast = cal.getTime();
            searchList = novelService.getNewNovelListSearch(targetPast,now,keyword,pageable);
        }else if(categoryStatus == 2){
            searchList = novelService.getEndNovelListSearch(keyword,pageable);
        }else{
            searchList = novelService.search(keyword, pageable);
        }
//        Page<NovelCategoryDTO> searchList = novelSearchService.search(keyword, pageable);
        PageableDTO pageableDTO = new PageableDTO( (int)searchList.getTotalElements(),pageable);
        pageableDTO.setCategoryStatus(categoryStatus);
        searchList.getSize();
        pageableDTO.setKeyword(keyword);
        model.addAttribute("list", searchList);
        model.addAttribute( "novelTotal", searchList.getTotalElements());
        model.addAttribute("keyword", keyword);
        model.addAttribute("pageableDTO", pageableDTO);
        return "novel/category/novelCategoryAllSearchLikeCount";
    }
    /* 검색 끝===========================================================================================*/

    /* 태그 검색(클릭) 시작===========================================================================================*/
    @LogStatus
    @GetMapping("novelFindToTag")
    public String novelFindToTag (@RequestParam(defaultValue="0") int categoryStatus, String keyword, Model model, @PageableDefault(page = 0, size = 10, sort = "novelNumber" ,direction = Sort.Direction.DESC)Pageable pageable){
        Page<NovelCategoryDTO> searchList = null;
        if(categoryStatus == 1){
            Calendar cal = Calendar.getInstance();
            Date now = new Date();
            cal.setTime(now);
            cal.add(Calendar.DATE, -7);
            Date targetPast = cal.getTime();
            searchList = novelService.getNewNovelListSearch(targetPast,now,keyword,pageable);
        }else if(categoryStatus == 2){
            searchList = novelService.getEndNovelListSearch(keyword,pageable);
        }else{
            searchList = novelService.search(keyword, pageable);
        }
//        Page<NovelCategoryDTO> searchList = novelSearchService.search(keyword, pageable);
        PageableDTO pageableDTO = new PageableDTO( (int)searchList.getTotalElements(),pageable);
        pageableDTO.setCategoryStatus(categoryStatus);
        searchList.getSize();
        pageableDTO.setKeyword(keyword);
        model.addAttribute("list", searchList);
        model.addAttribute( "novelTotal", searchList.getTotalElements());
        model.addAttribute("keyword", keyword);
        model.addAttribute("pageableDTO", pageableDTO);
        return "novel/category/novelCategoryFindToTag";
    }

    @LogStatus
    @GetMapping("novelFindToTag/viewCount")
    public String novelFindToTagViewCount (@RequestParam(defaultValue="0") int categoryStatus, String keyword, Model model, @PageableDefault(page = 0, size = 10, sort = "novelViewCountTotal" ,direction = Sort.Direction.DESC)Pageable pageable){
        Page<NovelCategoryDTO> searchList = null;
        if(categoryStatus == 1){
            Calendar cal = Calendar.getInstance();
            Date now = new Date();
            cal.setTime(now);
            cal.add(Calendar.DATE, -7);
            Date targetPast = cal.getTime();
            searchList = novelService.getNewNovelListSearch(targetPast,now,keyword,pageable);
        }else if(categoryStatus == 2){
            searchList = novelService.getEndNovelListSearch(keyword,pageable);
        }else{
            searchList = novelService.search(keyword, pageable);
        }
//        Page<NovelCategoryDTO> searchList = novelSearchService.search(keyword, pageable);
        PageableDTO pageableDTO = new PageableDTO( (int)searchList.getTotalElements(),pageable);
        pageableDTO.setCategoryStatus(categoryStatus);
        searchList.getSize();
        pageableDTO.setKeyword(keyword);
        model.addAttribute("list", searchList);
        model.addAttribute( "novelTotal", searchList.getTotalElements());
        model.addAttribute("keyword", keyword);
        model.addAttribute("pageableDTO", pageableDTO);
        return "novel/category/novelCategoryFindToTagViewCount";
    }

    @LogStatus
    @GetMapping("novelFindToTag/likeCount")
    public String novelFindToTagLikeCount (@RequestParam(defaultValue="0") int categoryStatus,String keyword, Model model, @PageableDefault(page = 0, size = 10, sort = "novelLikeCountTotal" ,direction = Sort.Direction.DESC)Pageable pageable){
        Page<NovelCategoryDTO> searchList = null;
        if(categoryStatus == 1){
            Calendar cal = Calendar.getInstance();
            Date now = new Date();
            cal.setTime(now);
            cal.add(Calendar.DATE, -7);
            Date targetPast = cal.getTime();
            searchList = novelService.getNewNovelListSearch(targetPast,now,keyword,pageable);
        }else if(categoryStatus == 2){
            searchList = novelService.getEndNovelListSearch(keyword,pageable);
        }else{
            searchList = novelService.search(keyword, pageable);
        }
//        Page<NovelCategoryDTO> searchList = novelSearchService.search(keyword, pageable);
        PageableDTO pageableDTO = new PageableDTO( (int)searchList.getTotalElements(),pageable);
        pageableDTO.setCategoryStatus(categoryStatus);
        searchList.getSize();
        pageableDTO.setKeyword(keyword);
        model.addAttribute("list", searchList);
        model.addAttribute( "novelTotal", searchList.getTotalElements());
        model.addAttribute("keyword", keyword);
        model.addAttribute("pageableDTO", pageableDTO);
        return "novel/category/novelCategoryFindToTagLikeCount";
    }
    /* 태그 검색(클릭) 끝===========================================================================================*/

    // 완결 소설 파트 시작 ==================================================================================================================================
    @GetMapping("endNovel")
    public String novelTest (@RequestParam(defaultValue="0") int categoryStatus,String keyword, Model model, @PageableDefault(page = 0, size = 10, sort = "novelNumber" ,direction = Sort.Direction.DESC)Pageable pageable){
        Page<NovelCategoryDTO> list =   novelService.getEndNovelList(pageable);
        PageableDTO pageableDTO = new PageableDTO( (int)list.getTotalElements(),pageable);
        pageableDTO.setCategoryStatus(categoryStatus);
        model.addAttribute( "list",list);
        model.addAttribute( "novelTotal", list.getTotalElements());
        model.addAttribute("pageableDTO", pageableDTO);
        return "novel/category/novelCategory";
    }
    // 완결 소설 파트 종료 ==================================================================================================================================

}
