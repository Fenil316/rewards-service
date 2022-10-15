package com.charter.hw.rewardsservice.controller;

import com.charter.hw.rewardsservice.model.Error;
import com.charter.hw.rewardsservice.model.vo.RewardsResponse;
import com.charter.hw.rewardsservice.service.RewardsService;
import io.swagger.annotations.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Api(tags = {"Get Rewards for Customers"}, value = "Rewards Service Endpoint")
@RestController
@RequestMapping("rewards-service")
public class RewardsController {

    private RewardsService rewardsService;

    public RewardsController (RewardsService rewardsService) {
        this.rewardsService = rewardsService;
    }

    @ApiOperation(value = "Get reward points for all the customers within a given date range")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved", response = RewardsResponse.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "Bad request error"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping("rewards")
    public RewardsResponse getRewardsBetween(@ApiParam(value = "From Date eg: 2022-08-30") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
                                             @ApiParam(value = "To Date eg: 2022-10-12") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate) {
        return rewardsService.getRewardsForDatesBetween(fromDate, toDate);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Error> handleMissingParams(MissingServletRequestParameterException ex) {
        String name = ex.getParameterName();
        return ResponseEntity.badRequest().body(new Error("400", name + " parameter is missing"));
    }
}
