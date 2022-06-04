package com.team1.stelling.domain.repository;

import com.team1.stelling.domain.vo.MyPickVO;
import com.team1.stelling.domain.vo.NovelVO;
import com.team1.stelling.domain.vo.RecentViewVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

public interface RecentViewRepository extends JpaRepository<RecentViewVO, Long> {
    Page<RecentViewVO> findByUserVO_UserNumber(Long userNumber, Pageable pageable);
    Page<RecentViewVO> findByUserVO_UserNumberAndNovelVO_NovelHashtagContaining(Long userNumber, String keyword, Pageable pageable);
    @Transactional
    int deleteByRecentViewNumber(Long recentViewNum);

    @Query(value = "select count(recentview_number)from TBL_RECENTVIEW where novel_number = :novelNumber",nativeQuery = true)
    int findByNovelNumber(Long novelNumber);

    @Query(value = "insert into TBL_RECENTVIEW where user_number = :userNumber",nativeQuery = true)
    void registerByUserNumber(Long userNumber);
}
