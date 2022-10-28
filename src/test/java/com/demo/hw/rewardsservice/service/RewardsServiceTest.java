package com.demo.hw.rewardsservice.service;

import com.demo.hw.rewardsservice.exceptions.BusinessException;
import com.demo.hw.rewardsservice.exceptions.SQLDataException;
import com.demo.hw.rewardsservice.model.Customer;
import com.demo.hw.rewardsservice.model.Transaction;
import com.demo.hw.rewardsservice.model.vo.RewardsResponse;
import com.demo.hw.rewardsservice.model.vo.RewardsVO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RewardsServiceTest {

    @Mock
    TransactionService transactionService;

    @ParameterizedTest
    @MethodSource("getValuesForSingleTransaction")
    public void testGetRewardsForDates_given_case(LocalDate fromDate, LocalDate toDate, List<Transaction> transactions, int totalPoints) {
        RewardsService service = new RewardsService(transactionService);
        when(transactionService.getListOfTransactionsBetween(fromDate, toDate)).thenReturn(transactions);
        RewardsResponse response = service.getRewardsForDatesBetween(fromDate, toDate);
        assertEquals(response.getRewards().size(), 1);
        RewardsVO vo1 = response.getRewards().get(0);
        assertEquals(totalPoints, vo1.getTotalPoints());
        assertEquals(totalPoints, vo1.getPoints().get(fromDate.getMonth().name()));
    }

    @ParameterizedTest
    @MethodSource("getValuesForSingleTransaction")
    public void testGetRewardsForDates_empty_list(LocalDate fromDate, LocalDate toDate, List<Transaction> transactions, int totalPoints) {
        RewardsService service = new RewardsService(transactionService);
        when(transactionService.getListOfTransactionsBetween(fromDate, toDate)).thenReturn(Collections.emptyList());
        RewardsResponse response = service.getRewardsForDatesBetween(fromDate, toDate);
        assertEquals(response.getRewards().size(), 0);
    }

    @ParameterizedTest
    @MethodSource("getValuesForMultipleTransactions")
    public void testGetRewardsForDates_multipleCases_success(LocalDate fromDate, LocalDate toDate, List<Transaction> transactions, int totalPoints) {
        RewardsService service = new RewardsService(transactionService);
        when(transactionService.getListOfTransactionsBetween(fromDate, toDate)).thenReturn(transactions);
        RewardsResponse response = service.getRewardsForDatesBetween(fromDate, toDate);
        assertEquals(response.getRewards().size(), 1);
        RewardsVO vo1 = response.getRewards().get(0);
        assertEquals(totalPoints, vo1.getTotalPoints());

    }

    public static Object[] getValuesForSingleTransaction() {
        return new Object[] {
                new Object[] {LocalDate.now(), LocalDate.now(), getTransactions(), 90}
        };
    }

    public static List<Transaction> getTransactions() {
        Customer customer = new Customer("Fenil", "Shah", "address_1");
        Transaction transaction = new Transaction(LocalDateTime.now(), new BigDecimal("120"), "XYZ", "Grocery Shopping", customer);
        return Collections.singletonList(transaction);
    }

    public static Object[] getValuesForMultipleTransactions() {
        return new Object[] {
                new Object[] {LocalDate.now(), LocalDate.now(), getMultipleTransactions1(), 1250},
                new Object[] {LocalDate.now().plusDays(5), LocalDate.now().plusDays(10), getMultipleTransactions2(), 41100},
                new Object[] {LocalDate.now().minusDays(90), LocalDate.now(), getMultipleTransactions3(), 50},
                new Object[] {LocalDate.now().minusDays(120), LocalDate.now().minusDays(30), getMultipleTransactions4(), 1},
        };
    }

    public static List<Transaction> getMultipleTransactions1() {
        Customer customer1 = new Customer("Fenil", "Shah", "address_1");
        customer1.setId(1L);
        Transaction transaction1 = new Transaction(LocalDateTime.now(), new BigDecimal("120"), "XYZ", "Grocery Shopping", customer1);
        Transaction transaction2 = new Transaction(LocalDateTime.now().minusDays(5), new BigDecimal("60"), "XYZ", "Grocery Shopping", customer1);
        Transaction transaction3 = new Transaction(LocalDateTime.now().minusDays(10), new BigDecimal("150"), "ABC", "Online Shopping", customer1);
        Transaction transaction4 = new Transaction(LocalDateTime.now().minusDays(17), new BigDecimal("200"), "PQR", "Restaurant dinner", customer1);
        Transaction transaction5 = new Transaction(LocalDateTime.now().minusDays(31), new BigDecimal("175"), "ABC", "Online Shopping", customer1);
        Transaction transaction6 = new Transaction(LocalDateTime.now().minusDays(60), new BigDecimal("350"), "UVW", "Furniture Shopping", customer1);

        return Arrays.asList(transaction1, transaction2, transaction3, transaction4, transaction5, transaction6);
    }

    public static List<Transaction> getMultipleTransactions2() {
        Customer customer1 = new Customer("Fenil", "Shah", "address_1");
        customer1.setId(1L);
        Transaction transaction1 = new Transaction(LocalDateTime.now(), new BigDecimal("1000"), "XYZ", "Grocery Shopping", customer1);
        Transaction transaction2 = new Transaction(LocalDateTime.now().minusDays(5), new BigDecimal("2000"), "XYZ", "Grocery Shopping", customer1);
        Transaction transaction3 = new Transaction(LocalDateTime.now().minusDays(10), new BigDecimal("3000"), "ABC", "Online Shopping", customer1);
        Transaction transaction4 = new Transaction(LocalDateTime.now().minusDays(17), new BigDecimal("4000"), "PQR", "Restaurant dinner", customer1);
        Transaction transaction5 = new Transaction(LocalDateTime.now().minusDays(31), new BigDecimal("5000"), "ABC", "Online Shopping", customer1);
        Transaction transaction6 = new Transaction(LocalDateTime.now().minusDays(60), new BigDecimal("6000"), "UVW", "Furniture Shopping", customer1);

        return Arrays.asList(transaction1, transaction2, transaction3, transaction4, transaction5, transaction6);
    }

    public static List<Transaction> getMultipleTransactions3() {
        Customer customer1 = new Customer("Fenil", "Shah", "address_1");
        customer1.setId(1L);
        Transaction transaction1 = new Transaction(LocalDateTime.now(), new BigDecimal("100"), "XYZ", "Grocery Shopping", customer1);
        return Arrays.asList(transaction1);
    }

    public static List<Transaction> getMultipleTransactions4() {
        Customer customer1 = new Customer("Fenil", "Shah", "address_1");
        customer1.setId(1L);
        Transaction transaction1 = new Transaction(LocalDateTime.now(), new BigDecimal("1"), "XYZ", "Grocery Shopping", customer1);
        Transaction transaction2 = new Transaction(LocalDateTime.now().minusDays(5), new BigDecimal("2"), "XYZ", "Grocery Shopping", customer1);
        Transaction transaction3 = new Transaction(LocalDateTime.now().minusDays(10), new BigDecimal("3"), "ABC", "Online Shopping", customer1);
        Transaction transaction4 = new Transaction(LocalDateTime.now().minusDays(17), new BigDecimal("4"), "PQR", "Restaurant dinner", customer1);
        Transaction transaction5 = new Transaction(LocalDateTime.now().minusDays(31), new BigDecimal("5"), "ABC", "Online Shopping", customer1);
        Transaction transaction6 = new Transaction(LocalDateTime.now().minusDays(60), new BigDecimal("51"), "UVW", "Furniture Shopping", customer1);

        return Arrays.asList(transaction1, transaction2, transaction3, transaction4, transaction5, transaction6);
    }

    @Test
    public void testGetRewardsForDates_business_exception() {
        RewardsService service = new RewardsService(transactionService);
        assertThrows(BusinessException.class,
                () -> service.getRewardsForDatesBetween(LocalDate.now(), LocalDate.now().minusDays(1)));

    }

    @Test
    public void testGetRewardsForDates_sql_exception() {
        RewardsService service = new RewardsService(transactionService);
        when(transactionService.getListOfTransactionsBetween(any(), any())).thenThrow(new RuntimeException("Error!"));
        assertThrows(SQLDataException.class,
                () -> service.getRewardsForDatesBetween(LocalDate.now(), LocalDate.now()));

    }

}