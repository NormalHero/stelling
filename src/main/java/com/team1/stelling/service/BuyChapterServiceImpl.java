package com.team1.stelling.service;

import com.team1.stelling.domain.dao.BuyChapterDAO;
import com.team1.stelling.domain.vo.BuyChapterVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BuyChapterServiceImpl implements BuyChapterService {

    private final BuyChapterDAO buyChapterDAO;

    @Override
    public BuyChapterVO get(Long userNumber) {
        return buyChapterDAO.get(userNumber);
    }

    public void register(BuyChapterVO buyChapterVO) {
        buyChapterDAO.register(buyChapterVO);
    }

    public List<Long> getSubNumByNovelNum(Long novelNumber, Long userNumber) {
        return buyChapterDAO.getSubNovelNumByNovelNum(novelNumber, userNumber);
    }

}