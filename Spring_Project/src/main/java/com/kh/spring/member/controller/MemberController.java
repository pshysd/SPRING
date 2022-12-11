package com.kh.spring.member.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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
	 * * 기존 객체 생성 방식 객체간의 결합도가 높아짐 (소스코드가 수정될 경우 일일이 다 바꿔줘야 함) 서비스가 동시에 매우 많은 횟수가
	 * 요청될 경우 그만큼 객체가 생성됨
	 * 
	 * * Spring의 DI(Dependency Injection, 의존성 주입)활용 방식 객체를 생성해서 주입해줌 -> 개발자가 직접 객체를
	 * 생성하지 않기 때문에 객체간의 결합도를 낮춰준다. -> new라는 키워드 없이 선언문만 써준다. -> 대신 선언 구문
	 * 위에 @Autowired 어노테이션을 붙여야 함
	 */

	@Autowired
	private MemberService mService; // = new MemberServiceImpl();
	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;

	/*
	 * * Controller 클래스 내부에 메소드를 만들어서 요청에 대한 처리 내용을 작성 -> 접근제어자는 무조건 public으로
	 * 지정해야한다. -> 메소드 상단에 url 매핑값을 지정해줘야한다. () -> 메소드명은 의미부여해서 알아볼 수 있게 짓기 -> 매개 변수는
	 * 있어도 되고, 없어도 된다. (매개 변수가 있는 경우 DispatcherServlet이 알아서 값을 넣어준다.) -> ***
	 */

