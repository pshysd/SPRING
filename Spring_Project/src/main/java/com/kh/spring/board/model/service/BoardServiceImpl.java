package com.kh.spring.board.model.service;

import java.util.ArrayList;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.spring.board.model.dao.BoardDao;
import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.model.vo.Reply;
import com.kh.spring.common.model.vo.PageInfo;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	BoardDao bDao;
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Override
	public int selectListCount() {
		
		return bDao.selectListCount(sqlSession);
	}

	@Override
	public ArrayList<Board> selectList(PageInfo pi) {
		
		return bDao.selectList(sqlSession,pi);
	}

	@Override
	public int increaseCount(int boardNo) {
		return 0;
	}

	@Override
	public Board selectboard(int boardNo) {
		return null;
	}

	@Override
	public int deleteBoard(int boardNo) {
		return 0;
	}

	@Override
	public int updateBoard(int boardNo) {
		return 0;
	}

	@Override
	public ArrayList<Reply> selectReply(int boardNo) {
		return null;
	}

	@Override
	public int insertReply(Reply r) {
		return 0;
	}

}
