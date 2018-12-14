package com.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.dao.ProductDao;
import com.test.model.Product;

@Service
public class ProductService {

	@Autowired
	ProductDao productDao;

	public void addProduct(Product product) {
		productDao.addProduct(product);
	}
}
