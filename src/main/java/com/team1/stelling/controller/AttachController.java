/*
 * 문제점
 *   1. 동일한 이름으로 파일이 업로드 되었을 때 기존 파일이 사라지는 문제
 *   2. 이미지 파일의 경우 원본 파일의 용량이 큰 경우 썸네일 이미지를 생성해야 하는 문제
 *   3. 이미지 파일과 일반 파일을 구분해서 다운로드 혹은 페이지에서 조회하도록 처리하는 문제
 *   4. 첨부파일 공격에 대비하기 위한 업로드 파일의 확장자 제한
 * */
package com.team1.stelling.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Controller
@Slf4j
@RequestMapping("/upload/*")
public class AttachController {

    @GetMapping("display")
    @ResponseBody
    public byte[] getFile(String fileName) throws IOException{
        return FileCopyUtils.copyToByteArray(new File("/home/ubuntu/stelling/upload/" + fileName));
    }

    @GetMapping("download")
    @ResponseBody
    public ResponseEntity<Resource> download(String fileName){
        Resource resource = new FileSystemResource("/home/ubuntu/stelling/upload/" + fileName);
        HttpHeaders header = new HttpHeaders();
        String name = resource.getFilename();
        name = name.substring(name.indexOf("_") + 1);
//        브라우저에 다운로드 될 파일의 저장 정보를 설정한다.
        try {
            header.add("Content-Disposition", "attachment; filename=" + new String(name.getBytes("UTF-8"), "ISO-8859-1"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
    }

    @PostMapping("deleteFile")
    @ResponseBody
    public String deleteFile(String fileName, String type){
        File file = new File("/home/ubuntu/stelling/upload/" + fileName);
        file.delete();
        if(type.equals("image")){
            file = new File(file.getPath().replace("s_", ""));
            file.delete();
        }
        return "deleted";
    }

    private String getPath(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date today = new Date();
        return sdf.format(today);
    }

    private boolean checkImageType(File file){
        try {
            //헤더에 담긴 파일의 ContentType을 가져온다.
            //startsWith()를 사용해서 image라는 문자열로 시작한다면 true리턴, 아니면 false리턴
            return Files.probeContentType(file.toPath()).startsWith("image");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}









