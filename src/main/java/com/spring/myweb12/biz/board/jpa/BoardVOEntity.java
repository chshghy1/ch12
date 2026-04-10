package com.spring.myweb12.biz.board.jpa;

//JPA학습
/*
	1. 테이블생성방식지정
			; hibernate.hbm2ddl.auto=create 또는 update 같은 옵션이 설정되어 있으면,
				(properties)
				. spring.jpa.hibernate.ddl-auto=create|update   //테이블생성
				  	- create  : 실행할 때 기존 테이블을 삭제하고 새로 생성. 종료 시에는 테이블을 그대로 둠.
                    - create-drop  : 실행할 때 테이블을 새로 생성하고, 애플리케이션 종료 시 테이블을 삭제(Test)
					- update  :  기존 테이블 구조를 유지하면서 엔티티 변경 사항만 반영. 데이터는 유지됨.
					- validate  : 엔티티와 테이블 구조가 일치하는지 검증만 하고, 테이블을 생성/수정하지 않음.
					- none :  아무 작업도 하지 않고, 이미 존재하는 테이블만 사용.
				. spring.jpa.show-sql=true  // sql쿼리가 보이게
				. spring.jpa.properties.hibernate.format_sql=true // 포맷에 맞춰 예쁘게 출력
	2. Entity(VO)
		- 테이블생성
		    . 클래스에 : @Entity선언
		    . 테이블이름 : @Table(name = "board")  으로 변경할수 있다. 선언안하면 클래스이름
	    - 생성자
			.  기본생성자는 필수이다.  하나도 작성안하면 컴파일러가 생성해 준다.
			.  생성자의 접근지정자는 public 또는 protected로 선언한다
			.  클래스 위에 선언하는 어노테이션
				@AllArgsConstructor(Lombok) : 모든 필드를 매개변수로 받는 생성자를 자동 생성.
				@NoArgsConstructor(Lombok)  : 매개변수가 없는 기본 생성자를 자동 생성.
				@RequiredArgsConstructor(Lombok) :  final이나 @NonNull 필드만 매개변수로 받는 생성자를 자동 생성.
				@Builder (생성자에 붙임)
					→ 해당 생성자의 매개변수를 기반으로 빌더 메서드를 자동 생성.
					→ 사실상 그 생성자를 직접 작성한 것과 동일한 효과를 내면서, 빌더까지 제공.
			. @Builder
			  public Member (Loing id, String name, String title, String content) {
			  	this.id = id;
			  	this.name = name;
			  	this.title = title;
			  	this.content = content;
			  }
			 -----------------------------------------------------------------
			  >> 생성자의 순서를 맞출 필요가 없다. 가독성이 좋다
			      . @Builder를 클래스에 붙이면 모든 필드를 대상으로 빌더가 생성.
				  . 하지만 특정 생성자에 붙이면, 그 생성자의 매개변수만을 대상으로 빌더를 생성합니다.
				  . @AllArgsConstructor나 @NoArgsConstructor 같은 Lombok의 생성자 애노테이션을 따로 쓸 필요가 없다.

			  >> 호출활용
			  	Member member = Member.builder()
					.id(1L)
					.name("홍길동")
					.title("공지사항")
					.content("내용입니다")
					.build();

		- 필드관계
			.  private로 일반적으로 선언한다
			.  관계지정을 설정한다
				- 일대일 :
						@OneToOne
						@JoinColumn(name="user_name")  // 내 테이블의 외래생성 컬럼
						private Uservo user;      // 상대 테이블의 기본키를 매핑한다.
						-------------------------------------------------------------
						- 양쪽선언 : 양방향 조회 가능
						- 한쪽선언 : 선언한 쪽에 FK가 생성되므로  FK를 활용한 단방향만 조회가능

				- N:1  (내가 N, 상대가 1)
						@ManyToOne                     // 다는 상대이고  일은 현재 이 엔터티이다
						@JoinColumn(name="user_name")  // 내 테이블의 외래생성 컬럼
						private Uservo user;      // 상대 테이블의 기본키를 매핑한다.
				- 1:N  (내가 1, 상대가 N)
						@OneToMany(mappedBy="user")    // 상대 엔터티  private User user 와 매핑선언
						private List<Board> boards = new ArrayList<>(); // 상대 테이블
						-------------------------------------------------------------
   						- 양쪽선언 : 양방향 조회 가능
						- 한쪽선언 : 선언한 쪽에 FK가 생성되므로  FK를 활용한 단방향만 조회가능

                - N:M (나도 N, 상대도 M)
                      (학생)
						@ManyToMany                                                                                                          @ManyToMany(mappedBy = "수강신청")
						@JoinTable(                                                                                                              private List<학생> students = new ArrayList<>();
							name = "수강신청",   // 중간 테이블 이름
							joinColumns = @JoinColumn(name = "학생번호"),        // 학생테이블의 PK를  수강신청의 FK 학생번호로 생성
							inverseJoinColumns = @JoinColumn(name = "강의번호")  // 강의테이블의 강의번호(PK)를  수강신청의 FK 강의번호로 생성
						)
						private List<강의> courses = new ArrayList<>();

					   (강의)
					   @ManyToMany(mappedBy = "수강신청")
					   private List<학생> students = new ArrayList<>();
                       ------------------------------------------------------------
   						- 양쪽선언 : 양방향 조회 가능 (현재양쪽선언)
						- 한쪽선언 : 선언한 쪽에 FK가 생성되므로  FK를 활용한 단방향만 조회가능
						- 만약 수강신청에 교수까지 다대다의 관계라면 (중첩)
							. 일반적으로  중간테이블을 두고 N:1, 또는 1:N으로 수동 매핑한다

                   * 주인(owner)
                   		. @ManyToOne이 붙은 엔티티, 혹은 @JoinTable을 선언한 쪽
						. @JoinColumn 또는 @JoinTable을 선언한 쪽
						. FK를 직접 관리하는 쪽(FK가 생성되는 쪽)

					비주인(non-owner)
						. mappedBy를 선언한 쪽
						. FK를 직접 관리하지 않고, 주인의 매핑을 참조만 함
						. DB에 새로운 FK 컬럼을 만들지 않음
						--------------------------------------------------------
						>> JPA에서 주인(owner)은 단순히 “FK가 생기는 쪽”이라는 의미만이 아니라,
						   관계를 관리하는 책임(테이블변경권한)을 가진 쪽이라는 뜻.
				   * 선언위치
				    	. @ManyToOne , @OneToMany
							- FK는 항상 N쪽 테이블에 존재해야 하므로 N이 항상주인이다.
							- N쪽에서는  mappedBy를 선언할수 없다.
						. @ManyToMany
							- FK는 양쪽 테이블에 생성되지 않는다. 중간테이블에서만 생성된다.
							- 주인은 양쪽 어느쪽이든 할수 있으나  둘다는 안된다.
							- 그런데 여기서는 학생은 이미 @JoinTable로 주인이 되었으니
							  둘다 주인은 할수없고  강의는 mappedBy를 선언해서 비주인으로 하는것이다.
							  (학생에 mappedBy선언불가)
    	2. 필드속성
			-  필드는  private로 선언하고 get/set메서드를 둔다
			-  반드시 @Id를 어느 필드나 get메서드에 선언해야하고
			-  @Id가 붙은 필드가 primary key가 된다
				. 자동증가처리
    				@GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가
    					- IDENTITY
							DB의 auto-increment 기능을 그대로 사용합니다.
							MySQL, MariaDB, PostgreSQL 등에서 흔히 사용.
							insert 시점에 PK가 DB에서 자동으로 생성됩니다.

						- SEQUENCE
							DB의 시퀀스 객체를 사용합니다.
							Oracle, PostgreSQL 등에서 지원.
							@SequenceGenerator와 함께 사용 가능.

						- TABLE
							별도의 키 생성용 테이블을 만들어 PK를 관리합니다.
							DB 독립성을 위해 사용하지만 성능은 떨어질 수 있습니다.

						- AUTO
							JPA가 DB 방언(dialect)에 따라 적절한 전략을 자동 선택합니다.

				- 필드연계 컬럼지정
					. @Column(columnDefinition="기본값")
					 	 - 기본값 정의
					 	 - 주의: DDL 자동 생성 시에만 반영됩니다. 이미 만들어진 테이블에는 적용되지 않는다

					. @Column(name="값" , nullable="true|false")
						 - 컬럼 이름과 null 허용 여부를 지정

					. @Column
						 - 엔티티 필드 이름을 그대로 DB 컬럼 이름으로 사용하고, 기본 타입 매핑 규칙을 따른다.
						---------------------------------------------------------------------------
						>> 혼용가능
							// 컬럼 이름 지정 + null 허용 여부 + 기본값 정의
							@Column(
								name = "student_name",
								nullable = false,
								columnDefinition = "varchar(100) default 'N/A'"
							)
							private String name;
		3. 메서드
			- 클래스에
				@Getter
				@Setter 선언으로 생성된다.

			- 일반 기능구현의 유틸리티 메서드는 활용해도 구현해도 무방하다
			- 다만,  역할의 구분으로 볼때 좋지않다.



 */


