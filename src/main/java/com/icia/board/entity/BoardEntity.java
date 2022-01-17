package com.icia.board.entity;

import com.icia.board.dto.BoardSaveDTO;
import com.icia.board.dto.BoardUpdateDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "board_table")
public class BoardEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @Column
    private String boardWriter;

    @Column
    private String boardPassword;

    @Column
    private String boardTitle;

    @Column
    private String boardContents;

    // 댓글 연관관계
    @OneToMany(mappedBy = "boardEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CommentEntity> commentEntityList = new ArrayList<>();
    // 게시글 하나에 댓글이 여러개 붙기 때문에 리스트 타입으로 선언해야함.
    // 리스트 타입으로는 자식테이블의 엔티티가 들어감.

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

//    @Column
//    private LocalDateTime boardDate;

    public static BoardEntity saveBoard(BoardSaveDTO boardSaveDTO) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setBoardWriter(boardSaveDTO.getBoardWriter());
        boardEntity.setBoardPassword(boardSaveDTO.getBoardPassword());
        boardEntity.setBoardTitle(boardSaveDTO.getBoardTitle());
        boardEntity.setBoardContents(boardSaveDTO.getBoardContents());
//        boardEntity.setBoardDate(LocalDateTime.now());
        return boardEntity;
    }

    public static BoardEntity updateBoard(BoardUpdateDTO boardUpdateDTO) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setId(boardUpdateDTO.getBoardId());
        boardEntity.setBoardWriter(boardUpdateDTO.getBoardWriter());
        boardEntity.setBoardPassword(boardUpdateDTO.getBoardPassword());
        boardEntity.setBoardTitle(boardUpdateDTO.getBoardTitle());
        boardEntity.setBoardContents(boardUpdateDTO.getBoardContents());
//        boardEntity.setBoardDate(LocalDateTime.now());
//        boardEntity.setBoardDate(boardUpdateDTO.getBoardDate());
        return boardEntity;
    }
}
