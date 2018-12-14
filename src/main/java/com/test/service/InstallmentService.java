package com.test.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.dao.CustomerDao;
import com.test.dao.InstallmentDao;
import com.test.model.Installment;

@Service
public class InstallmentService {

	@Autowired
	InstallmentDao installmentDao;

	@Autowired
	CustomerDao customerDao;

	public void addInstallment(Installment installment) {
		installmentDao.addInstallment(installment);
	}

	public List<Installment> getPendingInstallments(int customerId) {
		List<Installment> installments = installmentDao.getNonPaidInstallmentsByCustomer(customerId);
		return installments;

	}

	public List<Installment> getPendingInstallments(String customerName) {
		int customerId = customerDao.getCustomerByName(customerName);
		return installmentDao.getNonPaidInstallmentsByCustomer(customerId);
	}

	public Installment getNextUnpaidInstallment(String customerName) {
		int customerId = customerDao.getCustomerByName(customerName);
		Installment installment = installmentDao.getNextUnpaidInstallment(customerId);
		return installment;
	}

	public List<Installment> getPendingInstallmentsByPhone(String customerPhoneNumber) {
		List<Installment> installments = installmentDao.getNonPaidInstallmentsByCustomerPhone(customerPhoneNumber);
		return installments;
	}

}
