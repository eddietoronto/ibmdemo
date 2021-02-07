package com.ibm.demo.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ibm.demo.model.Balance;
import com.ibm.demo.model.Transaction;
import com.ibm.demo.model.TransactionType;
import com.ibm.demo.repository.BalanceRepository;
import com.ibm.demo.repository.TransactionRepository;

@Service
public class BalanceTransactionServiceImpl implements BalanceTransactionService {

	public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private final BalanceRepository balanceRepository;
	private final TransactionRepository transactionRepository;

	public BalanceTransactionServiceImpl(BalanceRepository balanceRepository,
			TransactionRepository transactionRepository) {
		this.balanceRepository = balanceRepository;
		this.transactionRepository = transactionRepository;
	}

	@Override
	public List<Transaction> getTransactions(String acctNum, String from, String to, int page, int size) {
		LocalDateTime dateTimeFrom = parseLocalDateTime(from, "00:00:00");
		LocalDateTime dateTimeTo = parseLocalDateTime(to, "23:59:59");
		
		Pageable paging = PageRequest.of(page, size);

		List<Transaction> transactionList = transactionRepository
				.findByAccountNumberAndTransactionTsBetweenOrderByTransactionTsDesc(acctNum, dateTimeFrom, dateTimeTo, paging);
		return transactionList;
	}

	@Override
	public List<Transaction> getTransactions(String acctNum, String from, String to, String transactionType, int page, int size) {
		LocalDateTime dateTimeFrom = parseLocalDateTime(from, "00:00:00");
		LocalDateTime dateTimeTo = parseLocalDateTime(to, "23:59:59");
		TransactionType type;
		try {
			type = TransactionType.valueOf(transactionType);

		} catch (IllegalArgumentException ex) {
			type = null;
		}
		
		Pageable paging = PageRequest.of(page, size);

		List<Transaction> transactionList;
		if (type != null)
			transactionList = transactionRepository
					.findByAccountNumberAndTransactionTsBetweenAndTypeOrderByTransactionTsDesc(acctNum, dateTimeFrom,
							dateTimeTo, type, paging);
		else
			transactionList = transactionRepository.findByAccountNumberAndTransactionTsBetweenOrderByTransactionTsDesc(
					acctNum, dateTimeFrom, dateTimeTo, paging);
		return transactionList;
	}

	@Override
	public List<Balance> getBalance(String acctNum) {

		List<Balance> balanceList = balanceRepository.findByAccountNumberOrderByLastUpdateTimestampDesc(acctNum);
		return balanceList;

	}

	@Override
	public Balance getLastBalance(String acctNum) {

		List<Balance> balanceList = balanceRepository.findByAccountNumberOrderByLastUpdateTimestampDesc(acctNum);
		Optional<Balance> balance = balanceList.stream().findFirst();
		return balance.orElse(null);

	}

	public static LocalDateTime parseLocalDateTime(String strDate, String strTime) {
		LocalDateTime dateTime;

		if (strDate != null) {
			try {
				LocalDate localDate = LocalDate.parse(strDate, formatter);
				LocalTime localTime = LocalTime.parse(strTime);
				dateTime = LocalDateTime.of(localDate, localTime);
			} catch (Exception e) {
				dateTime = LocalDateTime.of(LocalDate.now(), LocalTime.parse(strTime));
			}
		} else {
			dateTime = LocalDateTime.of(LocalDate.now(), LocalTime.parse(strTime));
		}

		return dateTime;
	}

}
