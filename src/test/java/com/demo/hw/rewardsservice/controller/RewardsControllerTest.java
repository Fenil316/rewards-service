package com.demo.hw.rewardsservice.controller;

import com.demo.hw.rewardsservice.exceptions.BusinessException;
import com.demo.hw.rewardsservice.exceptions.SQLDataException;
import com.demo.hw.rewardsservice.model.vo.RewardsResponse;
import com.demo.hw.rewardsservice.model.vo.RewardsVO;
import com.demo.hw.rewardsservice.service.RewardsService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;

import static org.mockito.Mockito.when;

@WebMvcTest(RewardsController.class)
class RewardsControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    private RewardsService rewardsService;

    @MockBean
    private CommandLineRunner commandLineRunner;

    @Test
    public void testGetRewardsBetween() throws Exception {
        RewardsVO rewardsVO = new RewardsVO("1", null, 100);
        RewardsResponse response = new RewardsResponse(Collections.singletonList(rewardsVO));
        when(rewardsService.getRewardsForDatesBetween(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(response);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/rewards-service/rewards?fromDate=2022-08-10&toDate=2022-10-11"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.rewards[0].customerId").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.rewards[0].totalPoints").value("100"));

    }

    @Test
    public void testGetRewardsBetween_Error_400_toDate_missing() throws Exception {
        RewardsVO rewardsVO = new RewardsVO("1", null, 100);
        RewardsResponse response = new RewardsResponse(Collections.singletonList(rewardsVO));
        when(rewardsService.getRewardsForDatesBetween(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(response);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/rewards-service/rewards?fromDate=2022-08-10"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errCode").value("400"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errMsg").value("toDate parameter is missing"));

    }

    @Test
    public void testGetRewardsBetween_Error_400_fromDate_missing() throws Exception {
        RewardsVO rewardsVO = new RewardsVO("1", null, 100);
        RewardsResponse response = new RewardsResponse(Collections.singletonList(rewardsVO));
        when(rewardsService.getRewardsForDatesBetween(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(response);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/rewards-service/rewards?toDate=2022-08-10"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errCode").value("400"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errMsg").value("fromDate parameter is missing"));

    }

    @Test
    public void testGetRewardsBetween_Error_500_UnexpectedError() throws Exception {
        when(rewardsService.getRewardsForDatesBetween(ArgumentMatchers.any(), ArgumentMatchers.any())).thenThrow(new RuntimeException("Unexpected Error!"));
        this.mockMvc.perform(MockMvcRequestBuilders.get("/rewards-service/rewards?fromDate=2022-08-10&toDate=2022-10-11"))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errCode").value("1000"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errMsg").value("Unexpected Error!"));

    }

    @Test
    public void testGetRewardsBetween_Error_400_BusinessException() throws Exception {
        when(rewardsService.getRewardsForDatesBetween(ArgumentMatchers.any(), ArgumentMatchers.any())).thenThrow(new BusinessException("fromDate cannot be after toDate"));
        this.mockMvc.perform(MockMvcRequestBuilders.get("/rewards-service/rewards?fromDate=2022-08-10&toDate=2022-10-11"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errCode").value("1001"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errMsg").value("fromDate cannot be after toDate"));

    }

    @Test
    public void testGetRewardsBetween_Error_500_SQLDataException() throws Exception {
        when(rewardsService.getRewardsForDatesBetween(ArgumentMatchers.any(), ArgumentMatchers.any())).thenThrow(new SQLDataException("Database Explosion!"));
        this.mockMvc.perform(MockMvcRequestBuilders.get("/rewards-service/rewards?fromDate=2022-08-10&toDate=2022-10-11"))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errCode").value("1002"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errMsg").value("Database Explosion!"));

    }

}