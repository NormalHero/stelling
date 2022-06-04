package com.team1.stelling.domain.dao;

import com.team1.stelling.domain.vo.*;
import com.team1.stelling.mapper.PayMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class PayDAO {
    private final PayMapper payMapper;

//    public PayVO get(Long payNumber) {return payMapper.get(payNumber);}
    //결제 내역
    public List<PayVO> getList(Criteria criteria, Long userNumber) {return payMapper.getList(criteria, userNumber);}

    //총 결제 금액
    public PayDTO getTotal(Long userNumber) {return payMapper.getTotal(userNumber);}

    //검색한 결과의 총 개수(페이징 처리를 위함)
    public int getSearchTotal(Criteria criteria) {return payMapper.getSearchTotal(criteria);}

//    public void register(Long userNumber, Long payCharge, Long payCoinCount) {payMapper.insert(userNumber, payCharge, payCoinCount);}
    public void register(PayVO payVO) {payMapper.insert(payVO);}
}
