package com.bo;

import com.dao.CommParameterDao;

public class CommParameterBO {
	CommParameterDao dao = new CommParameterDao();
	public String[] fetchDayArra() {
		return dao.fetchDayArra();
	}
	
}
