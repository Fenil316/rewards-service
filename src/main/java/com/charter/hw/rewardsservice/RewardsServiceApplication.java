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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
@Slf4j
public class RewardsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RewardsServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(CustomerRepository repository, TransactionRepository transactionRepository) {
		return (args) -> {
			// save a few customers
			Customer cust = new Customer("Jack", "Bauer", "address1");
			repository.save(cust);
			transactionRepository.save(new Transaction(LocalDateTime.now(), BigDecimal.ONE, "", "", cust));
			//repository.save(new Customer("Chloe", "O'Brian", "address2"));
			//repository.save(new Customer("Kim", "Bauer", "address3"));
			//repository.save(new Customer("David", "Palmer", "address4"));
			//repository.save(new Customer("Michelle", "Dessler", "address5"));

			// fetch all customers
			log.info("Customers found with findAll():");
			log.info("-------------------------------");
			List<Customer> customers = repository.findAll();
			for (Customer customer : customers) {
				log.info(customer.toString());
			}
			log.info("");

			// fetch an individual customer by ID
			Optional<Customer> customer = repository.findById(1L);
			log.info("Customer found with findById(1L):");
			log.info("--------------------------------");
			log.info(customer.toString());
			log.info("");

			// fetch customers by last name
			log.info("Customer found with findByLastName('Bauer'):");
			log.info("--------------------------------------------");
			/*repository.findByLastName("Bauer").forEach(bauer -> {
				log.info(bauer.toString());
			});*/
			// for (Customer bauer : repository.findByLastName("Bauer")) {
			//  log.info(bauer.toString());
			// }
			log.info("");
		};
	}

}
