package com.ibm.demo.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ibm.demo.model.Transaction;
import com.ibm.demo.model.TransactionType;

@Repository
public interface TransactionRepository extends MongoRepository<Transaction, String> {
	public List<Transaction> findByAccountNumber(String AccountNum);
	public List<Transaction> findByAccountNumberAndTransactionTsBetweenOrderByTransactionTsDesc(String AccountNum, LocalDateTime from, LocalDateTime to, Pageable pageable);
	public List<Transaction> findByAccountNumberAndTransactionTsBetweenAndTypeOrderByTransactionTsDesc(String AccountNum, LocalDateTime from, LocalDateTime to, TransactionType type, Pageable pageable);
}
