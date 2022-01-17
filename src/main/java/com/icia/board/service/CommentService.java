package com.icia.board.service;

import com.icia.board.dto.CommentDetailDTO;
import com.icia.board.dto.CommentSaveDTO;

import java.util.List;

public interface CommentService {
    Long save(CommentSaveDTO commentSaveDTO);

    List<CommentDetailDTO> findAll(Long boardId);
}
