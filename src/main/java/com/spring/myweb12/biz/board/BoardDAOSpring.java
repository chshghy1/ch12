package com.spring.myweb12.biz.board;

//import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

//DAO(Data Access Object)
@Repository
public class BoardDAOSpring { 
	
	@Autowired
	private JdbcTemplate jdbcTemplate; 
	
  // SQL 명령어들
    private final String BOARD_INSERT 
    = "insert into board(seq, title, writer,regDate,content,fileaddress) "
    		+ "values((select seq from(select ifnull(max(seq),0)+1 as seq from board) tmp),?,?,now(),?,?)";   //파일업로드
	private final String BOARD_UPDATE = "update board set title=?, content=? where seq=?";
	private final String BOARD_DELETE = "delete from board where seq=?";
	private final String BOARD_GET = "select * from board where seq=?";
	private final String BOARD_LIST = "select * from board order by seq desc";
	
	//검색용 쿼리추가
	private final String BOARD_LIST_T = "select * from board where title like ?  order by seq desc";
	private final String BOARD_LIST_C = "select * from board where content like ? order by seq desc";

	// CRUD 기능의 메소드 구현
	// 글 등록
	public void insertBoard(BoardVO vo) {
		System.out.println("===> Spring JDBC로 insertBoard() 기능 처리");
		jdbcTemplate.update(BOARD_INSERT, vo.getTitle(), vo.getWriter(),vo.getContent(),vo.getFileAddress()); //파일업로드
	}

	// 글 수정
	public void updateBoard(BoardVO vo) {
		System.out.println("===> Spring JDBC로 updateBoard() 기능 처리");
		jdbcTemplate.update(BOARD_UPDATE, vo.getTitle(), vo.getContent(), vo.getSeq());
	}

	// 글 삭제
	public void deleteBoard(BoardVO vo) {
		System.out.println("===> Spring JDBC로 deleteBoard() 기능 처리");
		jdbcTemplate.update(BOARD_DELETE, vo.getSeq());
	}

	// 글 상세 조회
	public BoardVO getBoard(BoardVO vo) {
		System.out.println("===> Spring JDBC로 getBoard() 기능 처리");
		Object[] args = { vo.getSeq() };
		return jdbcTemplate.queryForObject(BOARD_GET,new BoardRowMapper(),args);
	}

	// 글 목록 조회
	public List<BoardVO> getBoardList(BoardVO vo) {
		System.out.println("===> Spring JDBC로 getBoardList() 기능 처리");		
		
		//검색처리
		// - 미검색(초기)
		if(vo.getSearchCondition().equals("TITLE")  && vo.getSearchKeyword().equals("")) {
			
			return jdbcTemplate.query(BOARD_LIST, new BoardRowMapper());
		}
		// - 검색중
		else {
			//먼저 키워드만 읽어와서
			Object[] args = {"%"+vo.getSearchKeyword().trim()+"%"};  //공백제거
			
			if(vo.getSearchCondition().equals("TITLE")) {
				return jdbcTemplate.query(BOARD_LIST_T, new BoardRowMapper(), args);
			}
			else if(vo.getSearchCondition().equals("CONTENT")) {
				return jdbcTemplate.query(BOARD_LIST_C, new BoardRowMapper(), args);
			}
			else {
				return null;
			}
		}
	}
}
