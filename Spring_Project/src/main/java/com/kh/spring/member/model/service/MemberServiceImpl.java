package com.kh.spring.member.model.service;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.spring.member.model.vo.Member;
// @Component
@Service //Service 역할을 하는 bean으로 등록
public class MemberServiceImpl implements MemberService {
	
	// Spring에서 MyBatis를 사용하기 위한 SqlSessionTemplate 변수 선언
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Autowired
	private MemberDao mDao;
	@Override
	public Member loginMember(Member m) {
		
		
		/*
		 * 기존 방식
		 * SqlSession sqlSession = Template.getSqlSession(); // 객체 생성
		 * 
		 * Member loginUser = new MemberDao().loginMember(sqlSession, m); // 객체 생성
		 * 
		 * sqlSession.close();
		 * 
		 * return loginUser;
		 * 
		 */
		
		
		return null;
	}

	@Override
	public int insertMember(Member m) {
		return 0;
	}

	@Override
	public int updateMember(Member m) {
		return 0;
	}

	@Override
	public int deleteMember(Member m) {
		return 0;
	}

	@Override
	public int idCheck(String checkId) {
		return 0;
	}


}
