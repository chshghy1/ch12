package com.spring.myweb12.view.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.spring.myweb12.biz.user.UserDAO;

@Controller
public class LogoutController{

	@Autowired
	private UserDAO userDAO;

}
