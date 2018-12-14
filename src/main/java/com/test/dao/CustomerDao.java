package com.test.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.test.model.Customer;

@Repository
public class CustomerDao {

	@Autowired
	JdbcTemplate jdbcTemplate;

	String addCustomer = "INSERT INTO CUSTOMER (ID, NAME, PHONE_NUMBER, ADDRESS) "
			+ "VALUES (CUSTOMER_SEQ.NEXTVAL,?,?,?)";

	public void addCustomer(Customer customer) {
		jdbcTemplate.update(addCustomer, customer.getName(), customer.getPhoneNumber(), customer.getAddress());
	}

	public int getCustomerByName(String customerName) {
		int customerId = (Integer) jdbcTemplate.queryForObject("SELECT ID FROM CUSTOMER WHERE Name=?",
				new Object[] { customerName }, Integer.class);
		return customerId;
	}

	public int getCustomerByPhone(String customerPhoneNumber) {
		int customerId = (Integer) jdbcTemplate.queryForObject("SELECT ID FROM CUSTOMER WHERE PHONE_NUMBER=?",
				new Object[] { customerPhoneNumber }, Integer.class);
		return customerId;
	}

}
