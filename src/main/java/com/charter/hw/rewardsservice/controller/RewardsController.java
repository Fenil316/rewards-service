package com.charter.hw.rewardsservice.controller;

import com.charter.hw.rewardsservice.model.Error;
import com.charter.hw.rewardsservice.model.vo.RewardsResponse;
import com.charter.hw.rewardsservice.service.RewardsService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("rewards-service")
public class RewardsController {

    private RewardsService rewardsService;

    public RewardsController (RewardsService rewardsService) {
        this.rewardsService = rewardsService;
    }

    @GetMapping("rewards")
    public RewardsResponse getRewardsBetween(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
                                             @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate) {
        return rewardsService.getRewardsForDatesBetween(fromDate, toDate);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Error> handleMissingParams(MissingServletRequestParameterException ex) {
        String name = ex.getParameterName();
        return ResponseEntity.badRequest().body(new Error("400", name + " parameter is missing"));
    }
}
