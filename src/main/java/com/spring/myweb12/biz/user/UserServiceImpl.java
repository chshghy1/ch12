package com.spring.myweb12.biz.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDAO userDAO; 

	public void setUserDAO(UserDAO userDAO) { 
		this.userDAO = userDAO;
	}

	public UserVO getUser(UserVO vo) {
		return userDAO.getUser(vo);
	}
	
}
