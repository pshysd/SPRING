package com.kh.spring.member.model.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.kh.spring.member.model.vo.Member;

// Repository: 주로 DB와 관련된 작업(== 영속성 작업)을 처리하겠다. 라는 뜻 
@Repository // DAO 역할을 하는 bean 등록
public class MemberDao {
	
	public Member loginMember(SqlSessionTemplate sqlSession, Member m) {
		
		/*
		 * SQL문 종류에 따른 메소드를 호출
		 * -> SqlSession 객체와 SqlSessionTemplate 객체에서 제공하는 메소드 종류가 동일
		 */
		
		// 로그인 == 단일행 조회
		return sqlSession.selectOne("memberMapper.loginMember",m);
	}
}
