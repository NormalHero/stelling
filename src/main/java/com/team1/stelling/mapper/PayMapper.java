package com.team1.stelling.mapper;

import com.team1.stelling.domain.vo.Criteria;
import com.team1.stelling.domain.vo.MyIllustVO;
import com.team1.stelling.domain.vo.PayDTO;
import com.team1.stelling.domain.vo.PayVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PayMapper {
    //등록
//    public int insert(Long userNumber, Long payCharge, Long payCoinCount);
    public int insert(PayVO payVO);

    //결제 내역
    public List<PayVO> getList(Criteria criteria, Long userNumber);

    //총 결제 금액
    public PayDTO getTotal(Long userNumber);

    //검색한 결과의 총 개수(페이징 처리를 위함)
    public int getSearchTotal(Criteria criteria);

    //조회
    public PayVO read(Long payNumber);

    public int update(PayVO payVO);

    //거래 취소시 삭제?
    public int delete(Long payNumber);

//    public PayVO get(Long payNumber);
}
