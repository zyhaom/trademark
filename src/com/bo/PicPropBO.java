package com.bo;

import com.dao.PicPropDao;
import com.vo.PicPropVO;

public class PicPropBO {
	
//	private final static Logger log = Logger.getLogger(PicPropBO.class);
	
	PicPropDao picPropDao = new PicPropDao();
	
	public PicPropVO fetchResultVOByPic(String searchPicName) {
		return picPropDao.fetchResultVOByPic(searchPicName);
	}
}
