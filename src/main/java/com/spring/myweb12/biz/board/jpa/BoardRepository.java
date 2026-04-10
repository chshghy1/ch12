package com.spring.myweb12.biz.board.jpa;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// 스프링에서는 인터페이스와 클래스 어디든 붙일 수 있지만,
// Spring Data JPA 인터페이스에는 생략 가능하고,
// 직접 구현한 클래스에는 꼭 붙여야 한다.
// 스프링의 프록시 객체로 빈이 등록되고  주입도 프록시객체로 주입되어 실행가능하다
// 그래서 인터페이스의 이름과 구현체의 이름을 동일하게 하면 충돌날수있다. 다르게 해라.

//여기가 DAO이다
@Repository
public interface BoardRepository extends JpaRepository<BoardVOEntity, Integer> {   // Entity, Id 지정

    // SQL 자동생성
    /*
            저장/수정	save(S entity)	엔티티 저장 또는 수정 (PK 존재 여부에 따라 insert/update)
                        saveAll(Iterable entities)	여러 엔티티를 한 번에 저장

            조회	        findById(ID id)	PK로 엔티티 조회 (Optional 반환)
                        findAll()	모든 엔티티 조회
                        findAllById(Iterable ids)	여러 PK로 엔티티 조회
                        getOne(ID id) / getReferenceById(ID id)	프록시 객체 반환 (지연 로딩)

            삭제	        delete(T entity)	엔티티 삭제
                        deleteById(ID id)	PK로 엔티티 삭제
                        deleteAll()	모든 엔티티 삭제
                        deleteAllInBatch()	전체 엔티티를 배치 삭제 (성능 최적화)
                        deleteAllByIdInBatch(Iterable ids)	여러 PK를 한 번에 삭제
                        deleteInBatch(Iterable entities)	여러 엔티티를 배치 삭제

            카운트/존재 여부	count()	전체 엔티티 개수 반환

            * 커스터마이징 조건검색
                // 제목에 특정 키워드가 포함된 게시글 검색
                List<BoardVO> findByTitleContaining(String keyword);

                // 내용에 특정 키워드가 포함된 게시글 검색
                List<BoardVO> findByContentContaining(String keyword);

                // 제목 또는 내용에 특정 키워드가 포함된 게시글 검색
                List<BoardVO> findByTitleContainingOrContentContaining(String titleKeyword, String contentKeyword);

                // 제목과 내용 모두 특정 키워드가 포함된 게시글 검색
                List<BoardVO> findByTitleContainingAndContentContaining(String titleKeyword, String contentKeyword);
     */

    // 그외 커스터마이징해서 추가할때만 선언한다
    //  커스터마이징
     /*
        네이밍 규칙
             Containing을 붙이면 like %title% 쿼리를 만들고 안붙이면 동일한 값으 찾는다
                - StartsWith, EndsWith, Between, GreaterThan, LessThan, OrderBy 등
      */
    List<BoardVOEntity> findByTitleContaining(String title);
    List<BoardVOEntity> findByContentContaining(String content);
}