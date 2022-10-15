package com.charter.hw.rewardsservice.repository;

import com.charter.hw.rewardsservice.model.Transaction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class TransactionRepositoryTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    public void contextLoad() {
        assertNotNull(dataSource);
    }

    @ParameterizedTest
    @MethodSource("getDateParamsForTransactions")
    public void getTransactionsForPreLoadedData(LocalDateTime fromDate, LocalDateTime toDate, int noOfTransactions) {
        List<Transaction> transactions = transactionRepository.findAllByTransactionDateBetweenDates(fromDate, toDate);
        assertEquals(noOfTransactions, transactions.size());
    }

    public static Object[] getDateParamsForTransactions() {
        LocalDate date = LocalDate.now();
        return new Object[] {
                new Object[] {date.atStartOfDay(), date.atTime(LocalTime.MAX), 2},
                new Object[] {date.plusDays(5).atStartOfDay(), LocalDate.now().plusDays(10).atTime(LocalTime.MAX), 0},
        };
    }

}