package com.bo.option;

import java.util.Map;

import com.dao.option.PreSearchDao;

public class PreSearchBO {

	PreSearchDao dao = new PreSearchDao(); 
	
	public int updatePreSearch(String recordId) {
		return dao.updatePreSearch(recordId);
	}
	
	public int addPreSearch(String username,String pic,String stat,String bigclass,String smallclass,String precisionNum) {
		return dao.addPreSearch(username, pic, stat, bigclass, smallclass, precisionNum);
	}
	
	public int getPreSearchImgNum() {
		return dao.getPreSearchImgNum();
	}
	
	public Map<String,String> getSinglePreSearch() {
		return dao.getSinglePreSearch();
	}
	
	public String IsThisImgSearched(String userID,String picPath,String bigclass,String smallclass,String precisionNum) {
		return dao.IsThisImgSearched(userID, picPath, bigclass, smallclass,precisionNum);
	}
}
