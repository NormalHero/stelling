package com.team1.stelling.domain.dao;

import com.team1.stelling.domain.dto.SupportDTO;
import com.team1.stelling.domain.vo.*;
import com.team1.stelling.mapper.SupportMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class SupportDAO {
    private final SupportMapper supportMapper;

    //후원 내역

    public List<SupportVO> getSupportList(Paging paging, Long userNumber) {return supportMapper.getSupportList(paging, userNumber);}


    public void register(SupportVO supportVO) {supportMapper.insert(supportVO);}

    //검색한 결과의 총 개수(페이징 처리)
    public int getSearchSupportTotal(Paging paging) {return supportMapper.getSearchSupportTotal(paging);}

    //후원한 코인 총 개수
    public SupportDTO getSupportCoinTotal(Long userNumber){return supportMapper.getSupportCoinTotal(userNumber);}
    public List<SupportVO> getSupportListWithNovelNumber(Long novelNumber){return supportMapper.getSupportListWithNovelNumber(novelNumber);}

}
