package com.test.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.test.model.PenaltyFees;

@Repository
public class PenaltyFeesDao {

	@Autowired
	JdbcTemplate jdbcTemplate;

	String addNewPenaltyFees = "INSERT INTO PENALTY_FEES (ID, AMOUNT, CUSTOMER_ID, PAYMENT_ID, INSTALLMENT_ID, ORIGINAL_AMOUNT) "
			+ "VALUES(PENALTY_FEES_SEQ.NEXTVAL,?,?,?,?,?)";

	public void addNewPenaltyFees(PenaltyFees penaltyFees) {
		jdbcTemplate.update(addNewPenaltyFees, penaltyFees.getAmount(), penaltyFees.getCustomerId(),
				penaltyFees.getPaymentId(), penaltyFees.getInstallmentId(), penaltyFees.getOriginalAmount());
	}
}
