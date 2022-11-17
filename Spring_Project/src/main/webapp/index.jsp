<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<!-- 
		* Spring framework의 특징
		1. IOC(Inversion Of Control): 제어의 역전
		-> 객체들을 관리할 권한이 개발자가 아닌 Spring에게 있음
			개발자가 직접 new 구문을 이용해서 객체를 생성할 일이 거의 없다.
			
		2. DI (Dependency Injection): 의존성 주입
		-> 객체간의 의존관계를 알아서 설정해준다.
			ex) MVC 패턴에서
				Controller -> Service 객체 생성 후 메소드 호출 -> Dao 객체 생성 후 메소드 호출
				JDBC에서
				DriverManager -> Connection 객체 생성 -> PreparedStatement 객체 생성 -> ResultSet 객체 생성
				 
		3. Spring AOP (Aspect Oriented Programming): 관점 지향 프로그래밍
		-> 객체지향 프로그래밍을 보완한 개념.
			객체지향에서도 부족했던 재활용성, 코드의 중복을 더 줄여서 모듈화하게 도와주는 기법
			
		4. POJO (Plain Old Java Object): 내가 직접 만든 자바 클래스를 지칭 (POJO Class)
		-> 다른 프레임워크들은 대부분 내가 직접 만든 클래스를 혼용해서 쓸 수 없게 되어있는데
			Spring이나 MyBatis는 내가 직접 만든 클래스를 혼용해서 쓸 수 있음
			
		5. Spring JDBC
			영속성 작업과 관련된 JDBC도 사용할 수 있고, MyBatis와 같은 영속성 프레임워크와의 연동도 지원
		
		6. Spring MVC
			MVC 패턴으로 코딩하기 위한 Model, View, Controller 간의 관계르 알아서 IOC, DI를 통해서 관리해주겠다
			
		7. PSA(Portable Service Abstraction)
			스프링에서는 수많은 모듈들을 붙여 쓸 수 있는데 일일이 연동해주는 게 아니라 모듈만 바꿔치기 해서 사용할 수 있게끔 도와주겠다.
	-->
	<jsp:forward page="WEB-INF/views/main.jsp" />
</body>
</html>