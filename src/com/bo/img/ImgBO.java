package com.bo.img;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dao.img.ImgDao;

public class ImgBO {
	
	ImgDao imgDao = new ImgDao();
	
	public Map<String,String> getDatPathByTypeID(String bigclass,String smallclass){
		Map<String, String> map = imgDao.getDatPathByTypeID(bigclass, smallclass);//用户选中的商标类型
		Map<String, String> map_relevant = imgDao.getKuaLei(bigclass);//自动跨类的商标类型
		map.putAll(map_relevant);
		return map;
	}
	
	public Set<String> getSearchedImg(){
		return imgDao.getSearchedImg();
	}
	
	//接口3.7 删除检索历史
	public boolean delSearchedPicList(String del){
		return imgDao.delSearchedPicList(del);
	}
	
	//接口3.7
	public List<Map<String,String>> getSearchedPicList(String userID){
		return imgDao.getSearchedPicList(userID);
	}
	
	//接口3.8
	public List<Map<String,String>> getSearchedTypeList(String recordId){
		return imgDao.getSearchedTypeList(recordId);
	}
	
	//接口3.9
	public List<Map<String,String>> getSearchedDatList(String recordId,String typeid,int pageSize, int pageNumber){
		return imgDao.getSearchedDatList(recordId, typeid, pageSize, pageNumber);
	}
	//接口3.9 
	public int getSearchedDatListToTalnum(String recordId,String typeid){
		return imgDao.getSearchedDatListToTalnum(recordId, typeid);
	}
	
	//接口3.10
	public Map<String,String> getSingleDat(String datpathid){
		return imgDao.getSingleDat(datpathid);
	}
	
	public Map<String,String> getClassName(String typeid){
		return imgDao.getClassName(typeid);
	}
}
