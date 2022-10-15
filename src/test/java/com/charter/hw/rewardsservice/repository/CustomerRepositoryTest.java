package com.charter.hw.rewardsservice.repository;

import com.charter.hw.rewardsservice.model.Customer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class CustomerRepositoryTest {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void contextLoad() {
        assertNotNull(dataSource);
    }

    @Test
    public void validCustomers() {
        List<Customer> customers = customerRepository.findAll();
        assertEquals(customers.size(), 2);
    }
}