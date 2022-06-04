package com.team1.stelling.controller;


import com.team1.stelling.aspect.annotation.LogStatus;
import com.team1.stelling.domain.dto.*;
import com.team1.stelling.domain.vo.*;
import com.team1.stelling.service.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
import org.bouncycastle.math.raw.Mod;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriComponentsBuilder;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


@Controller
@Slf4j
@RequestMapping("/novel/*")
@RequiredArgsConstructor
public class NovelController {

    private  final NovelService novelService;
    private final UserService userService;
    private final SubNovelService subNovelService;
    private final NovelFileService novelFileService;
    private final RecentViewService recentViewService;



    @GetMapping("novelRegister")
    public void ViewDetail(){
    }

    @GetMapping("novelWrite")
    public void novelWrite(Long novelNumber, Model model){
        model.addAttribute("novelNumber", novelNumber);
    }

    /*소설 수정페이지 이동*/
    @GetMapping("novelModify")
    public String novelModify(Long subNovelNumber,Long novelNumber,Model model) throws IOException{
        String getFilePath= novelFileService.getFilePathBySubNum(subNovelNumber).getNovelFileFilePath();
        String getFileName= novelFileService.getFilePathBySubNum(subNovelNumber).getNovelFileFileName();
        SubNovelVO subNovelVO = subNovelService.get(subNovelNumber);

        BufferedReader br = null;
        String line = null;
        String novelContent = "";
        try {
            br = new BufferedReader(new FileReader("/home/ubuntu/stelling/upload/"+getFilePath+"/"+getFileName+".txt"));

            while((line = br.readLine()) != null){
                novelContent+=line+"\n";
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if(br !=null){
                br.close();
            }
        }
        model.addAttribute("novelContent",novelContent);
        model.addAttribute("subNovelVO",subNovelVO);
        model.addAttribute("modifyCheck",true);
        model.addAttribute("subNovelNumber",subNovelNumber);
        model.addAttribute("novelNumber", novelNumber);

        return "novel/novelWrite";
    }

    @LogStatus
    @GetMapping("novelCategory")
    public void novelCategory(Model model, @PageableDefault(page = 0, size = 10, sort = "novelNumber" ,direction = Sort.Direction.DESC)Pageable pageable){
        Page<NovelCategoryDTO> list = novelService.getList(pageable);
        PageableDTO pageableDTO = new PageableDTO( (int)list.getTotalElements(),pageable);
        model.addAttribute( "list",list);
        model.addAttribute( "novelTotal", list.getTotalElements());
        model.addAttribute("pageableDTO", pageableDTO);
    }


    @GetMapping("novelRanking")
    public void novelRanking(){
    }


    /*소설상세보기 페이지*/
    @GetMapping("novelDetailView")
    public void ViewDetail(Long novelNumber,Long subNovelNumber,int count, HttpServletRequest request, Model model) throws IOException {
        String getFilePath= novelFileService.getFilePathBySubNum(subNovelNumber).getNovelFileFilePath();
        String getFileName= novelFileService.getFilePathBySubNum(subNovelNumber).getNovelFileFileName();
        SubNovelVO subNovelVO = subNovelService.get(subNovelNumber);
        subNovelVO.updateSubNovelViewCount();
        subNovelService.register(subNovelVO);

        NovelVO novelVo = subNovelVO.getNovelVO();
        List<SubNovelVO> subNovelVOList = subNovelService.getListByNovelNumber(novelNumber);

        HttpSession session = request.getSession();
        Long userNumber = (Long) session.getAttribute("userNumber");
        if(!recentViewService.findRecentView(novelNumber)){
            RecentViewVO recentViewVO = RecentViewVO.builder().userVO(userService.get(userNumber)).novelVO(novelService.get(novelNumber)).build();
            recentViewService.register(recentViewVO);
        }

        BufferedReader br = null;
        String line = null;
        String result = "";


        try {
            br = new BufferedReader(new FileReader("/home/ubuntu/stelling/upload/"+getFilePath+"/"+getFileName+".txt"));
            while((line = br.readLine()) != null){
                result+=line+"\n";
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if(br !=null){
                br.close();
            }
        }

        if(userNumber!=null){
            UserVO userVO=userService.get(userNumber);
            model.addAttribute("balance", userVO.getUserCoinBalance());
        }

        model.addAttribute("novelContent",result);
        model.addAttribute("novelNumber",novelNumber);
        model.addAttribute("subNovelVO",subNovelVO);
        model.addAttribute("subNovelVOList",subNovelVOList);
        model.addAttribute("subNovelNumber",subNovelNumber);
        model.addAttribute("novelVO",novelVo);
        model.addAttribute("count",count);

    }

    @GetMapping("nextPrevNovelDetailView")
    public RedirectView nextPrevNovelDetailView(Long novelNumber, Long subNovelNumber, int num, int count,RedirectAttributes rttr){
        List<SubNovelVO> subNovelVOList=subNovelService.getListByNovelNumber(novelNumber);
        int currentIndexNum = subNovelVOList.indexOf(subNovelService.get(subNovelNumber));
        Long nextPrevSubNovelNumber = subNovelVOList.get(currentIndexNum+num).getSubNovelNumber();
        rttr.addAttribute("novelNumber", novelNumber);
        rttr.addAttribute("subNovelNumber",nextPrevSubNovelNumber);
        rttr.addAttribute("count", count);
        return  new RedirectView("novelDetailView");

    }


    @LogStatus
    @GetMapping("novelSearch")
    public String novelSearch (String keyword, Model model, @PageableDefault(page = 0, size = 10, sort = "novelNumber" ,direction = Sort.Direction.DESC)Pageable pageable){
        Page<NovelCategoryDTO> searchList = novelService.search(keyword, pageable);
        PageableDTO pageableDTO = new PageableDTO( (int)searchList.getTotalElements(),pageable);
        searchList.getSize();
        model.addAttribute("list", searchList);
        model.addAttribute( "novelTotal", searchList.getTotalElements());
        model.addAttribute("keyword", keyword);
        model.addAttribute("pageableDTO", pageableDTO);
        return "novel/novelCategory";
    }

    /*소설 등록*/
    @PostMapping("novelRegister")
    public RedirectView novelRegister(NovelVO novelVO, HttpServletRequest request, RedirectAttributes rttr) {

        HttpSession session = request.getSession();
        Long userNumber = (Long)session.getAttribute("userNumber");
        novelVO.setUserVO(userService.get(userNumber));

            String[] result = novelVO.getNovelHashtag().split(" ");
            novelVO.setNovelCategory(result[0]);
        Long novelNumber = novelService.registerReturnNovelNum(novelVO);
        rttr.addAttribute("novelNumber",novelNumber);
        return new RedirectView("novelRoundList");
    }

    /*소설 표지 이미지 저장*/
    @PostMapping("uploadAjaxAction")
    @ResponseBody
    public List<NovelVO> uploadAjaxPost(MultipartFile[] uploadFile) {
        String uploadFolder = "/home/ubuntu/stelling/upload/";
        List<NovelVO> fileList = new ArrayList<>();

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
                fileList.add(NovelVO.builder().novelFileName(uploadFileName)
                        .novelUUID(uuid.toString())
                        .novelUploadPath(uploadFolderPath).build());
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


    /*소설 회차 파일입력 textarea에 뿌려주기*/
    @ResponseBody
    @PostMapping(value = "/novelFileUpload", headers = ("content-type=multipart/*"))
    public String uploadTest(@RequestParam("file") MultipartFile mfile) throws IOException, TikaException, SAXException {
        String result = null;
        BodyContentHandler handler = new BodyContentHandler();
        AutoDetectParser parser = new AutoDetectParser();
        Metadata metadata = new Metadata();
        InputStream fileStream = mfile.getInputStream();
        try (InputStream stream = fileStream) {
            parser.parse(stream, handler, metadata);
            result = handler.toString();
        }
        return result;
    }


    /*소설회차 원고파일 생성*/
    @PostMapping("makeNovelFile")
    public RedirectView novelBufferedWrite(NovelFileDTO novelFileDTO) throws IOException {

        NovelVO novelVO =novelService.get(novelFileDTO.getNovelNumber());
        UserVO userVO =userService.get(novelFileDTO.getUserNumber());

        SubNovelVO subNovelVO =SubNovelVO.builder()
                .novelVO(novelVO)
                .userVO(userVO)
                .subNovelTitle(novelFileDTO.getSubNovelTitle())
                .subNovelWriterComment(novelFileDTO.getSubNovelWriterComment())
                .subNovelStatus(1).build();

        subNovelService.register(subNovelVO);
        novelVO.setNovelRoundAboutTotal(novelVO.getNovelRoundAboutTotal()+1);
        novelService.modify(novelVO);
        String title = novelVO.getNovelTitle();
        String uploadFolder = "/home/ubuntu/stelling/upload/";
        UUID uuid = UUID.randomUUID();
        String uploadFolderPath = novelVO.getNovelNumber()+"_"+title+"/"+getPath();
        String uploadFileName = uuid.toString() + "_" + novelFileDTO.getSubNovelTitle();

        File uploadPath = new File(uploadFolder, uploadFolderPath);
        if (!uploadPath.exists()) {
            uploadPath.mkdirs();
        }

        novelFileService.register(NovelFileVO.builder().novelFileFileName(uploadFileName)
                .novelFileFilePath(uploadFolderPath)
                .novelFileOriginalUUID(uuid.toString())
                .subNovelVO(subNovelVO)
                .novelVO(novelVO)
                .userVO(userVO).build());
        BufferedWriter bw = new BufferedWriter(new FileWriter(new File(uploadPath+"/"+uploadFileName+".txt")));
        bw.write(novelFileDTO.getContent());
        bw.close();
        return new RedirectView("novelRoundList?novelNumber="+novelFileDTO.getNovelNumber());
    }

    /*수정된 파일 DB저장, 파일 저장*/
    @PostMapping("modifyNovelFile")
    public RedirectView modifyNovelFile(NovelFileDTO novelFileDTO, RedirectAttributes rttr) throws IOException{
        DateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String today = sdFormat.format(date);
        subNovelService.register(SubNovelVO.builder()
                .subNovelUpdateDate(today)
                .subNovelTitle(novelFileDTO.getSubNovelTitle())
                .subNovelWriterComment(novelFileDTO.getSubNovelWriterComment())
                .subNovelNumber(novelFileDTO.getSubNovelNumber())
                .novelVO(novelService.get(novelFileDTO.getNovelNumber()))
                .build());
        NovelFileVO novelFileVO = novelFileService.getFilePathBySubNum(novelFileDTO.getSubNovelNumber());
        String uploadPath="/home/ubuntu/stelling/upload/"+novelFileVO.getNovelFileFilePath();
        String uploadFileName = novelFileVO.getNovelFileFileName();

        BufferedWriter bw = new BufferedWriter(new FileWriter(new File(uploadPath+"/"+uploadFileName+".txt")));
        bw.write(novelFileDTO.getContent());
        bw.close();

        List<SubNovelVO> subNovelVOList = subNovelService.getListByNovelNumber(novelFileDTO.getNovelNumber());
        int index=subNovelVOList.indexOf(subNovelService.get(novelFileDTO.getSubNovelNumber()));

        rttr.addAttribute("novelNumber",novelFileDTO.getNovelNumber());
        rttr.addAttribute("subNovelNumber",novelFileDTO.getSubNovelNumber());
        rttr.addAttribute("count", index+1);
        return new RedirectView("novelDetailView");
    }





}