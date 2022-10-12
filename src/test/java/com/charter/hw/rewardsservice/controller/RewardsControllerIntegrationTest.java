package com.charter.hw.rewardsservice.controller;

import com.charter.hw.rewardsservice.exceptions.SQLDataException;
import com.charter.hw.rewardsservice.model.Error;
import com.charter.hw.rewardsservice.model.Transaction;
import com.charter.hw.rewardsservice.model.vo.RewardsResponse;
import com.charter.hw.rewardsservice.model.vo.RewardsVO;
import com.charter.hw.rewardsservice.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RewardsControllerIntegrationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @SpyBean
    private TransactionService transactionService;

    @Mock
    private Transaction transaction;

    @Test
    public void getRewardsForCustomers_success_all_1() {
        LocalDate fromDate = LocalDate.now().minusDays(62);
        LocalDate toDate = LocalDate.now();
        ResponseEntity<RewardsResponse> response = testRestTemplate.getForEntity("/rewards-service/rewards?fromDate="+fromDate+"&toDate="+toDate, RewardsResponse.class);
        assertEquals(response.getStatusCode().value(), 200);
        RewardsResponse body = response.getBody();
        assertEquals(body.getRewards().size(), 2);
        RewardsVO vo1 = body.getRewards().get(0);
        RewardsVO vo2 = body.getRewards().get(1);

        assertEquals(vo1.getTotalPoints(), 1250);
        assertEquals(vo2.getTotalPoints(), 2860);
    }

    @Test
    public void getRewardsForCustomers_BusinessException() {
        LocalDate toDate = LocalDate.now().minusDays(62);
        LocalDate fromDate = LocalDate.now();
        ResponseEntity<Error> response = testRestTemplate.getForEntity("/rewards-service/rewards?fromDate="+fromDate+"&toDate="+toDate, Error.class);
        assertEquals(response.getStatusCode().value(), 400);
        Error body = response.getBody();
        assertTrue(body.getErrCode().equals("1001"));
        assertTrue(body.getErrMsg().equals("fromDate cannot be after toDate"));
    }

    @Test
    public void getRewardsForCustomers_SQLException() {
        LocalDate fromDate = LocalDate.now().minusDays(62);
        LocalDate toDate = LocalDate.now() ;
        when(transactionService.getListOfTransactionsBetween(fromDate, toDate)).thenThrow(new SQLDataException("Database Exploded!"));
        ResponseEntity<Error> response = testRestTemplate.getForEntity("/rewards-service/rewards?fromDate="+fromDate+"&toDate="+toDate, Error.class);
        assertEquals(response.getStatusCode().value(), 500);
        Error body = response.getBody();
        assertTrue(body.getErrCode().equals("1002"));
        assertTrue(body.getErrMsg().equals("Database Exploded!"));
    }

    @Test
    public void getRewardsForCustomers_AnyException() {
        LocalDate fromDate = LocalDate.now().minusDays(62);
        LocalDate toDate = LocalDate.now() ;
        when(transactionService.getListOfTransactionsBetween(fromDate, toDate)).thenReturn(Collections.singletonList(transaction));
        when(transaction.getCustomer()).thenThrow(new RuntimeException("Unexpected Exception!"));
        ResponseEntity<Error> response = testRestTemplate.getForEntity("/rewards-service/rewards?fromDate="+fromDate+"&toDate="+toDate, Error.class);
        assertEquals(response.getStatusCode().value(), 500);
        Error body = response.getBody();
        assertTrue(body.getErrCode().equals("1000"));
        assertTrue(body.getErrMsg().equals("Unexpected Exception!"));
    }

    @Test
    public void getRewardsForCustomers_success_all_2() {
        LocalDate fromDate = LocalDate.now().minusDays(90);
        LocalDate toDate = LocalDate.now();
        ResponseEntity<RewardsResponse> response = testRestTemplate.getForEntity("/rewards-service/rewards?fromDate="+fromDate+"&toDate="+toDate, RewardsResponse.class);
        assertEquals(response.getStatusCode().value(), 200);
        RewardsResponse body = response.getBody();
        assertEquals(body.getRewards().size(), 2);
        RewardsVO vo1 = body.getRewards().get(0);
        RewardsVO vo2 = body.getRewards().get(1);

        assertEquals(vo1.getTotalPoints(), 1250);
        assertEquals(vo2.getTotalPoints(), 4710);
    }

    @Test
    public void getRewardsForCustomers_success_given_case_90_points_for_$120_spent() {
        String fromDate = LocalDate.now() + "";
        String toDate = LocalDate.now() + "";
        ResponseEntity<RewardsResponse> response = testRestTemplate.getForEntity("/rewards-service/rewards?fromDate="+fromDate+"&toDate="+toDate, RewardsResponse.class);
        assertEquals(response.getStatusCode().value(), 200);
        RewardsResponse body = response.getBody();
        assertEquals(body.getRewards().size(), 2);
        RewardsVO vo1 = body.getRewards().get(0);
        RewardsVO vo2 = body.getRewards().get(1);

        assertEquals(vo1.getTotalPoints(), 90);
        assertEquals(vo2.getTotalPoints(), 10);
    }
}
