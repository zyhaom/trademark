package com.bo.option;

import java.util.List;

import com.dao.option.RelevantDao;

public class RelevantBO {

	RelevantDao dao = new RelevantDao(); 
	
	public List<String> getRelevantTypes(String typeID){
		return dao.getRelevantTypes(typeID);
	}
	
	public int addRelevantType(String typeID,String relevantType) {
		return dao.addRelevantType(typeID, relevantType);
	}
	
	public int delRelevantType(String typeID,String relevantType) {
		return dao.delRelevantType(typeID, relevantType);
	}
}
