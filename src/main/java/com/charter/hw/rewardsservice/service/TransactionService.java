package com.charter.hw.rewardsservice.service;

import com.charter.hw.rewardsservice.model.Transaction;
import com.charter.hw.rewardsservice.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class TransactionService {

    private TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> getListOfTransactionsBetween(LocalDate fromDate, LocalDate toDate) {
        return transactionRepository.findAllByTransactionDateBetweenDates(fromDate.atStartOfDay(), toDate.atTime(LocalTime.MAX));
    }
}
