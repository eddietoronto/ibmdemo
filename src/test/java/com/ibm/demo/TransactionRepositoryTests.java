package com.ibm.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.ibm.demo.model.Transaction;
import com.ibm.demo.repository.BalanceRepository;
import com.ibm.demo.repository.TransactionRepository;

//@SpringBootTest
@DataMongoTest
public class TransactionRepositoryTests {
	
	TransactionRepository repository;
	
    @Autowired
    public void setRepository(TransactionRepository repository) {
        this.repository = repository;
    }

	@Test
	public void findByAccountNum() {

		List<Transaction> result = repository.findByAccountNumber("abc");

		assertThat(result).extracting("accountNumber").contains("abc");
	}
}
