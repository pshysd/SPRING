package com.kh.ajax.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AjaxController {
	
	
	/*
	 * * Spring에서 Ajax 요청에 따른 응답 데이터 넘기는 방법
	 * 1. HttPServletResponse 객체로 응답하기
	 */
	
	/*
	@RequestMapping("ajax1.do")
	public void ajaxMethod1(String name, int age, HttpServletResponse response) throws IOException {
		
		System.out.println(name);
		System.out.println(age);
		
//		요청 처리를 위해 서비스 - DAO 호출
		
//		요청 처리가 다 되었다는 가정 하에
//		요청한 그 페이지에 응답할 데이터가 있을 경우
		String responseDate = "응답문자열: " +name+ "님은 " +age+ "살 입니다.";
		
//		response로 응답 데이터 넘기기
		response.setContentType("text/html;chaset=utf-8");
		response.getWriter().print(responseData);
		
//		Servlet 때엔 doGet() 메소드 정의 부분에 throws 구문이 추가되어있었는데
//		Spring에 와서는 일반 클래스로 컨트롤러를 작성하여 DispatcherServlet이 호출하기 때문에 
//		직접 작성해주어야 한다.
		
	*/
	
	/*
	 * * Spring에서 AJAX 요청에 따른 응답데이터 넘기는 방법
	 * 2. 응답할 데이터를 문자열 타입으로 반환 (HttpServletResponse를 사용하지 않아도 됨)
	 * -> 문자열 리턴은 원래 포워딩 방식을 의미(응답페이지로 인식해서 해당 뷰페이지를 찾음 : 404 오류 발생)
	 * -> 따라서 내가 리턴하는 문자열이 응답페이지가 아닌 응답데이터라는 것을 선언하는 어노테이션인
	 *    @ResponseBody라는 어노테이션을 붙여줘야 함
	 *    추가적으로 응답데이터에 한글이 포함될 경우, produces 속성도 추가
	 */
	@ResponseBody
	@RequestMapping(value="ajax1.do", produces="text/html; charset=utf-8")
	public String ajaxMethod1(String name, int age) {
		String responseData = "응답문자열: " +name+ "님은 " +age+ "살 입니다.";
		
		return responseData;
//		스프링에서 문자열을 리턴하면 곧 DispatcherServlet에 의해 해당 화면이 포워딩됨
//		-> prefix + responseDate + suffix = WEB-INF/webapp/views/ + 응답 ~~~ + .jsp
//		?
		
//		주의할점: Spring에서 Ajax 상의 응답 데이터를 문자열로 리턴하고자 한다면
//				  현재 내가 리턴하는 데이터가 응답 페이지에 대한 정보가 아니라 응답 데이터임을 항상 명시해야 함
//		-> 메소드 선언부 상단에 @ResponseBody를 붙여야 함
//		-> 이 때, 응답 데이터에 한글이 있다면 response.setContentType으로 인코딩 해줘야함
	}
}