import com.spring.myweb12.biz.user.UserVO;
import jakarta.persistence.*;
import org.springframework.web.multipart.MultipartFile;
import lombok.*;
import java.util.Date;

@Entity
@Table(name = "boardentity")  // 이렇게 해도 spring네이밍규칙때문에 board_entity로 나온다
// 지정한 이름으로 하고 싶다면 properties에 spring.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl 이렇게 지정한다
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BoardVOEntity {

	//@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가 PK
	// int id;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가 PK
	private int seq;

	private String title;
	private String writer;
	private String content;
	private int cnt;

	@Temporal(TemporalType.DATE)
	private Date regDate;

	/*
		java.util.Date 또는 java.util.Calendar 타입을 매핑할 때
			@Temporal(TemporalType.DATE)      // 날짜만 (yyyy-MM-dd)
			private Date regDate;

			@Temporal(TemporalType.TIME)      // 시간만 (HH:mm:ss)
			private Date regTime;

			@Temporal(TemporalType.TIMESTAMP) // 날짜+시간 (yyyy-MM-dd HH:mm:ss)
			private Date regDateTime;
	 */

	//private int cnt;

	// 검색용 필드 (DB 저장 X)
	/*
		@Transient
		  - 필드는 JPA 매핑에서 제외, 엔티티 내부에서만 사용
		  - DB 테이블 컬럼으로 생성되지 않고, 영속성 컨텍스트에도 반영되지 않는다.
		  - 주로  파일업로드,  검색조건, 화면표시용 임시값 등에 사용
		  >> MultipartFile uploadFile 같은 경우는 DB에 저장할 필요가 없는 값이므로 @Transient만 붙이면 충분.
	 */
	@Transient
	private String searchCondition;

	@Transient
	private String searchKeyword;

	// 업로드 파일
	@Transient
	private MultipartFile uploadFile;

	// DB에 저장할 파일명/경로
	private String fileAddress;

	@Builder
	private BoardVOEntity(String title, String writer, String content, String fileAddress){
		this.title = title;
		this.writer = writer;
		this.content= content;
		this.fileAddress = fileAddress;
	}


	//관계설정 : user 1 - Board N
	//@ManyToOne                     // 다는 상대이고  일은 현재 이 엔터티이다
	//@JoinColumn(name="user")  // 내 테이블의 외래생성 컬럼
	//private UserVO user;      // 상대 테이블의 기본키를 매핑한다.
}