package com.bo;

import java.util.List;
import com.dao.SBTypeDao;
import com.vo.SBTypeVO;

public class SBTypeBO {
	
	SBTypeDao sbtypeDao = new SBTypeDao();
	
	public List<SBTypeVO> fetchSBTypeVOList(String dependTypeID) {
		return sbtypeDao.fetchSBTypeVOList(dependTypeID);
	}
}
