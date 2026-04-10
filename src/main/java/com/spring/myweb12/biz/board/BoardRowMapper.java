package com.spring.myweb12.biz.board;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


public class BoardRowMapper implements RowMapper<BoardVO> {
	
	public BoardVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		BoardVO board = new BoardVO(); 
		
			board.setSeq(rs.getInt("SEQ"));
			board.setTitle(rs.getString("TITLE"));
			board.setWriter(rs.getString("WRITER"));
			board.setRegDate(rs.getDate("REGDATE"));
			board.setContent(rs.getString("CONTENT"));
			board.setCnt(rs.getInt("CNT"));
			
	        // DB 컬럼 → VO의 fileAddress
	        board.setFileAddress(rs.getString("FILEADDRESS"));
			
		return board;
	}
}