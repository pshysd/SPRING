package com.kh.spring.member.model.service;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.spring.member.model.dao.MemberDao;
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
		
		/*
		 * 순수 MYBATIS에서는 SqlSession 객체가 필요
		 * 스프링에서 MYBATIS를 쓰려면 SqlSessionTemplate 객체가 필요
		 * -> root.context.xml에서 bean으로 등록했었음
		 * -> 내가 직접 new 구문을 통해 객체를 생성할 필요가 없음 (@Autowired 사용)
		 */
		
		Member loginUser = mDao.loginMember(sqlSession, m);
		
//		SqlSessionTemplate 객체를 bean으로 등록 후 @Autowired 해줌으로써
//		Spring이 해당 객체를 생성해서 사용 후 자동으로 객체를 알아서 반납시켜주기 때문에
//		내가 직접 close를 사용하여 자원을 반납할 필요 x
		
		return loginUser;
	}

	@Override
	public int insertMember(Member m) {
		
		return mDao.insertMember(sqlSession, m);
	}

	@Override
	public int updateMember(Member m) {
		
		return mDao.updateMember(sqlSession, m);
	}

	@Override
	public int deleteMember(String userId) {
		return mDao.deleteMember(sqlSession, userId);
	}

	@Override
	public int idCheck(String checkId) {
		return 0;
	}


}
