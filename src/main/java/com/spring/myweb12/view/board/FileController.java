package com.spring.myweb12.view.board;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
//@RequestMapping("/file")
public class FileController {

    //이미지보기
    @GetMapping("/file/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {

        try {

            Path base = Paths.get("C:/test"); // 저장된 디렉터리 경로객체생성
            Path fullPath = base.resolve(filename); //  해당 디렉터리 안에서 filename을 붙여서 전체 경로 생성
            fullPath.normalize();     // 전체경로를 정규화 (예: C:/test/../test/file.png → C:/test/file.png)

            if (!Files.exists(fullPath)) {
                return ResponseEntity.notFound().build();   // 응답객체를 가져와서 404를 설정하고 응답객체를 생성해서 반환
            }

            Resource resource = new UrlResource(fullPath.toUri());

            // MIME 타입 자동 추출 : Files.probeContentType() - 주어진 Path 객체를 보고 파일의 유형을 문자열로 반환
            String contentType = Files.probeContentType(fullPath);

            // MIME 타입 설정
            if (filename.endsWith(".png")) contentType = "image/png";
            else if (filename.endsWith(".jpg") || filename.endsWith(".jpeg")) contentType = "image/jpeg";
            else if (filename.endsWith(".mp4")) contentType = "video/mp4";

            if (contentType == null) {
                // 기본값을 범용으로 지정 (브라우저가 알아서 처리)
                contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
            }


            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")  //inline; filename="file.png"
                    .contentType(MediaType.parseMediaType(contentType))   // 응답의 MIME타입설정
                    .body(resource);   // 응답 본문(body)에 실제 파일 데이터을 담는다.

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();  // 서버 내부오류 500번 전송
            /*
                ResponseEntity.ok(body) → 200 OK (정상 응답)
                ResponseEntity.notFound().build() → 404 Not Found (리소스 없음)          
                ResponseEntity.badRequest().build() → 400 Bad Request (잘못된 요청)              
                ResponseEntity.internalServerError().build() → 500 Internal Server Error (서버 내부 오류)
             */
        }
    }
}
