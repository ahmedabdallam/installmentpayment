package com.test.model;

import java.sql.Date;

public class Installment {

	private int id;
	private double amount;
	private int customerId;
	private int paymentId;
	private Date installmentDueDate;
	private Date paidDate;
	public Date getPaidDate() {
		return paidDate;
	}

	public void setPaidDate(Date paidDate) {
		this.paidDate = paidDate;
	}

	private String paidFlag;
	private String delayFlag;

	public Date getInstallmentDueDate() {
		return installmentDueDate;
	}

	public void setInstallmentDueDate(Date installmentDueDate) {
		this.installmentDueDate = installmentDueDate;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}

	public String getPaidFlag() {
		return paidFlag;
	}

	public void setPaidFlag(String paidFlag) {
		this.paidFlag = paidFlag;
	}

	public String getDelayFlag() {
		return delayFlag;
	}

	public void setDelayFlag(String delayFlag) {
		this.delayFlag = delayFlag;
	}
}
