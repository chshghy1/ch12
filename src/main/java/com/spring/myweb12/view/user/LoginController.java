package com.spring.myweb12.view.user;

import com.spring.myweb12.biz.user.UserDAO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.spring.myweb12.biz.user.UserVO;


import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/auth")
public class LoginController{
	
	@Autowired
	private UserDAO userDAO;

	//로그인
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody UserVO vo, HttpSession session){

		UserVO user = userDAO.getUser(vo);

		if(user != null){
			session.setAttribute("user",user);

			//성공
			return ResponseEntity.ok(user);
			// user객체를 Json데이로 변환해서 body에 넣어서 응답처리

		}
		else{
			//실패 : 401- 인증실패
			return ResponseEntity.status(401).body("아이디 또는 비밀번호가 틀립니다");
		}
	}

	//현재 로그인사용자 : getCurrentUser()에서 여기로...
	@GetMapping("/me")
	public ResponseEntity<?> getCurrentUser(HttpSession session){
		//세션에 저장된 user (로그인)
		UserVO user = (UserVO) session.getAttribute("user");
		if(user != null){
			return ResponseEntity.ok(user);
		}else{
			return ResponseEntity.status(401).body("로그인이 필요합니다");
		}

	}
}





























