package com.test.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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

	public Payment getCurrentPaymentId(Payment payment) {

		List<Payment> payments = jdbcTemplate.query(
				"SELECT * FROM PAYMENT WHERE CUSTOMER_ID=" + payment.getCustomerId() + " AND PARTNER_ID="
						+ payment.getPartnerId() + " AND INSTALLMENT_ID=" + payment.getInstallmentId() + "",
				new RowMapper<Payment>() {
					@Override
					public Payment mapRow(ResultSet rs, int rowNum) throws SQLException {
						Payment payment = new Payment();
						payment.setId(rs.getInt("ID"));
						payment.setAmount(rs.getDouble("AMOUNT"));
						return payment;
					}
				});
		return payments.get(0);
	}

}
