package com.test.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentPartnerDao {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public int getPartnerByName(String paymentName) {
		int partnerId = (Integer) jdbcTemplate.queryForObject("SELECT ID FROM PAYMENT_PARTNER WHERE NAME=?",
				new Object[] { paymentName }, Integer.class);
		return partnerId;
	}

}
