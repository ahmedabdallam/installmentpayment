package com.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.dao.CustomerDao;
import com.test.dao.InstallmentDao;
import com.test.dao.PaymentDao;
import com.test.dao.PaymentPartnerDao;
import com.test.model.CustomPayment;
import com.test.model.Installment;
import com.test.model.Payment;

@Service
public class PaymentService {

	@Autowired
	PaymentDao paymentDao;

	@Autowired
	CustomerDao customerDao;

	@Autowired
	InstallmentDao installmentDao;

	@Autowired
	PaymentPartnerDao partnerDao;

	public void addNewPayment(CustomPayment customPayment) {
		int customerId = customerDao.getCustomerByPhone(customPayment.getCustomerPhoneNumber());
		Installment installment = installmentDao.getNextUnpaidInstallment(customerId);
		int partnerId = partnerDao.getPartnerByName(customPayment.getPartnerName());

		Payment payment = new Payment();
		payment.setCustomerId(customerId);
		payment.setPartnerId(partnerId);
		payment.setInstallmentId(installment.getId());
		payment.setAmount(new Double(customPayment.getAmount()));
		System.out.println("Installment ID is >>" + installment.getId());
		addNewPayment(payment);
	}

	public void addNewPayment(Payment payment) {
		paymentDao.addNewPayment(payment);
		int paymentId = paymentDao.getCurrentPaymentId(payment);
		payment.setId(paymentId);
		installmentDao.addInstallmentPayment(payment);
	}

	public int getCurrentPaymentId(Payment payment) {
		return paymentDao.getCurrentPaymentId(payment);
	}
}
