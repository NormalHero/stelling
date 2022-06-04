package com.team1.stelling.domain.repository;

import com.team1.stelling.domain.vo.IllustVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IllustRepository extends JpaRepository<IllustVO, Long> {
    @Query(value = "SELECT COUNT(USER_NUMBER) FROM TBL_ILLUST", nativeQuery = true)
    int findByUserNumberTotal(Long userNumber);



    Page<IllustVO> findByUserVO_UserNumber(Pageable pageable, Long userNumber);

    List<IllustVO> findByUserVO_UserNumber(Long userNumber);

    Page<IllustVO> findByIllustHashTagContaining(String keyword, Pageable pageable);

    @Query(value = "SELECT SUM(ILLUST_LIKE) FROM TBL_ILLUST WHERE USER_NUMBER = :userNumber", nativeQuery = true)
    Long findByIllustLikeTotal(Long userNumber);

    @Modifying
    @Query(value = "update TBL_ILLUST set ILLUST_VIEWCOUNT = ILLUST_VIEWCOUNT + 1 where ILLUST_NUMBER = :illustNumber", nativeQuery = true)
    int updateViewCount(Long illustNumber);


}
