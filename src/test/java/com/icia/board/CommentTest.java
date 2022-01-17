package com.icia.board;

import com.icia.board.dto.BoardSaveDTO;
import com.icia.board.dto.CommentDetailDTO;
import com.icia.board.dto.CommentSaveDTO;
import com.icia.board.entity.CommentEntity;
import com.icia.board.repository.CommentRepository;
import com.icia.board.service.BoardService;
import com.icia.board.service.CommentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CommentTest {

    @Autowired
    private BoardService bs;

    @Autowired
    private CommentService cs;

    @Autowired
    private CommentRepository cr;

    @Test
    @Transactional  // 참조관계의 데이터를 사용할 때 @Transactional 을 사용해야함. 그래야 나중에 여러가지에 사용 가능
    @Rollback(value = false)    // Rollback을 하지 않겠다는 의미
    @DisplayName("댓글작성 테스트")
    public void commentTest() {
//        Long boardId = bs.save(new BoardSaveDTO("테스트작성자", "테스트비밀번호", "테스트제목", "테스트내용"));
//        Long commentId = cs.save(new CommentSaveDTO(boardId, "댓글작성자", "댓글내용"));
//        assertThat(commentId).isNotNull();
        assertThat(cs.save(new CommentSaveDTO(bs.save(new BoardSaveDTO("테스트작성자", "테스트비밀번호", "테스트제목", "테스트내용")), "댓글작성자", "댓글내용"))).isNotNull();
    }

    @Test
    @Transactional
    @DisplayName("댓글 조회 테스트")
    public void findByIdTest() {
        CommentEntity commentEntity = cr.findById(2L).get();
        // 원래 Long타입을 매개변수로 보낼 때 뒤에 L을 붙여서 보내야한다.
        System.out.println("commentEntity.toString() = " + commentEntity.toString());
        System.out.println("commentEntity.getId() = " + commentEntity.getId());
        System.out.println("commentEntity.getCommentWriter() = " + commentEntity.getCommentWriter());
        System.out.println("commentEntity.getCommentContents() = " + commentEntity.getCommentContents());
        System.out.println("commentEntity.getBoardEntity() = " + commentEntity.getBoardEntity());
        System.out.println("commentEntity.getBoardEntity().getBoardTitle() = " + commentEntity.getBoardEntity().getBoardTitle());
        // 이렇게 board_table에 속한 컬럼도 갖고 올 수 있다.
    }

    @Test
    @Transactional
    @DisplayName("댓글 목록 조회 테스트")
    public void findByAllTest() {
        List<CommentDetailDTO> commentDetailDTOList = cs.findAll(2L);
        for (CommentDetailDTO c: commentDetailDTOList) {
            System.out.println("c.toString() = " + c.toString());
        }
    }

}
