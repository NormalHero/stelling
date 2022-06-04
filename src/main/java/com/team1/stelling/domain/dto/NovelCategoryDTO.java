package com.team1.stelling.domain.dto;

import com.team1.stelling.domain.vo.UserVO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;
@Component
@Getter

public class NovelCategoryDTO {
    private Long novelNumber; // 소설 번호
    private UserVO userVO;
    private String novelTitle;  // 소설제목
    private String[] novelHashtag; /*예) #로맨스 #메카물 #마법사*/
    private Date novelUploadDate; // 등록 시간
    private Date novelUpdateDate; // 수정 시간
    private int novelStatus;  /*작품 상태 0휴재, 1연재, 2완결, 3 숨김*/
    private int novelSerialsStatus; /*정기연재 OR 자유연재 상태*/
    private String novelIntro; /* 소설 소개*/
    private String novelFileName; /* 프로질 사진 이름 */
    private String novelUploadPath; /* 경로 */
    private String novelUUID; /* UUID */
    private int novelRoundAboutTotal;
    private String novelLikeCountTotal;
    private String novelViewCountTotal;


    public void setUserVO(UserVO userVO) { this.userVO = userVO; }

    public void setNovelTitle(String novelTitle) { this.novelTitle = novelTitle; }

    public void setNovelHashtag(String novelHashtag) {
        if(!Objects.isNull(novelHashtag)){
            this.novelHashtag = novelHashtag.split(" ");
        }else {
            this.novelHashtag =null;
        }
    }

    public void setNovelUpdateDate(Date novelUpdateDate) { this.novelUpdateDate = novelUpdateDate; }

    public void setNovelUploadDate(Date novelUploadDate) { this.novelUploadDate = novelUploadDate; }

    public void setNovelStatus(int novelStatus) { this.novelStatus = novelStatus; }

    public void setNovelSerialsStatus(int novelSerialsStatus) { this.novelSerialsStatus = novelSerialsStatus; }

    public void setNovelIntro(String novelIntro) { this.novelIntro = novelIntro; }

    public void setNovelFileName(String novelFileName) { this.novelFileName = novelFileName; }

    public void setNovelUploadPath(String novelUploadPath) { this.novelUploadPath = novelUploadPath; }

    public void setNovelUUID(String novelUUID) { this.novelUUID = novelUUID; }

    public void setNovelRoundAboutTotal(int novelRoundAboutTotal) { this.novelRoundAboutTotal = novelRoundAboutTotal; }

    public void setNovelLikeCountTotal(int novelLikeCountTotal) { this.novelLikeCountTotal = setStringNumber(novelLikeCountTotal); }

    public void setNovelViewCountTotal(int novelViewCountTotal) { this.novelViewCountTotal = setStringNumber(novelViewCountTotal); }

    public void setNovelNumber(Long novelNumber) { this.novelNumber = novelNumber;}

    public static String setStringNumber(int count) {
        if(count >= 1000 && count < 10000){ //1k
            return (count / 1000) + "." + Math.round(count%1000/100) + "K";
        }
        else if(count >= 10000 && count < 100000){ //10k
            return (count / 1000) + "." + Math.round(count%1000/100) + "K";
        }
        else if(count >= 100000 && count < 1000000){ //100k
            return (count / 1000) + "." + Math.round(count%1000/100) + "K";
        }
        else if(count >= 1000000 && count < 10000000){ //1M
            return (count / 1000000) + "." + Math.round(count%1000000/100000) + "M";
        }
        else if(count >= 10000000 && count < 100000000){ //10M
            return (count / 1000000) + "." + Math.round(count%1000000/100000) + "M";
        }
        else if(count >= 100000000 && count < 1000000000){ //100M
            return (count / 1000000) + "." + Math.round(count%1000000/100000) + "M";
        }
        else if(count >= 1000000000){ //1B
            return (count / 1000000000) + "B";
        }
        return count+"";
    }

}
