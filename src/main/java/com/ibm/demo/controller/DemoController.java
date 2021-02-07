package com.ibm.demo.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.demo.model.Balance;
import com.ibm.demo.model.Transaction;
import com.ibm.demo.service.BalanceTransactionService;

@RestController
public class DemoController {

	private BalanceTransactionService service;
	
	public DemoController(BalanceTransactionService service) {
		this.service = service;
	}

	@RequestMapping("/getLastBalance")
	public Balance getLastBalance(@RequestParam("accountNum") String accountNum) {
		return service.getLastBalance(accountNum);
	}
	
	@RequestMapping("/getTransactions")
	public List<Transaction> getTransactions(
			@RequestParam("accountNum") String accountNum,
			@RequestParam("from") Optional<String> from,
			@RequestParam("to") Optional<String> to,
			@RequestParam("type") Optional<String> type,
			@RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size) {
		
		return service.getTransactions(accountNum, from.orElse(""), to.orElse(""), type.orElse(""), page.orElse(0), size.orElse(10));
	}


	@RequestMapping("/")
	public String index() {
		return "Greetings from Spring Boot!";
	}

	@RequestMapping("/test")
	@ResponseBody
	public String test() {
		return "Greetings from Spring Boot!!!";
	}
}
