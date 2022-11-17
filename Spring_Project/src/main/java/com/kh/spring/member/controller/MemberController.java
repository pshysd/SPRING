package com.kh.spring.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kh.spring.member.model.service.MemberService;
import com.kh.spring.member.model.vo.Member;

/*
 * * SPRING 에서는 내부적으로 중앙 요청 처리 서블릿 (DispatcherServlet)이 존재함
 * -> 모든 요청은 DispatcherServlet에 의해 분배되는 구조
 * -> 기존의 방식처럼 매 요청마다 직접적으로 Servlet을 만들 필요가 없음 (일반 클래스로 Controller 만들기)
 * 
 * * DispatcherServlet에 의해 MemberController 클래스의 언 ㅡ메소드가 호출
 * -> MemberController 객체가 필요함
 * -> Spring이 MemberController 객체를 만들 수 있게끔 bean 등록을 해줘야 함
 */
@Controller // Controller 타입의 어노테이션을 붙여주면 빈 스캐닝을 통해 자동으로 bean으로 등록
// @Component // 타입에 상관없이 bean으로 등록하겠다.
public class MemberController {

//	private MemberService memberService = new MemberServiceImpl();

	/*
	 * * 기존 객체 생성 방식
	 * 객체간의 결합도가 높아짐 (소스코드가 수정될 경우 일일이 다 바꿔줘야 함)
	 * 서비스가 동시에 매우 많은 횟수가 요청될 경우 그만큼 객체가 생성됨 
	 * 
	 * * Spring의 DI(Dependency Injection, 의존성 주입)활용 방식
	 * 객체를 생성해서 주입해줌
	 * -> 개발자가 직접 객체를 생성하지 않기 때문에 객체간의 결합도를 낮춰준다.
	 * -> new라는 키워드 없이 선언문만 써준다.
	 * -> 대신 선언 구문 위에 @Autowired 어노테이션을 붙여야 함
	 */
	
	@Autowired
	private MemberService mService; // = new MemberServiceImpl();
	
	/*
	 * * Controller 클래스 내부에 메소드를 만들어서 요청에 대한 처리 내용을 작성
	 * -> 접근제어자는 무조건 public으로 지정해야한다.
	 * -> 메소드 상단에 url 매핑값을 지정해줘야한다. ()
	 * -> 메소드명은 의미부여해서 알아볼 수 있게 짓기
	 * -> 매개 변수는 있어도 되고, 없어도 된다. (매개 변수가 있는 경우 DispatcherServlet이 알아서 값을 넣어준다.)
	 * -> ***
	 */
	
//	@RequestMapping(value="login.me")
//	public void loginMember() {
//	}
	
	/*
	 * * Spring에서 파라미터(요청 시 전달값)를 받는 방법
	 * 
	 * 1. HttpServletRequest 객체의 getParameter 메소드를 이용하는 방법 (기존 방식)
	 * -> 해당 메소드의 매개 변수로 HttpServletRequest 객체를 전달받아야 함.
	 * -> 해당 메소드 호출 시 자동으로 Spring에 의해 객체가 생성돼서 매개 변수로 주입해줌.
	 */
	
//	@RequestMapping(value="login.me")
//	public void loginMember(HttpServletRequest request) {
//		
//		String userId = request.getParameter("userId");
//		String userPwd = request.getParameter("userPwd");
//	
//		System.out.println("userId: " +userId);
//		System.out.println("userPwd: "+userPwd);
//	}
	
	/*
	 * 2. @RequestParam 어노테이션을 이용하는 방법
	 * -> request.getParameter("key")로 밸류를 뽑아오는 역할을 대신 해주는 어노테이션
	 * -> 어노테이션의 value 속성으로 jsp에서 작성했던 name 속성 값을 담으면
	 * 	  알아서 해당 매개 변수로 요청 시 전달 값을 받아올 수 있다.
	 * -> 만약, 넘어온 값이 비어있는 형태일 경우 defaultValue 속성으로 기본 값을 지정해줄 수 있다.
	 */
	
	/*
	@RequestMapping(value="login.me")
	public void loginMember(@RequestParam(value="userId", defaultValue="없음") String userId, 
							@RequestParam(value="userPwd") String userPwd) {
		
		System.out.println(userId + userPwd);
	}
	*/
	
	/*
	 * 3. @RequestParam 어노테이션 생략하는 방법
	 * -> 매개 변수명을 jsp의 name 속성 값과 동일하게 세팅하면 자동으로 값이 주입된다.
	 * -> 또한, 위에서 썼던 defaultValue같은 추가적인 속성을 사용할 수 없음
	 */
	
	/*
	@RequestMapping("login.me") // 어노테이션에 기술할 key-value 세트가 한 세트라면 밸류만 지정해도 됨
	public void loginMember(String userId, String userPwd) {
		
		Member m = new Member();
		m.setUserId(userId);
		m.setUserPwd(userPwd);
	}
	*/
	
	/*
	 * 4. 커맨드 객체 방식
	 * -> 해당 메소드의 매개 변수로
	 * 	  요청 시 전달 값을 담고자 하는 VO 클래스 타입을 세팅 후
	 * 	  요청 시 전달 값의 key와 VO 클래스의 정의되어있는 필드명을 일치시켜주면
	 * 	  SPRING이 내부적으로 해당 전달 값을 뽑아서 VO 객체로 가공해서 넘겨주는 방식
	 * -> 반드시 jsp의 name 속성 값과 담고자하는 필드명이 동일해야만 함
	 */

	/*
	 * * 원리
	 * 1. Spring에 의해 내부적으로 요청 시 전달 값이 뽑힘
	 * 2. Spring에 의해 내부적으로 '기본 생성자'를 통해 Member 객체 생성
	 * 3. Spring에 의해 요청 시 name 속성 값과 일치하는 해당 VO의 필드를 찾아 setter 메소드로 가공
	 */
	@RequestMapping("login.me")
	public void loginMember(Member m) {

//		System.out.println("userId: " + m.getUserId());
//		System.out.println("userPwd: " + m.getUserPwd());
		
//		기존 방식 -> MemberService 객체를 직접 생성하여 호출
//		MemberService mService = new MemberServiceImpl();
//		Member loginUser = mService.loginMember(m);
		
//		스프링 방식 -> MemberServiceImpl 클래스도 bean으로 등록
//					   스프링이 객체를 생성하도록
	}
}