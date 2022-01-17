package com.icia.board.service;

import com.icia.board.dto.CommentDetailDTO;
import com.icia.board.dto.CommentSaveDTO;
import com.icia.board.entity.BoardEntity;
import com.icia.board.entity.CommentEntity;
import com.icia.board.repository.BoardRepository;
import com.icia.board.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final CommentRepository cr;
    private final BoardRepository br;

    @Override
    public Long save(CommentSaveDTO commentSaveDTO) {
        BoardEntity boardEntity = br.findById(commentSaveDTO.getBoardId()).get();
        CommentEntity commentEntity = CommentEntity.toSaveEntity(commentSaveDTO, boardEntity);
        return cr.save(commentEntity).getId();
    }

    @Override
    public List<CommentDetailDTO> findAll(Long boardId) {
        BoardEntity boardEntity = br.findById(boardId).get();
        List<CommentEntity> commentEntityList = boardEntity.getCommentEntityList();
        List<CommentDetailDTO> commentList = new ArrayList<>();
        for (CommentEntity c: commentEntityList) {
            CommentDetailDTO commentDetailDTO = CommentDetailDTO.toCommentDetailDTO(c);
            commentList.add(commentDetailDTO);
        }
        return commentList;
    }

}
