package com.test.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.dao.CustomerDao;
import com.test.dao.InstallmentDao;
import com.test.dao.PaymentDao;
import com.test.dao.PaymentPartnerDao;
import com.test.dao.PenaltyFeesDao;
import com.test.model.CustomPayment;
import com.test.model.Installment;
import com.test.model.Payment;
import com.test.model.PenaltyFees;

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

	@Autowired
	PenaltyFeesDao penaltyFeesDao;

	public void addNewPayment(CustomPayment customPayment) {
		int customerId = customerDao.getCustomerByPhone(customPayment.getCustomerPhoneNumber());
		Installment installment = installmentDao.getNextUnpaidInstallment(customerId);
		int partnerId = partnerDao.getPartnerByName(customPayment.getPartnerName());

		Payment payment = new Payment();
		payment.setCustomerId(customerId);
		payment.setPartnerId(partnerId);
		payment.setInstallmentId(installment.getId());
		payment.setAmount(new Double(customPayment.getAmount()));

		addNewPayment(payment);
	}

	public void addNewPayment(Payment payment) {
		LocalDate todayDate = LocalDate.now();
		LocalDate installmentDueDate = installmentDao.getNextUnpaidInstallment(payment.getCustomerId())
				.getInstallmentDueDate().toLocalDate();

		paymentDao.addNewPayment(payment);
		Payment currentPayment = paymentDao.getCurrentPaymentId(payment);
		payment.setId(currentPayment.getId());
		installmentDao.addInstallmentPayment(payment);
		if (todayDate.isAfter(installmentDueDate)) {
			addNewPenaltyFees(payment);
			installmentDao.updateDelayedInstallment(payment.getInstallmentId());
		}
	}

	private void addNewPenaltyFees(Payment payment) {

		PenaltyFees penaltyFees = new PenaltyFees();
		penaltyFees.setAmount(50);
		penaltyFees.setCustomerId(payment.getCustomerId());
		penaltyFees.setPaymentId(payment.getId());
		penaltyFees.setInstallmentId(payment.getInstallmentId());
		penaltyFees.setOriginalAmount(installmentDao.getAmountByInstallmentId(payment.getInstallmentId()).getAmount());

		penaltyFeesDao.addNewPenaltyFees(penaltyFees);
	}

	public Payment getCurrentPaymentId(Payment payment) {
		return paymentDao.getCurrentPaymentId(payment);
	}
}
