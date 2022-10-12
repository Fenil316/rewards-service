package com.charter.hw.rewardsservice.repository;

import com.charter.hw.rewardsservice.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("select t from Transaction t where t.transactionDate >= :fromDate and t.transactionDate <= :toDate order by t.transactionDate")
    List<Transaction> findAllByTransactionDateBetweenDates(@Param("fromDate") LocalDateTime fromDate,
                                                           @Param("toDate") LocalDateTime toDate);
}
