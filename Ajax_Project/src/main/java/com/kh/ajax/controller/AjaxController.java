package com.kh.ajax.controller;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kh.ajax.model.vo.Member;

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
	/*
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
	*/
	
	
//	다수의 응답 데이터가 있을 경우
	/*
	@RequestMapping("ajax1.do")
	public void ajaxMethod1(String name, int age, HttpServletResponse response) throws IOException {
		
//		요청처리가 다 되었다는 가정하에 데이터 응답
//		response.setContentType("text/html; charset=utf-8");
//		response.getWriter().print(name);
//		response.getWriter().print(age);
//		홍길동30 -> 여러 개의 데이터가 연달아서 응답됨
		
//		JSON (JavaScript Object Notation) 형태로 응답
//		JSONArray -> [값, 값, 값, 값, ...] -> 자바스크립트의 배열 형식 (자바로 따지면 ArrayList)
//		JSONObject -> {키:값, 키:값, 키:값 ...} -> 자바스크립트의 객체 형식 (자바로 따지면 HashMap)
		
//		1. JSONArray로 담아서 응답
//		JSONArray jArr = new JSONArray(); // []
//		jArr.add(name); // ["홍길동"]
//		jArr.add(age); // ["홍길동", 30]
		
//		response.setContentType("applcation/json; charset=utf-8");
//		response.getWriter().print(jArr);
		
//		2. JSONObject로 담아서 응답
		JSONObject jObj = new JSONObject(); // {}
		
		jObj.put("name", name); // {name: "홍길동"};
		jObj.put("age", age); // {name: "홍길동", age: 30};
		
		response.setContentType("application/json; charset=utf-8");
		response.getWriter().print(jObj);
	}
	*/
	
//	여러 개의 응답 데이터를 넘기는 경우 (response 객체 사용 안함)
	
	@ResponseBody
	@RequestMapping(value="ajax1.do", produces="application/json; charset=utf-8")
	public String ajaxMethod1(String name, int age) {
		
//		다수의 응답 데이터가 있다라는 가정하에 JSONObject 타입으로 넘기기
		JSONObject jObj = new JSONObject();
		
		jObj.put("name", name); // {name: "홍길동"};
		jObj.put("age", age); // {name: "홍길동", age: 30};
		
		return jObj.toJSONString();
	}
	
	/*
	@ResponseBody
	@RequestMapping(value="ajax2.do", produces="application/json; charset=utf-8")
	public String ajaxMethod2() {
		
//		DB로부터 조회했다는 가정하에 Member 객체 생성하기
		
		Member m = new Member("user01", "pass01", "홍길동", 20, "01011112222");
		
		JSONObject jObj = new JSONObject();
		
		jObj.put("userId", m.getUserId());
		jObj.put("userName", m.getUserName());
		jObj.put("age", m.getAge());
		jObj.put("phone", m.getPhone());
		
		return jObj.toJSONString();
	}
	*/
	
//	GSON: Google JSON의 약자
//	-> VO 객체를 JSONObject로 가공할 때 내부적으로 필드명을 키값으로 잡아서 제공해준다.
	
	@ResponseBody
	@RequestMapping(value="ajax2.do", produces="application/json; charset=utf-8")
	public String ajaxMethod2() {
		
		Member m = new Member("user01", "pass01", "홍길동", 20, "01011112222");
		
		
	}
}
