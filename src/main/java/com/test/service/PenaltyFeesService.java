package com.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.dao.PenaltyFeesDao;
import com.test.model.PenaltyFees;

@Service
public class PenaltyFeesService {

	@Autowired
	PenaltyFeesDao penaltyFeesDao;
	
	public void addNewPenalty(PenaltyFees penaltyFees) {
		penaltyFeesDao.addNewPenaltyFees(penaltyFees);
	}

}
