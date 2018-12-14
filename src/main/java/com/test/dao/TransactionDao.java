package com.test.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.test.model.TransactionLog;

@Repository
public class TransactionDao {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	CustomerDao customerDao;
	
	@Autowired
	ProductDao productDao;

	String addTransaction = "INSERT INTO TRANSACTION_LOG (ID, TRANSACTION_DATE, CUSTOMER_ID, PRODUCT_ID) "
			+ "VALUES(TRANSACTION_LOG_SEQ.NEXTVAL,SYSDATE,?,?)";

	public void addTransaction(TransactionLog transactionLog) {
		jdbcTemplate.update(addTransaction, transactionLog.getCustomerId(), transactionLog.getProductId());
	}

	public void addTransaction(String customerPhoneNumber, String productName) {
		int customerId = customerDao.getCustomerByPhone(customerPhoneNumber);
		int productId = productDao.getProductByName(productName);
		TransactionLog transactionLog = new TransactionLog();
		transactionLog.setCustomerId(customerId);
		transactionLog.setProductId(productId);
		addTransaction(transactionLog);
	}
}
