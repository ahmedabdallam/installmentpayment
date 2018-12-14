package com.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.test.model.CustomPayment;
import com.test.service.PaymentService;

@RestController
public class PaymentController {
	
	@Autowired
	PaymentService paymentService;

	@PostMapping("/newPayment")
	public void addNewPayment(@RequestBody CustomPayment customPayment) {
		paymentService.addNewPayment(customPayment);
	}
	
}
