package com.spring.myweb12.view.common;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
 
//선언한 순간.... CommonExceptionHandler객체가 자동으로 생성된다.
@ControllerAdvice("com.spring.myweb1.view")   //이 경로를 포하하는 대상지정
public class CommonExceptionHandler {
	
	//발생되는 유형에 따라 자동으 메소드가 실행된다.   JSP에 있는 해당페이지에서 출력된다.  
	//만약 해당하는 오류유형이 없다면  handleException()메소드가 호출된다.  
	@ExceptionHandler(ArithmeticException.class)
	public ModelAndView handleArithmeticException(Exception e) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("exception", e);
		//mav.setViewName("/common/arithmeticError");    // 어노테이션으로 처리할 경우 - ViewResolver가 적용되어 있지 않다면 webapp을 root로 시작한다
		mav.setViewName("views/common/arithmeticError"); // xml로 처리할 경루 - presentation-layer.xml속  driven선언 위치가 기준이 된다. 상대경로지정 및 ViewResolver적용 .jsp삭제함.
		return mav;
	}

	
	@ExceptionHandler(IllegalArgumentException.class)
	public ModelAndView handleIllegalArgumentException(Exception e) {
		System.out.println("IllegalArgumentException 핸들러 실행됨");

	    ModelAndView mav = new ModelAndView();
	    mav.addObject("exception", e);
	    mav.setViewName("views/common/error"); 
	    return mav;
	}

	
	@ExceptionHandler(NullPointerException.class)
	public ModelAndView handleNullPointerException(Exception e) {
		System.out.println("IllegalArgumentException 핸들러 실행됨");

		ModelAndView mav = new ModelAndView();
		mav.addObject("exception", e);
		mav.setViewName("views/common/nullPointerError");
		return mav;
	}

	//만약 해당하는 오류유형이 없다면 Exception메소드(클래스)가 실행된다.
	@ExceptionHandler(Exception.class)
	public ModelAndView handleException(Exception e) {
		System.out.println("IllegalArgumentException 핸들러 실행됨");

		ModelAndView mav = new ModelAndView();
		mav.addObject("exception", e);
		mav.setViewName("views/common/error");
		return mav;
	}
}