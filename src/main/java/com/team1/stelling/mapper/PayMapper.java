package com.team1.stelling.mapper;

import com.team1.stelling.domain.vo.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PayMapper {
    //등록
//    public int insert(Long userNumber, Long payCharge, Long payCoinCount);
    public int insert(PayVO payVO);

    //결제 내역
    public List<PayVO> getList(Paging paging, Long userNumber);

    //검색한 결과의 총 개수(페이징 처리를 위함)
    public int getSearchTotal(Paging paging);

    //총 결제 금액
    public PayDTO getTotal(Long userNumber);

    //결제 금액만 따로 가져오기
    public List<PayVO> getPayCharge(Long userNumber);

    //조회
    public PayVO read(Long payNumber);

    public int update(PayVO payVO);

    //거래 취소시 삭제?
    public int delete(Long payNumber);

//    public PayVO get(Long payNumber);
}
