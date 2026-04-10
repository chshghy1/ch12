package com.spring.myweb12.biz.common;

//모두 java.sql속의 클래스이다.
import java.sql.Connection;      // sql의 Connection이다.   여러개중 잘 선택할 것!
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


/*
	메소드들을 static정적 메소드로 만들어 놓고,,,  DAO쪽에서  클래스명.메소드()로 호출하려고 한다.
*/
public class JDBCUtil {
	
	//접속
	public static Connection getConnection() {
		
		try {
			
			//Class.forName("com.mysql.cj.jdbc.Driver");   //useSSL=true&amp;verifyServerCertificate=false&amp;useUnicode=true&amp;characterEncoding=utf8
			return DriverManager.getConnection("jdbc:mysql://localhost:3306/studydb?serverTimezone=UTC","root","mysql"); 
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	//닫기 - PreparedStatement - 오버로딩을  close()메소드는 같은 이름으로 두개를 만들어 놓은것이다.
	public static void close(PreparedStatement stmt, Connection conn) {
		
		//PrepareStatement닫기
		if(stmt != null) {
			
			try {
				if(!stmt.isClosed()) stmt.close();
			}catch (Exception e) {
				e.printStackTrace();
			}finally {
				stmt = null;
			}			
		}
		//Connection닫기
		if(conn != null) {
			
			try {
				if(!conn.isClosed()) conn.close();
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				conn = null;
			}
		}
		
	}
	
	//닫기- ResultSet,PreparedStatement
	public static void close(ResultSet rs, PreparedStatement stmt, Connection conn) {
		
		//ResultSet 닫기
		if(rs != null) {
			
			try{
				if(!rs.isClosed()) rs.close();
			}catch (Exception e) {
				e.printStackTrace();
			}finally {
				rs = null;
			}
					
		}
		//PreparedStatement 닫기
		if(stmt != null) {
			
			try {
				if(!stmt.isClosed()) stmt.close();
			}catch (Exception e) {
				e.printStackTrace();
			}finally {
				stmt = null;
			}
		}
		//Connetion 닫기
		if(conn != null) {
			
			try{
				if(!conn.isClosed()) conn.close();
			}catch (Exception e) {
				e.printStackTrace();
			}finally {
				conn = null;
			}
					
		}		
	}

	
}














