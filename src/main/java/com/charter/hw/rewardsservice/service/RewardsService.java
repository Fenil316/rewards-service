package com.charter.hw.rewardsservice.service;

import com.charter.hw.rewardsservice.exceptions.BusinessException;
import com.charter.hw.rewardsservice.exceptions.SQLDataException;
import com.charter.hw.rewardsservice.model.Transaction;
import com.charter.hw.rewardsservice.model.vo.RewardsResponse;
import com.charter.hw.rewardsservice.model.vo.RewardsVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RewardsService {

    private TransactionService transactionService;
    public static final BigDecimal HUNDRED = new BigDecimal(100);
    public static final BigDecimal FIFTY = new BigDecimal(50);

    public RewardsService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public RewardsResponse getRewardsForDatesBetween(LocalDate fromDate, LocalDate toDate) {
        List<Transaction> transactionList;
        validateDates(fromDate, toDate);
        try {
            transactionList = transactionService.getListOfTransactionsBetween(fromDate, toDate);
        } catch (Exception e) {
            log.error("Error occurred while getting TransactionData", e);
            throw new SQLDataException(e.getMessage());
        }

        Map<String, List<Transaction>> groupedByCustomers = transactionList.stream().collect(Collectors.groupingBy(t -> t.getCustomer().getId() + ""));
        RewardsResponse response = transformTransactionsToPoints(groupedByCustomers);
        return response;
    }

    private void validateDates(LocalDate fromDate, LocalDate toDate) {
        if(fromDate.isAfter(toDate))
            throw new BusinessException("fromDate cannot be after toDate");
    }

    private RewardsResponse transformTransactionsToPoints(Map<String, List<Transaction>> groupedByCustomers) {
        List<RewardsVO> rewardsVOs = new ArrayList<>();
        for(Map.Entry<String, List<Transaction>> entry: groupedByCustomers.entrySet()) { // Iterate for each customer
            List<Transaction> transactions = entry.getValue();
            Map<String, Integer> pointsByMonth = new LinkedHashMap<>();
            int totalPoints;
            for(Transaction transaction : transactions) {
                int pointsForThisTransaction = calculatePointsForTransaction(transaction); // Calculate the points earned for each transaction
                String month = transaction.getTransactionDate().getMonth().name();
                if(pointsByMonth.containsKey(month)) {
                    totalPoints = pointsByMonth.get(month);
                    int newPoints = totalPoints + pointsForThisTransaction;
                    pointsByMonth.put(month, newPoints);
                } else {
                    pointsByMonth.put(month, pointsForThisTransaction);
                }
            }
            RewardsVO rewardsVO = new RewardsVO();
            rewardsVO.setCustomerId(entry.getKey());
            rewardsVO.setPoints(pointsByMonth);
            rewardsVO.setTotalPoints(pointsByMonth.values().stream().reduce(0, Integer::sum));
            rewardsVOs.add(rewardsVO); // Add each customer data as a VO
        }

        return new RewardsResponse(rewardsVOs);
    }

    private int calculatePointsForTransaction(Transaction transaction) {
        int points = 0;
        BigDecimal dollarAmount = transaction.getAmount();
        if(dollarAmount.compareTo(FIFTY) > 0) {
            if(dollarAmount.compareTo(FIFTY) > 0 && dollarAmount.compareTo(HUNDRED) <= 0) {
                points = dollarAmount.subtract(FIFTY).intValue();
            }
            if(dollarAmount.compareTo(HUNDRED) > 0) {
                points = dollarAmount.subtract(HUNDRED).intValue() * 2 + 50;
            }
        }
        return points;
    }
}
