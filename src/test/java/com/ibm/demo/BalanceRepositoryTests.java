package com.ibm.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;

import com.ibm.demo.model.Balance;
import com.ibm.demo.repository.BalanceRepository;

//@SpringBootTest
@DataMongoTest
public class BalanceRepositoryTests {

	private BalanceRepository repository;
	
    @Autowired
    public void setRepository(BalanceRepository repository) {
        this.repository = repository;
    }

	@Test
	public void findsByAccountNum() {

		List<Balance> result = repository.findByAccountNumberOrderByLastUpdateTimestampDesc("abc");

		assertThat(result).extracting("accountNumber").contains("abc");
	}
	
	@Test
	public void findLastByAccoutNumber() {
		Balance result = repository.findLastByAccountNumber("abc");

		assertThat(result).isNotNull();
	}
}
