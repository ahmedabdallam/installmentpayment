package com.test.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.test.model.Installment;
import com.test.model.Payment;

@Repository
public class InstallmentDao {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	CustomerDao customerDao;

	String addInstallment = "INSERT INTO INSTALLMENTS (ID, AMOUNT, INSTALLMENT_DUE_DATE, CUSTOMER_ID, PAYMENT_ID, PAID_FLAG, DELAYED_FLAG) "
			+ "VALUES(INSTALLMENT_SEQ.NEXTVAL,?,?,?,NULL,'N','N')";

	String getAllInstallments = "";

	String getInstallmentsByCustomer = "SELECT * FROM INSTALLMENTS WHERE CUSTOMER_ID=? AND PAID_FLAG='N'";

	public void addInstallment(Installment installment) {
		jdbcTemplate.update(addInstallment, installment.getAmount(), installment.getInstallmentDueDate(),
				installment.getCustomerId());

	}

	public List<Installment> getInstallmentsByCustomer(int customerId) {
		List<Installment> installments = jdbcTemplate
				.query("SELECT * FROM INSTALLMENTS WHERE CUSTOMER_ID=" + customerId + "", new RowMapper<Installment>() {
					@Override
					public Installment mapRow(ResultSet rs, int rowNum) throws SQLException {
						Installment installment = new Installment();
						installment.setId(rs.getInt("ID"));
						installment.setAmount(rs.getDouble("AMOUNT"));
						installment.setInstallmentDueDate(rs.getDate("INSTALLMENT_DUE_DATE"));
						installment.setCustomerId(rs.getInt("CUSTOMER_ID"));
						installment.setPaymentId(rs.getInt("PAYMENT_ID"));
						installment.setPaidFlag(rs.getString("PAID_FLAG"));
						installment.setDelayFlag(rs.getString("DELAYED_FLAG"));
						return installment;
					}
				});
		return installments;
	}

	public List<Installment> getNonPaidInstallmentsByCustomer(int customerId) {
		List<Installment> installments = jdbcTemplate.query(
				"SELECT * FROM INSTALLMENTS WHERE CUSTOMER_ID=" + customerId + " AND PAID_FLAG='N'",
				new RowMapper<Installment>() {
					@Override
					public Installment mapRow(ResultSet rs, int rowNum) throws SQLException {
						Installment installment = new Installment();
						installment.setId(rs.getInt("ID"));
						installment.setAmount(rs.getDouble("AMOUNT"));
						installment.setInstallmentDueDate(rs.getDate("INSTALLMENT_DUE_DATE"));
						installment.setCustomerId(rs.getInt("CUSTOMER_ID"));
						installment.setPaymentId(rs.getInt("PAYMENT_ID"));
						installment.setPaidFlag(rs.getString("PAID_FLAG"));
						installment.setDelayFlag(rs.getString("DELAYED_FLAG"));
						return installment;
					}
				});
		return installments;
	}

	public void updateCurrentInstallment(int installmentId, double newAmount) {
		jdbcTemplate.update("UPDATE INSTALLMENTS SET AMOUNT=? WHERE ID=?", newAmount, installmentId);
	}

	public void addInstallmentPayment(Payment payment) {
		jdbcTemplate.update("UPDATE INSTALLMENTS SET PAYMENT_ID=?, PAID_FLAG='Y' WHERE CUSTOMER_ID=? AND ID=?",
				payment.getId(), payment.getCustomerId(), payment.getInstallmentId());
	}

	public Installment getNextUnpaidInstallment(int customerId) {
		List<Installment> installments = jdbcTemplate.query(
				"SELECT * FROM INSTALLMENTS WHERE ID =(SELECT MIN(ID) FROM INSTALLMENTS WHERE PAID_FLAG='N' AND CUSTOMER_ID=3)",
				new RowMapper<Installment>() {
					@Override
					public Installment mapRow(ResultSet rs, int rowNum) throws SQLException {
						Installment installment = new Installment();
						installment.setId(rs.getInt("ID"));
						installment.setAmount(rs.getDouble("AMOUNT"));
						installment.setInstallmentDueDate(rs.getDate("INSTALLMENT_DUE_DATE"));
						installment.setCustomerId(rs.getInt("CUSTOMER_ID"));
						installment.setPaymentId(rs.getInt("PAYMENT_ID"));
						installment.setPaidFlag(rs.getString("PAID_FLAG"));
						installment.setDelayFlag(rs.getString("DELAYED_FLAG"));
						return installment;
					}
				});
		return installments.get(0);
	}

	public List<Installment> getNonPaidInstallmentsByCustomerPhone(String customerPhoneNumber) {
		int customerId = customerDao.getCustomerByPhone(customerPhoneNumber);
		List<Installment> installments = jdbcTemplate.query(
				"SELECT * FROM INSTALLMENTS WHERE CUSTOMER_ID=" + customerId + " AND PAID_FLAG='N'",
				new RowMapper<Installment>() {
					@Override
					public Installment mapRow(ResultSet rs, int rowNum) throws SQLException {
						Installment installment = new Installment();
						installment.setId(rs.getInt("ID"));
						installment.setAmount(rs.getDouble("AMOUNT"));
						installment.setInstallmentDueDate(rs.getDate("INSTALLMENT_DUE_DATE"));
						installment.setCustomerId(rs.getInt("CUSTOMER_ID"));
						installment.setPaymentId(rs.getInt("PAYMENT_ID"));
						installment.setPaidFlag(rs.getString("PAID_FLAG"));
						installment.setDelayFlag(rs.getString("DELAYED_FLAG"));
						return installment;
					}
				});
		return installments;
	}
}
