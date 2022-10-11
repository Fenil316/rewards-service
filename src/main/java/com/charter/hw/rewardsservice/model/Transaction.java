package com.charter.hw.rewardsservice.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "transaction")
@NoArgsConstructor
@Getter
@Setter
public class Transaction {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long transactionId;
    private LocalDateTime transactionDate;
    private BigDecimal amount;
    private String transactionCode;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    public Transaction (LocalDateTime transactionDate, BigDecimal amount, String transactionCode, String description, Customer customer) {
        this.transactionDate = transactionDate;
        this.amount = amount;
        this.transactionCode = transactionCode;
        this.description = description;
        this.customer = customer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(transactionId, that.transactionId) && Objects.equals(transactionDate, that.transactionDate) && Objects.equals(amount, that.amount) && Objects.equals(transactionCode, that.transactionCode) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId, transactionDate, amount, transactionCode, description);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", transactionDate=" + transactionDate +
                ", amount=" + amount +
                ", transactionCode='" + transactionCode + '\'' +
                ", description='" + description + '\'' +
                ", customer=" + customer.getId() +
                '}';
    }
}
