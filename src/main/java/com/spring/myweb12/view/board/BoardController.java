package com.spring.myweb12.view.board;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.spring.myweb12.biz.board.BoardServiceImpl;
import com.spring.myweb12.biz.board.jpa.BoardVOEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/api/boards")
public class BoardController {
	
	@Autowired
	private BoardServiceImpl service;


	//전체 목록 조회
	@GetMapping
	public ResponseEntity<List<BoardVOEntity>> getBoardList(){
		List<BoardVOEntity> boardList = service.getBoardList(null);
		return ResponseEntity.ok(boardList);
	}




	//상세조회
	@GetMapping("/{id}")
	public ResponseEntity<BoardVOEntity> getBoard(@PathVariable Integer id){

		BoardVOEntity board = service.getBoard(id);

		return ResponseEntity.ok(board);

	}

	//글 수정
	@PutMapping("/{id}")

	public ResponseEntity<?> updateBoard(
			@PathVariable Integer id,
			@RequestParam("title") String title,
			@RequestParam("content") String content,
			@RequestParam(value = "uploadFile", required = false) MultipartFile uploadFile
	) throws IOException {

		BoardVOEntity vo = service.getBoard(id);
		vo.setTitle(title);
		vo.setContent(content);


		String realPath = "C:/test/";
		if (uploadFile != null && !uploadFile.isEmpty()) {
			String fileName = uploadFile.getOriginalFilename();
			uploadFile.transferTo(new File(realPath + fileName)); // 로컬저장
			vo.setFileAddress(fileName); // db에 파일명저장
		}

		service.updateBoard(vo);

		return ResponseEntity.ok("수정 성공");
	}



	//글등록
	@PostMapping
	public ResponseEntity<?> insertBoard(
			@RequestParam("title") String title,
			@RequestParam("writer") String writer,
			@RequestParam("content") String content,
			@RequestParam(value = "uploadFile", required = false) MultipartFile uploadFile
	) throws IOException {

		BoardVOEntity vo = new BoardVOEntity();
		vo.setTitle(title);
		vo.setWriter(writer);
		vo.setRegDate(new Date()); // 등록일 직접 세팅
		vo.setContent(content);

		String realPath = "C:/test/";

		if (uploadFile != null && !uploadFile.isEmpty()) {
			String fileName = uploadFile.getOriginalFilename();
			uploadFile.transferTo(new File(realPath + fileName));

			vo.setFileAddress(fileName);
		}

		service.insertBoard(vo);

		return ResponseEntity.ok("등록 성공");
	}


	//삭제
	/*
		     fetch/axios                                  controller                              method
		-----------------------------------------------------------------------------------------------------------------------------
		DELETE /boards/1234                      >>>  @DeleteMapping("/boards/{id}")   >>>  deleteBoard(@PathVariable int id)
		DELETE /boards/delete?id=1234            >>>  @DeleteMapping("/boards/delete") >>>  deleteBoard(@RequestParam int id)
        DELETE /boards + Body: { "id": 1234 }    >>>  @DeleteMapping("/boards")        >>>  deleteBoard(@RequestBody BoardEntityVO vo)

	 */
	@DeleteMapping("/{seq}")
	public ResponseEntity<?> deleteBoard(@PathVariable Integer seq) {
		service.deleteBoard(seq);   // 서비스수정
		return ResponseEntity.ok("삭제 성공");
	}
		
}












