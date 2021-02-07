package com.ibm.demo.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ibm.demo.model.Balance;

@Repository
public interface BalanceRepository extends MongoRepository<Balance, String> {
	public List<Balance> findByAccountNumberOrderByLastUpdateTimestampDesc(String AccountNum);
	public List<Balance> findByAccountNumberOrderByLastUpdateTimestampDesc(String AccountNum, Pageable pageable);
	public Balance findLastByAccountNumber(String AccountNum);
}
