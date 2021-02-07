package com.ibm.demo.service;

import java.time.LocalDateTime;
import java.util.List;

import com.ibm.demo.model.Balance;
import com.ibm.demo.model.Transaction;

public interface BalanceTransactionService {
	List<Transaction> getTransactions(String acctNum, String from, String to, int page, int size);
	List<Transaction> getTransactions(String acctNum, String from, String to, String transactionType, int page, int size);
	List<Balance> getBalance(String acctNum);
	Balance getLastBalance(String acctNum);
	
}
