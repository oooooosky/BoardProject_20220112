package com.icia.board.dto;

import com.icia.board.entity.CommentEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDetailDTO {

    private Long commentId;
    private Long boardId;
    private String commentWriter;
    private String commentContents;
    private LocalDateTime createTime;

    public static CommentDetailDTO toCommentDetailDTO(CommentEntity commentEntity) {
        CommentDetailDTO commentDetailDTO = new CommentDetailDTO();
        commentDetailDTO.setCommentId(commentEntity.getId());
        commentDetailDTO.setBoardId(commentEntity.getBoardEntity().getId());
        commentDetailDTO.setCommentWriter(commentEntity.getCommentWriter());
        commentDetailDTO.setCommentContents(commentEntity.getCommentContents());
        commentDetailDTO.setCreateTime(commentEntity.getCreateTime());
        return commentDetailDTO;
    }
}
