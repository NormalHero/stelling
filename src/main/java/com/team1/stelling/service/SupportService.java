package com.team1.stelling.service;

import com.team1.stelling.domain.dao.SupportDAO;
import com.team1.stelling.domain.dto.SupportDTO;
import com.team1.stelling.domain.vo.Paging;
import com.team1.stelling.domain.vo.SupportVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SupportService{
    private final SupportDAO supportDAO;

    //후원 내역
    public List<SupportVO> getSupportList(Paging paging, Long userNumber) {return supportDAO.getSupportList(paging, userNumber);}

    public void register(SupportVO supportVO) {supportDAO.register(supportVO);}

    //검색한 결과의 총 개수(페이징 처리)
    public int getSearchSupportTotal(Paging paging){return supportDAO.getSearchSupportTotal(paging);}

    //후원한 코인 총 개수
    public SupportDTO getSupportCoinTotal(Long userNumber){return supportDAO.getSupportCoinTotal(userNumber);}
    public List<SupportVO> getSupportListWithNovelNumber(Long novelNumber){return supportDAO.getSupportListWithNovelNumber(novelNumber);}

}
