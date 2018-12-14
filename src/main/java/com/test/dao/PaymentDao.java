package com.test.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.test.model.Payment;

@Repository
public class PaymentDao {

	@Autowired
	JdbcTemplate jdbcTemplate;

	String addNewPayment = "INSERT INTO PAYMENT (ID, AMOUNT, CUSTOMER_ID, PARTNER_ID, INSTALLMENT_ID) "
			+ "VALUES (PAYMENT_SEQ.NEXTVAL,?,?,?,?)";

	public void addNewPayment(Payment payment) {
		jdbcTemplate.update(addNewPayment, payment.getAmount(), payment.getCustomerId(), payment.getPartnerId(),
				payment.getInstallmentId());
	}

	public int getCurrentPaymentId(Payment payment) {
		int paymentId = (Integer) jdbcTemplate.queryForObject(
				"SELECT ID FROM PAYMENT WHERE CUSTOMER_ID=? AND PARTNER_ID=? AND INSTALLMENT_ID=?",
				new Object[] { payment.getCustomerId(), payment.getPartnerId(), payment.getInstallmentId() },
				Integer.class);
		return paymentId;
	}

}
