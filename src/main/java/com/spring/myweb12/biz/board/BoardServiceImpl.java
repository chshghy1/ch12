package com.spring.myweb12.biz.board;

import java.util.List;


import com.spring.myweb12.biz.board.jpa.BoardRepository;
import com.spring.myweb12.biz.board.jpa.BoardVOEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class BoardServiceImpl{

	@Autowired
	@Qualifier("boardRepository")
	private BoardRepository boardRepository;
	//private BoardDAOSpring boardDAO;
	//private BoardDAO boardDAO; 
	
	public void insertBoard(BoardVOEntity vo) {
		
		//boardDAO.insertBoard(vo);
		boardRepository.save(vo); // insert or update

	}

	public void updateBoard(BoardVOEntity vo) {
		//boardDAO.updateBoard(vo);
		boardRepository.save(vo); // insert or update
	}
	

	public void deleteBoard(Integer seq) {
		//boardDAO.deleteBoard(vo);
		boardRepository.deleteById(seq);
	}

	public BoardVOEntity getBoard(Integer seq) {
		//return boardDAO.getBoard(vo);
		return boardRepository.findById(seq).orElse(null); // JpaRepository 메서드 사용
	}


	//전체검색
	public List<BoardVOEntity> getBoardList(BoardVOEntity vo) {  // 검색을 위해 vo넣어둔다
		//return boardDAO.getBoardList(vo);            // 검색을 위해 vo넣어둔다

		if (vo == null || vo.getSearchCondition() == null) {
			return boardRepository.findAll();
		}


		if("TITLE".equals(vo.getSearchCondition())) {
			return boardRepository.findByTitleContaining(vo.getSearchKeyword());
		} else if("CONTENT".equals(vo.getSearchCondition())) {
			return boardRepository.findByContentContaining(vo.getSearchKeyword());
		} else {
			return boardRepository.findAll();
		}

	}

}