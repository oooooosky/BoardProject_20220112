package com.icia.board.entity;

import com.icia.board.dto.CommentSaveDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "comment_table")
public class CommentEntity extends BaseEntity {

    // 댓글번호, 작성자, 내용, 원글 (시간은 BaseEntity가 생성해줌)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    // JPA에서 참조관계 맺기
    // JPA 문법이기 때문에 외우기
    // 원글의 게시글 번호를 참조하기 위한 설정
    // 댓글과 게시글의 관계에서 댓글 입장에선 -> N : 1 관계, 게시글 입장에선 1 : N
    @ManyToOne(fetch = FetchType.LAZY)  // @ManyToOne : N : 1 관계로 설정 (현재 엔티티를 기준으로 결정, 댓글용 엔티티이므로  N : 1)
    @JoinColumn(name = "board_id")  // 부모 테이블(참조하고자 하는 테이블)의 pk 컬럼 이름
    private BoardEntity boardEntity;    // 참조하고자 하는 테이블을 관리하는 엔티티

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    // LAZY : 다른 Entity를  쓸 때 만 요청해서 가져옴
    // EAGER : 다른 Entity를 쓰던 말던 무조건 가져옴.
    // EAGER는 데이터의 양이 커 부담이 크기 때문에 잘 사용하지 않는다.

    @Column
    private String commentWriter;

    @Column
    private String commentContents;

    public static CommentEntity toSaveEntity(CommentSaveDTO commentSaveDTO, BoardEntity boardEntity) {
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setCommentWriter(commentSaveDTO.getCommentWriter());
        commentEntity.setCommentContents(commentSaveDTO.getCommentContents());
        commentEntity.setBoardEntity(boardEntity);  // jpa가 제공하는 기능을 활용하기 위해 Entity데이터 자체를 넣음.
        return commentEntity;
    }
}
