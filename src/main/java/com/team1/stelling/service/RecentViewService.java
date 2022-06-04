package com.team1.stelling.service;

import com.team1.stelling.domain.repository.RecentViewRepository;
import com.team1.stelling.domain.vo.RecentViewVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RecentViewService {
    private  final  RecentViewRepository recentViewRepository;

    public void register(RecentViewVO vo){ recentViewRepository.save(vo);}
    public RecentViewVO get(Long rvNo){return recentViewRepository.findById(rvNo).get();}
    public List<RecentViewVO> getList(){return recentViewRepository.findAll();}
    public Page<RecentViewVO> getMyView(Long userNum, Pageable pageable){return recentViewRepository.findByUserVO_UserNumber(userNum,pageable);}
    public Page<RecentViewVO> getMyViewSearch(Long userNum, String keyword, Pageable pageable){ return recentViewRepository.findByUserVO_UserNumberAndNovelVO_NovelHashtagContaining(userNum,keyword,pageable);}
    public boolean remove(Long recentViewNumber){ return recentViewRepository.deleteByRecentViewNumber(recentViewNumber) == 1;}
    public boolean findRecentView(Long novelNumber){ return recentViewRepository.findByNovelNumber(novelNumber) == 1;}
    public void registerByUserNumber (Long userNumber){recentViewRepository.registerByUserNumber(userNumber);}
}
