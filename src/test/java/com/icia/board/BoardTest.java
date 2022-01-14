package com.icia.board;

import com.icia.board.common.PagingConst;
import com.icia.board.dto.BoardDetailDTO;
import com.icia.board.dto.BoardPagingDTO;
import com.icia.board.dto.BoardSaveDTO;
import com.icia.board.dto.BoardUpdateDTO;
import com.icia.board.entity.BoardEntity;
import com.icia.board.repository.BoardRepository;
import com.icia.board.service.BoardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BoardTest {

    @Autowired
    private BoardService bs;

    // 페이징 테스트용 BoardRepository 클래스 주입
    @Autowired
    private BoardRepository br;

    @Test
    @Transactional
    @Rollback
    @DisplayName("글작성 테스트")
    public void boardSaveTest() {
        bs.save(new BoardSaveDTO("테스트작성자", "테스트비밀번호", "테스트제목", "테스트내용"));
    }

    @Test
    @DisplayName("글작성 30개")
    public void boardSaveTest30() {
//        for (int i=1; i<=30; i++) {
//            bs.save(new BoardSaveDTO("작성자"+i,"비밀번호"+i,"제목"+i,"내용"+i));
//        }
        // IntStream.range(1, 30) : 1부터 29
        // IntStream.rangeClosed(1, 30) : 1부터 30
        IntStream.rangeClosed(1, 30).forEach(i -> {
            bs.save(new BoardSaveDTO("작성자"+i,"비밀번호"+i,"제목"+i,"내용"+i));
        });
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("목록조회 테스트")
    public void boardListTest() {
        for (int i=1; i<=5; i++) {
            bs.save(new BoardSaveDTO("조회용작성자"+i,"조회용비밀번호"+i,"조회용제목"+i,"조회용내용"+i));
        }
        assertThat(bs.findAll().size()).isEqualTo(5);
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("상세조회 테스트")
    public void boardDetailTest() {
        assertThat(bs.findById(bs.save(new BoardSaveDTO("상세조회용작성자", "상세조회용비밀번호", "상세조회용제목", "상세조회용내용")))).isNotNull();
//        assertThat(bs.findById(bs.save(new BoardSaveDTO("상세조회용작성자", "상세조회용비밀번호", "상세조회용제목", "상세조회용내용"))).getBoardWriter()).isEqualTo("상세조회용작성자");
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("수정 테스트")
    public void boardUpdateTest() {

        Long boardId = bs.save(new BoardSaveDTO("수정전작성자", "수정전비밀번호", "수정전제목", "수정전내용"));
        BoardDetailDTO boardDetailDTO = bs.findById(boardId);
        bs.update(new BoardUpdateDTO(boardId, "수정후작성자", "수정후비밀번호", "수정후제목", "수정후내용"));
        assertThat(boardDetailDTO.getBoardTitle()).isNotEqualTo(bs.findById(boardId).getBoardTitle());

    }

    @Test
    @DisplayName("상항 연산자 테스트 (자바문법 설명용)")
    public void test1() {

        int num = 10;
        int num2 = 0;

        if(num == 10) {
            num2 =5;
        } else {
            num2 = 100;
        }

        // 위 if 와 아래 코드는 같은 결과를 출력
        num2 = (num==10)? 5: 100;

        // 기본적인 구조
        // 변수 = (조건식) ? true일 때 값 : false일 때 값
        // 조건이 맞으면 true일 때 값이 변수에 담김, false일 때는 반대

        // 정말 단순한 if else일 때 위와 같은 삼항 연산자 사용
        // if else에 같은 변수로 값에 차이를 둘 때 주로 사용
    }

    @Test
    @Transactional
    @DisplayName("페이징 테스트")
    public void pagingTest() {

        // 글갯수 30개 기준 한페이지 5개 기준 페이징 처리
//        int page = 0; // JPA 기준 1페이지 (첫번째 페이지)
        int page = 5; // JPA 기준 6페이지 (마지막 페이지)
        Page<BoardEntity> boardEntities = br.findAll(PageRequest.of(page, PagingConst.PAGE_LIMIT, Sort.by(Sort.Direction.DESC, "id")));

        // Page 객체가 제공해주는 매서드 확인
        System.out.println("boardEntities.getContent() = " + boardEntities.getContent()); // 요청 페이지에 들어있는 데이터, toString이 없기 때문에 주소값이 출력
        System.out.println("boardEntities.getTotalElements() = " + boardEntities.getTotalElements()); // 전체 글 갯수
        System.out.println("boardEntities.getNumber() = " + boardEntities.getNumber()); // JPA 기준 요청 페이지
        System.out.println("boardEntities.getTotalPages() = " + boardEntities.getTotalPages()); // 전체 페이지 갯수
        System.out.println("boardEntities.getSize() = " + boardEntities.getSize()); // 한 페이지에 보여지는 글 갯수
        System.out.println("boardEntities.hasPrevious() = " + boardEntities.hasPrevious()); // 이전 페이지 존재 여부
        System.out.println("boardEntities.isFirst() = " + boardEntities.isFirst()); // 첫 페이지인지 여부
        System.out.println("boardEntities.isLast() = " + boardEntities.isLast()); // 마지막 페이지인지 여부

        // Page<BoardEntity> -> Page<BoardPagingDTO>
        // 일반적인 방법으로 변환하면 페이징객체가 제공하는 메서드를 사용 못함.
        // .map() : Entity 가 담긴 페이지 객체를 dto 가 담긴 페이지 객체로 변환해주는 역할
        // board : 반복용 변수로 만든 변수, foreach 문의 반복 변수와 동일한 개념
        // 여기서의 반복변수(board)는 Entity 객체임. Entity에 담긴 정보를 BoardPagingDTO에 담음.
        Page<BoardPagingDTO> boardList = boardEntities.map(
                board -> new BoardPagingDTO(board.getId(),
                                            board.getBoardWriter(),
                                            board.getBoardTitle())
        );

        System.out.println("boardList.getContent() = " + boardList.getContent()); // 요청 페이지에 들어있는 데이터, toString이 없기 때문에 주소값이 출력
        System.out.println("boardList.getTotalElements() = " + boardList.getTotalElements()); // 전체 글 갯수
        System.out.println("boardList.getNumber() = " + boardList.getNumber()); // JPA 기준 요청 페이지
        System.out.println("boardList.getTotalPages() = " + boardList.getTotalPages()); // 전체 페이지 갯수
        System.out.println("boardList.getSize() = " + boardList.getSize()); // 한 페이지에 보여지는 글 갯수
        System.out.println("boardList.hasPrevious() = " + boardList.hasPrevious()); // 이전 페이지 존재 여부
        System.out.println("boardList.isFirst() = " + boardList.isFirst()); // 첫 페이지인지 여부
        System.out.println("boardList.isLast() = " + boardList.isLast()); // 마지막 페이지인지 여부

    }

}
