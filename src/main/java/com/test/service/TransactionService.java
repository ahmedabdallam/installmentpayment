package com.test.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.dao.CustomerDao;
import com.test.dao.InstallmentDao;
import com.test.dao.ProductDao;
import com.test.dao.TransactionDao;
import com.test.model.CustomTransaction;
import com.test.model.Installment;
import com.test.model.TransactionLog;

@Service
public class TransactionService {

	@Autowired
	TransactionDao transactionDao;

	@Autowired
	InstallmentDao installmentDao;

	@Autowired
	ProductDao productDao;

	@Autowired
	CustomerDao customerDao;

	public void addTransaction(TransactionLog transactionLog) {

		double productPrice = new Double(productDao.getProductPrice(transactionLog.getProductId()));
		double installmentAmount = productPrice / 12;

		List<Installment> installments = installmentDao.getInstallmentsByCustomer(transactionLog.getCustomerId());

		transactionDao.addTransaction(transactionLog);

		LocalDate futureDate = LocalDate.now().withDayOfMonth(1);
		if (installments.size() == 0) {
			for (int i = 1; i <= 12; i++) {
				System.out.println(futureDate.plusMonths(i));
				Installment installment = new Installment();
				installment.setCustomerId(transactionLog.getCustomerId());
				installment.setAmount(installmentAmount);
				installment.setInstallmentDueDate(java.sql.Date.valueOf(futureDate.plusMonths(i)));
				installmentDao.addInstallment(installment);
			}
		} else {
			List<Installment> remainingInstallments = installmentDao
					.getNonPaidInstallmentsByCustomer(transactionLog.getCustomerId());
			List<Installment> newInstallments = new ArrayList<>();
			LocalDate lastInstallmentDate = remainingInstallments.get(remainingInstallments.size() - 1)
					.getInstallmentDueDate().toLocalDate();
			for (int i = 1; i <= 12; i++) {
				Installment newPurchaseInstallment = new Installment();
				newPurchaseInstallment.setCustomerId(transactionLog.getCustomerId());
				newPurchaseInstallment.setAmount(installmentAmount);
				newPurchaseInstallment
						.setInstallmentDueDate(java.sql.Date.valueOf(lastInstallmentDate.plusMonths(i - 1)));
				newInstallments.add(newPurchaseInstallment);
			}

			int newInstallmentNumbers = newInstallments.size() - remainingInstallments.size();
			for (int i = 1; i <= newInstallmentNumbers; i++) {
				installmentDao.addInstallment(newInstallments.get(i));
			}

			for (Installment installment : remainingInstallments) {
				double newAmount = installment.getAmount() + installmentAmount;
				installmentDao.updateCurrentInstallment(installment.getId(), newAmount);
			}

		}

	}

	public void addTransaction(CustomTransaction customTransaction) {
		TransactionLog transactionLog = new TransactionLog();
		transactionLog.setProductId(productDao.getProductByName(customTransaction.getProductName()));
		transactionLog.setCustomerId(customerDao.getCustomerByPhone(customTransaction.getCustomerPhoneNumber()));
		addTransaction(transactionLog);
	}

}
