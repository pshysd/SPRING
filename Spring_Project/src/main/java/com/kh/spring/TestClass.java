package com.kh.spring;

import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.slf4j.Slf4j;

// 이 클래스에서 Logging을 하고싶을 때 자동으로 전역변수로써 Logger 객체가 생성됨 (이때, 객체명은 log)
@Slf4j
public class TestClass {
	
//	Logger log = LoggerFactory.getLogger(TestClass.class);
	
	@Test
	public void test() {

		/*
//		로깅 수행하는 도구 생성 (Logger 타입)
		Logger logger = LoggerFactory.getLogger(TestClass.class);
		
//		로깅 수행 시 필요한 출력문들
		logger.debug("난 debug야");
		logger.info("난 info야");
		logger.warn("난 warn이야");
		logger.error("난 error야");
		
//		-> 이 메시지의 종류와, 어디서 찍힌 메시지인지 앞에 붙고
//		   그 뒤에 로그를 남기고자 하는 메시지 내용이 출력됨을 볼 수 있다.
//		-> debug 출력문은 출력이 되지 않음을 볼 수 있다.
//		src/test/resources/log4j.xml 에서 설정 확인
 		*/
		
		log.debug("나디버그여ㅑ");
		log.info("나인포여");
		log.warn("워니닝워닝");
		log.error("난에러다");
//		개발 중 디버깅 할 일이 있다면 System.out.println 대신에 log.debug로 출력해보기!
	}
}
