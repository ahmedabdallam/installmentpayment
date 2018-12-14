package com.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.test.model.CustomTransaction;
import com.test.model.TransactionLog;
import com.test.service.TransactionService;

@RestController
public class TransactionController {

	@Autowired
	TransactionService transactionService;
	CustomTransaction customerTransaction;

	@PostMapping("/transactionLog")
	public void addNewTransaction(@RequestBody TransactionLog transactionLog) {
		transactionService.addTransaction(transactionLog);
	}

	@PostMapping("/transactionLogNew")
	public void addNewTransactionByNames(@RequestBody CustomTransaction customTransaction) {
		transactionService.addTransaction(customTransaction);
	}

}
