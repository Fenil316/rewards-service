package com.charter.hw.rewardsservice;

import com.charter.hw.rewardsservice.model.Customer;
import com.charter.hw.rewardsservice.model.Transaction;
import com.charter.hw.rewardsservice.repository.CustomerRepository;
import com.charter.hw.rewardsservice.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
@Slf4j
public class RewardsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RewardsServiceApplication.class, args);
	}

	@Bean
	@Profile("!prod")
	public CommandLineRunner loadData(CustomerRepository repository, TransactionRepository transactionRepository) {
		return (args) -> {
			// save a few customers
			Customer customer1 = new Customer("Jack", "Bauer", "address1");
			repository.save(customer1);
			transactionRepository.save(new Transaction(LocalDateTime.now(), new BigDecimal("120"), "XYZ", "Grocery Shopping", customer1));
			transactionRepository.save(new Transaction(LocalDateTime.now().minusDays(5), new BigDecimal("60"), "XYZ", "Grocery Shopping", customer1));
			transactionRepository.save(new Transaction(LocalDateTime.now().minusDays(10), new BigDecimal("150"), "ABC", "Online Shopping", customer1));
			transactionRepository.save(new Transaction(LocalDateTime.now().minusDays(17), new BigDecimal("200"), "PQR", "Restaurant dinner", customer1));
			transactionRepository.save(new Transaction(LocalDateTime.now().minusDays(31), new BigDecimal("175"), "ABC", "Online Shopping", customer1));
			transactionRepository.save(new Transaction(LocalDateTime.now().minusDays(60), new BigDecimal("350"), "UVW", "Furniture Shopping", customer1));


			Customer customer2 = new Customer("John", "Doe", "address2");
			repository.save(customer2);
			transactionRepository.save(new Transaction(LocalDateTime.now(), new BigDecimal("60"), "XYZ", "Grocery Shopping", customer2));
			transactionRepository.save(new Transaction(LocalDateTime.now().minusDays(7), new BigDecimal("500"), "XYZ", "Grocery Shopping", customer2));
			transactionRepository.save(new Transaction(LocalDateTime.now().minusDays(14), new BigDecimal("700"), "ABC", "Online Shopping", customer2));
			transactionRepository.save(new Transaction(LocalDateTime.now().minusDays(21), new BigDecimal("50"), "PQR", "Restaurant dinner", customer2));
			transactionRepository.save(new Transaction(LocalDateTime.now().minusDays(28), new BigDecimal("450"), "ABC", "Online Shopping", customer2));
			transactionRepository.save(new Transaction(LocalDateTime.now().minusDays(70), new BigDecimal("1000"), "UVW", "Furniture Shopping", customer2));

			// fetch all customers
			log.info("Customers found with findAll():");
			log.info("-------------------------------");
			List<Customer> customers = repository.findAll();
			for (Customer customer : customers) {
				log.info(customer.toString());
			}
		};
	}

}
