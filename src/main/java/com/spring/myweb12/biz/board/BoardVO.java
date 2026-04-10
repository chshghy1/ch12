package com.spring.myweb12.biz.board;

import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

//VO(Value Object)
public class BoardVO {
	private int seq;
	private String title;
	private String writer;
	private String content;
	private Date regDate;
	private int cnt;

	//검색추가 *************************************************
	private String searchCondition;
	private String searchKeyword;

	public String getSearchCondition() {
		return searchCondition;
	}

	public void setSearchCondition(String searchCondition) {
		this.searchCondition = searchCondition;
	}

	public String getSearchKeyword() {
		return searchKeyword;
	}

	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}
	// ******************************************************


	// 파일추가 ***********************************************
	/*
	 	MultipartFile 인터페이스
	 		- 업로드한 파일을 표현할때 사용되는 인터페이스
	 		- 업로드 파일의 메타정보(파일이름, 실제데이터, 파일크기등) 읽어올수 있다
	 		- 메서드
	 			. String getOriginalFilename()   : 업로드한 파일명을 문자열로 반환
	 			. void transferTo(File destFile) : 업로드한 파일을  destFile경로에 저장
	 			. boolean isEmpty()  : 업로드한 파일 존재여부(없으며 true)
	 */
	// 폼에서 넣은 파일을 vo에 바인딩하기 위해 사용
	private MultipartFile uploadFile;

	public MultipartFile getUploadFile() {
		return uploadFile;
	}
	public void setUploadFile(MultipartFile uploadFile) {
		this.uploadFile = uploadFile;
	}


	// DB에 저장할 파일명/경로
	private String fileAddress;

	public String getFileAddress() {
		return fileAddress;
	}
	public void setFileAddress(String fileAddress) {
		this.fileAddress = fileAddress;
	}

	// *******************************************************


	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public int getCnt() {
		return cnt;
	}

	public void setCnt(int cnt) {
		this.cnt = cnt;
	}

	@Override
	public String toString() {
		return "BoardVO [seq=" + seq + ", title=" + title + ", writer=" + writer + ", content=" + content + ", regDate="
				+ regDate + ", cnt=" + cnt + "]";
	}
}