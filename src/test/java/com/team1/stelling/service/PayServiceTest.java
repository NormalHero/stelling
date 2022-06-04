package com.team1.stelling.service;

import com.team1.stelling.domain.vo.Criteria;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class PayServiceTest {
    @Autowired
    PayService payService;

    @Test
    public void testGetList(){
        payService.getList(new Criteria(3,10), 1L).forEach(pay -> log.info(pay.toString()));
    }
}
