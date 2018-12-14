package com.test.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.test.model.Installment;
import com.test.service.InstallmentService;

@RestController
public class InstallmentController {

	@Autowired
	InstallmentService installmentService;

	public void addInstallment(Installment installment) {
		installmentService.addInstallment(installment);
	}

	@GetMapping("/installment")
	public List<Installment> getPendingInstallments(@RequestParam(value = "customerName") String customerName) {

		return installmentService.getPendingInstallments(customerName);
	}

	@GetMapping("/installmentCustomer")
	public List<Installment> getPendingInstallmentsByPhone(
			@RequestParam(value = "customerPhoneNumber") String customerPhoneNumber) {

		return installmentService.getPendingInstallmentsByPhone(customerPhoneNumber);
	}
}
