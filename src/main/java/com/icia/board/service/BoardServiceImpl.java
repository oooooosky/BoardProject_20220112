package com.icia.board.service;

import com.icia.board.dto.BoardDetailDTO;
import com.icia.board.dto.BoardSaveDTO;
import com.icia.board.dto.BoardUpdateDTO;
import com.icia.board.entity.BoardEntity;
import com.icia.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository br;

    @Override
    public Long save(BoardSaveDTO boardSaveDTO) {
        BoardEntity boardEntity = BoardEntity.saveBoard(boardSaveDTO);
        return br.save(boardEntity).getId();
    }

    @Override
    public List<BoardDetailDTO> findAll() {
        List<BoardEntity> boardEntityList = br.findAll();
        List<BoardDetailDTO> boardDetailDTOList = BoardDetailDTO.toBoardDetailDTOList(boardEntityList);
        return boardDetailDTOList;
    }

    @Override
    public BoardDetailDTO findById(Long boardId) {
        /*
            Optional 사용
            먼저 null 체크를 해야 함.

            Optional 객체 메서드
            .isPresent() : 데이터가 있으면 true, 없으면 false 반환
            .isEmpty() : 데이터가 있으면 false, 없으면 true 반환
            .get() : Optional 객체에 들어있는 실제 데이터를 가져올 때
         */
        Optional<BoardEntity> optionalBoardEntity = br.findById(boardId);
        BoardDetailDTO boardDetailDTO = null;
        if (optionalBoardEntity.isPresent()) {
            BoardEntity boardEntity = optionalBoardEntity.get();
            boardDetailDTO = BoardDetailDTO.toBoardDetailDTO(boardEntity);
        }
        return boardDetailDTO;
//        return BoardDetailDTO.toBoardDetailDTO(br.findById(boardId).get());
    }

    @Override
    public Long update(BoardUpdateDTO boardUpdateDTO) {
            BoardEntity boardEntity = BoardEntity.updateBoard(boardUpdateDTO);
            return br.save(boardEntity).getId();
    }
}
