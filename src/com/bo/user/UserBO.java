package com.bo.user;

import com.dao.user.UserDao;
import com.vo.user.UserVO;

public class UserBO {
	
	UserDao userDao = new UserDao();
	
//	public List<SBTypeVO> fetchSBTypeVOList(String dependTypeID) {
//		return sbtypeDao.fetchSBTypeVOList(dependTypeID);
//	}
	
	public UserVO fetchUserVOByID(String userID){
		return userDao.fetchUserVOByID(userID);
	}
	
	public int addUser(UserVO vo){
		return userDao.addUser(vo);
	}
	
	public int modifyUser(UserVO vo) {
		return userDao.modifyUser(vo);
	}
}
