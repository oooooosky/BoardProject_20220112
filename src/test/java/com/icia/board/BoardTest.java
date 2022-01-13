package com.icia.board;

import com.icia.board.dto.BoardDetailDTO;
import com.icia.board.dto.BoardSaveDTO;
import com.icia.board.dto.BoardUpdateDTO;
import com.icia.board.service.BoardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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

}
