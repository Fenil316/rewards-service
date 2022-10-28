package com.demo.hw.rewardsservice.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "RewardsResponse", description = "List of Rewards")
public class RewardsResponse {
    @ApiModelProperty(value = "List containing rewards per month for each customer")
    private List<RewardsVO> rewards;
}
