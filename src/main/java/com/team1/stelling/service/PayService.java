package com.team1.stelling.service;

import com.team1.stelling.domain.dao.PayDAO;
import com.team1.stelling.domain.vo.Criteria;
import com.team1.stelling.domain.vo.PayDTO;
import com.team1.stelling.domain.vo.PayVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PayService{
    private final PayDAO payDAO;

//    public PayVO get(Long payNumber) {return payDAO.get(payNumber);}
    //결제 내역
    public List<PayVO> getList(Criteria criteria, Long userNumber) {return payDAO.getList(criteria, userNumber);}

    //총 결제 금액
    public PayDTO getTotal(Long userNumber) {return payDAO.getTotal(userNumber);}

    //검색한 결과의 총 개수(페이징 처리를 위함)
    public int getSearchTotal(Criteria criteria) {return payDAO.getSearchTotal(criteria);}

//    public void register(Long userNumber, Long payCharge, Long payCoinCount) {payDAO.register(userNumber, payCharge, payCoinCount);}
    public void register(PayVO payVO) {payDAO.register(payVO);}
}
