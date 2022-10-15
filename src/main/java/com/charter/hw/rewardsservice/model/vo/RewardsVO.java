package com.charter.hw.rewardsservice.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "RewardsVO", description = "Rewards Value Object")
public class RewardsVO {
    @ApiModelProperty(value = "Unique identifier for customer")
    private String customerId;
    @ApiModelProperty(value = "Points earned per month")
    private Map<String, Integer> points;
    @ApiModelProperty(value = "Aggregated points")
    private Integer totalPoints;
}