//	@RequestMapping(value="login.me")
//	public void loginMember() {
//	}

	/*
	 * * Spring에서 파라미터(요청 시 전달값)를 받는 방법
	 * 
	 * 1. HttpServletRequest 객체의 getParameter 메소드를 이용하는 방법 (기존 방식) -> 해당 메소드의 매개 변수로
	 * HttpServletRequest 객체를 전달받아야 함. -> 해당 메소드 호출 시 자동으로 Spring에 의해 객체가 생성돼서 매개
	 * 변수로 주입해줌.
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
	 * 2. @RequestParam 어노테이션을 이용하는 방법 -> request.getParameter("key")로 밸류를 뽑아오는 역할을
	 * 대신 해주는 어노테이션 -> 어노테이션의 value 속성으로 jsp에서 작성했던 name 속성 값을 담으면 알아서 해당 매개 변수로 요청
	 * 시 전달 값을 받아올 수 있다. -> 만약, 넘어온 값이 비어있는 형태일 경우 defaultValue 속성으로 기본 값을 지정해줄 수
	 * 있다.
	 */

	/*
	 * @RequestMapping(value="login.me") public void
	 * loginMember(@RequestParam(value="userId", defaultValue="없음") String userId,
	 * 
	 * @RequestParam(value="userPwd") String userPwd) {
	 * 
	 * System.out.println(userId + userPwd); }
	 */

	/*
	 * 3. @RequestParam 어노테이션 생략하는 방법 -> 매개 변수명을 jsp의 name 속성 값과 동일하게 세팅하면 자동으로 값이
	 * 주입된다. -> 또한, 위에서 썼던 defaultValue같은 추가적인 속성을 사용할 수 없음
	 */

	/*
	 * @RequestMapping("login.me") // 어노테이션에 기술할 key-value 세트가 한 세트라면 밸류만 지정해도 됨
	 * public void loginMember(String userId, String userPwd) {
	 * 
	 * Member m = new Member(); m.setUserId(userId); m.setUserPwd(userPwd); }
	 */

	/*
	 * 4. 커맨드 객체 방식 -> 해당 메소드의 매개 변수로 요청 시 전달 값을 담고자 하는 VO 클래스 타입을 세팅 후 요청 시 전달 값의
	 * key와 VO 클래스의 정의되어있는 필드명을 일치시켜주면 SPRING이 내부적으로 해당 전달 값을 뽑아서 VO 객체로 가공해서 넘겨주는
	 * 방식 -> 반드시 jsp의 name 속성 값과 담고자하는 필드명이 동일해야만 함
	 */

	/*
	 * * 원리 1. Spring에 의해 내부적으로 요청 시 전달 값이 뽑힘 2. Spring에 의해 내부적으로 '기본 생성자'를 통해
	 * Member 객체 생성 3. Spring에 의해 요청 시 name 속성 값과 일치하는 해당 VO의 필드를 찾아 setter 메소드로 가공
	 * 
	 * * Spring에서의 요청 처리 후 응답 데이터를 담고 응답 페이지로 포워딩 또는 url 재요청 하는 방법
	 * 
	 * 1. 스프링에서 제공하는 Model 객체를 사용하는 방법 -> Model 객체는 기존의 requestScope와 같은 역할 (요청에 대한
	 * 응답 페이지에서만 사용할 수 있는 scope) -> 포워딩할 응답뷰로 전달하고자 하는 데이터를 key-value로 담을 수 있음 ->
	 * 데이터를 담은 후 응답 페이지를 포워딩하려면 해당 응답페이지 파일명을 "문자열로 리턴" 해주면 됨
	 * 
	 * [ 표현법 ] 기존의 requestScope에 값 담기 : request.setAttribute("key","value"); Spring의
	 * Model 객체에 값 담기 : model.addAttribute("key","value");
	 */

	/*
	 * @RequestMapping("login.me") public String loginMember(Member m ,Model model,
	 * HttpSession session) {
	 * 
	 * // System.out.println("userId: " + m.getUserId()); //
	 * System.out.println("userPwd: " + m.getUserPwd());
	 * 
	 * // 기존 방식 -> MemberService 객체를 직접 생성하여 호출 // MemberService mService = new
	 * MemberServiceImpl(); // Member loginUser = mService.loginMember(m);
	 * 
	 * // 스프링 방식 -> MemberServiceImpl 클래스도 bean으로 등록 // 스프링이 객체를 생성하도록
	 * 
	 * Member loginUser = mService.loginMember(m);
	 * 
	 * // 요청 결과에 따른 응답 페이지 처리 if(loginUser == null) { // 실패
	 * 
	 * // 에러 문구를 응답 페이지에 담아서 포워딩 model.addAttribute("errorMsg", "로그인 실패");
	 * 
	 * // * 포워딩 방식: url 주소는 그대로고 화면만 바꿔치기 해주는 개념 // -> 문자열을 리턴할 때 파일 경로를 포함한 파일명을 제시
	 * 
	 * // return "/WEB-INF/views/common/errorPage.jsp"; //
	 * /WEB-INF/views/WEB-INF/views/common/errorPage.jsp.jsp 이렇게 포워딩됨
	 * 
	 * // * SPring에서 포워딩시 주의할 점 // -> servlet-context.mxl의 주소의 자동완성을 도와주는 bean 설정에
	 * 의해 // 접두어: "/WEB-INF/views/" // 접미어: ".jsp" // 를 제외한 나머지 부분에 대한 경로만 문자열로
	 * 리턴해야함.
	 * 
	 * // 내가 리턴하고싶은 경로: /WEB-INF/views/common/errorPage.jsp // 접두어랑 접미어 제외하기:
	 * common/errorPage
	 * 
	 * return "common/errorPage";
	 * 
	 * 
	 * // 이 메소드를 호출하는 DispatcherServlet에게 포워딩할 경로값만 문자열로 리턴 // ->
	 * DispatcherServlet에서는 받은 경로값을 가지고 //
	 * request.getRequestDispatcher(리턴값).forward() 구문을 실행함
	 * 
	 * }else { // 성공
	 * 
	 * // loginUser를 sessionScope에 담고 메인페이지로 url 재요청
	 * 
	 * session.setAttribute("loginUser", loginUser);
	 * 
	 * // * url 재요청 방식: 주소창에 url을 요청하는 방식 // -> 문자열을 리턴할 때 redirect: 을 붙여서 주소값을 제시
	 * 
	 * // 내부적으로 리턴 값을 받아주는 DispatcherServlet이 // 해당 요청할 url 주소를 받아
	 * response.sendRedirect(리턴값) 메소드를 호출하는 원리
	 * 
	 * return "redirect:/"; // /: ContextPath 뒤에 붙은 /를 의미함 (== 메인페이지) } }
	 */

	/*
	 * 2. 스프링에서 제공하는 ModelAndView 객체를 사용하는 방법 -> Model은 데이터를 key-value 세트로 담을 수 있는
	 * requestScope와 같은 공간이라고 한다면 View는 응답 뷰에 대한 정보를 담을 수 있는 공간임 -> ModelAndView 객체는
	 * Model 객체와 View 객체를 결합한 형태 (단, Model 객체는 단독 존재가 가능하지만, View 객체는 그럴 수 없다.)
	 * 
	 * [ 표현법 ] ModelAndView 객체에 데이터를 담고자 한다면: mv.addObject("key","value");
	 * ModelAndView 객체에 응답 뷰에 대한 데이터를 담고자 한다면: mv.setViewName("응답 뷰 경로 또는 url 주소");
	 */

	@RequestMapping("login.me")
	public ModelAndView loginMember(Member m, ModelAndView mv, HttpSession session) {

//		암호화 작업 후 로직
//		-> BCrypt 방식에 의해 복호화가 불가능한 암호문 형태의 비밀번호와 일치하는지 대조작업
//		Member m의 userId 필드: 사용자가 입력한 아이디 (평문)
//				   userPwd 필드: 사용자가 입력한 비밀번호 (평문)
		Member loginUser = mService.loginMember(m);
//		loginUser: 오로지 아이디만 가지고 조회된 회원의 정보
//		Member loginUser의 userPwd 필드: DB에 기록된 암호화된 비밀번호

//		BCryptPasswordEncoder 객체의 matches 메소드
//		matches(평문, 암호문)을 작성하면 내부적으로 평문과 암호문을 맞추는 작업이 이루어짐
//		두 구문이 일치하는지 비교 후 일치하면 true 반환
		if (loginUser != null && bcryptPasswordEncoder.matches(m.getUserPwd(), loginUser.getUserPwd())) {

//			비밀번호도 일치한다면 -> 로그인 성공
			session.setAttribute("loginUser", loginUser);

			session.setAttribute("alertMsg", "로그인에 성공했습니다.");

			mv.setViewName("redirect:/");

		} else {
//			로그인 실패
			mv.addObject("errorMsg", "로그인실패");
//			/WEB-INF/views/common/errorPage.jsp
			mv.setViewName("common/errorPage");
		}

		return mv;
		/*
		 * 암호화 작업 전 로직 Member loginUser = mService.loginMember(m);
		 * 
		 * if (loginUser == null) { // 실패
		 * 
		 * // model.addAttribute("errorMsg", "로그인 실패"); mv.addObject("errorMsg",
		 * "로그인 실패");
		 * 
		 * // return "common/errorPage"; mv.setViewName("common/errorPa`ge"); //
		 * ModelAndView 객체를 이용해서 응답 페이지를 지정할 경우에도 // ViewResolver에 의해 접두어와 접미어를 생략한 형태로
		 * 지정해야만 한다.
		 * 
		 * } else { // 성공
		 * 
		 * // session.setAttribute("loginUser", loginUser);
		 * session.setAttribute("loginUser", loginUser); // return "redirect:/";
		 * mv.setViewName("redirect:/"); }
		 * 
		 * return mv;
		 */
	}

	@RequestMapping("logout.me")
	public String logoutMember(HttpSession session) {

		session.removeAttribute("loginUser");

		return "redirect:/";
	}

	@RequestMapping("enrollForm.me")
	public String enrollForm() {

//		회원가입 페이지 포워딩

//		포워딩하고자 하는 경로: /WEB-INF/views/member/memberEnrollForm.jsp
		return "member/memberEnrollForm";
	}

	@RequestMapping("insert.me")
	public String insertMember(Member m, Model model, HttpSession session) {

//		1. 한글 깨짐 문제 발생 -> 요청 시 전달 값을 뽑기 전에 먼저 UTF-8 형식으로 인코딩 설정하기.
//		이 시점에서 인코딩 설정을 하면 이미 늦었다 (이미 DispatcherServlet에서 값을 뽑았음)
//		해결책: 요청이 DispatcherServlet에 도달하기 전에 인코딩 필터를 거쳐가게끔 해줄 것임.
//				스프링에서 제공하는 인코딩 필터를 web.xml에 등록하기.

//		2. 필수입력사항만 입력하고 넘겼을 경우 400 error 발생
//		ex) int 자료형인 age 필드에 빈 문자열이 넘어와 자료형이 맞지 않는 문제 발생 (NumberFormatException)

//		400 error(Bad Request Error): 형식에 맞지 않는 데이터가 넘어온 경우 발생
//		해결책: Member 클래스의 age 필드를 int형에서 String 형으로 변경

//		3. 비밀번호가 사용자가 입력한 그대로의 평문
//		사람이 그냥 봐도 의미를 파악할 수 있는 데이터: 평문(plainText)
//		사람이 그냥 봐서는 의미를 파악할 수 없는 데이터: 암호문
//		-> 스프링에서 제공하는 Bcrypt 방식으로 비밀번호를 암호화할 것

//		1) 스프링 시큐리티 모듈에서 제공하는 라이브러리 pom.xml에 추가
//		2) BCryptPasswordEncoder 클래스를 xml 파일에 bean 등록
//		3) web.xml에 spring-security.xml 파일을 로딩할 수 있게 등록

		String encPwd = bcryptPasswordEncoder.encode(m.getUserPwd());
//		-> 같은 평문이여도 매번 다른 암호문 결과가 나옴
//		-> 평문 + salt(랜덤값) -> 암호화 작업이 이루어지기 때문

//		Member 객체의 userPwd의 필드의 값을 암호문으로 바꿔치기 -> setter 메소드
		m.setUserPwd(encPwd);

		int result = mService.insertMember(m);

		if (result > 0) {

			session.setAttribute("alertMsg", "회원가입이 되었습니다.");

			return "redirect:/";
		} else {
			model.addAttribute("errorMsg", "회원가입 실패");

			return "common/errorPage";
		}
	}

	@RequestMapping("myPage.me")
	public String myPage() {
		
		return "member/myPage";
	}

	@RequestMapping("update.me")
	public String updateMember(Member m, Model model, HttpSession session) {

//		System.out.println(m);

		int result = mService.updateMember(m);

		if (result > 0) { // 성공

//			수정 성공일 경우 DB로부터 수정된 회원의 정보를 다시 조회해서
//			session에 loginUser 키값으로 덮어씌워야 한다.
//			-> 이 때, 기존의 loginMember 메소드를 재활용해서 조회해온다.

			Member updateMem = mService.loginMember(m);

			session.setAttribute("loginUser", updateMem);

//			session에 일회성 알람 문구도 담기
			session.setAttribute("alertMsg", "수정이 완료되었습니다.");

			return "redirect:/";
		} else { // 실패
			model.addAttribute("errorMsg", "회원정보 변경 실패");

			return "common/errorPage";
		}
	}

	@RequestMapping("delete.me")
	public String deleteMember(String userPwd, String userId, HttpSession session, Model model) {

//		userPwd: 회원탈퇴 요청 시 사용자가 입력했던 평문 비밀번호
//		session의 loginUser.userPwd: 현재 로그인한 회원의 암호화된 비밀번호
//		-> 이 두가지 정보가 있어야만 matches 메소드 활용 가능

		String encPwd = ((Member) session.getAttribute("loginUser")).getUserPwd();

//		비밀번호 대조작업
		if (bcryptPasswordEncoder.matches(userPwd, encPwd)) {

//			비밀번호가 맞을 경우 -> 탈퇴처리

			int result = mService.deleteMember(userId);

			if (result > 0) { // 성공

//				로그아웃 처리 후 일회성 알람 메시지 담기, 메인 페이지로 url 재요청
				session.removeAttribute("loginUser");
				session.setAttribute("alertMsg", "그동안 이용해주셔서 감사합니다.");

				return "redirect:/";
			} else { // 실패
				model.addAttribute("errorMsg", "회원탈퇴 실패");

				return "common/errorPage";
			}
		} else {
//			비밀번호가 틀렸을 경우 -> 비밀번호가 틀렸다고 알려주고 마이페이지로 url 재요청
			session.setAttribute("alertMsg", "비밀번호가 다릅니다.");

			return "redirect:myPage.me";
		}
	}

	@ResponseBody
	@RequestMapping(value = "idCheck.me", produces = "text/html; charset=utf-8")
	public String checkId(String checkId) {
		int count = mService.idCheck(checkId);
		
		return count > 0 ? "NNNNN" : "NNNNY";
	}
}