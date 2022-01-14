package com.icia.board.service;

import com.icia.board.dto.BoardDetailDTO;
import com.icia.board.dto.BoardPagingDTO;
import com.icia.board.dto.BoardSaveDTO;
import com.icia.board.dto.BoardUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardService {
    Long save(BoardSaveDTO boardSaveDTO);

    List<BoardDetailDTO> findAll();

    BoardDetailDTO findById(Long boardId);

    Long update(BoardUpdateDTO boardUpdateDTO);

    Page<BoardPagingDTO> paging(Pageable pageable);
}
