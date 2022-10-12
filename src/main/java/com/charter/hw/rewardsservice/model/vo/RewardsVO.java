package com.charter.hw.rewardsservice.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RewardsVO {
    private String customerId;
    private Map<String, Integer> points;
    private Integer totalPoints;
}
