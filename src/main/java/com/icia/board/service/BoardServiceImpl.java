package com.icia.board.service;

import com.icia.board.common.PagingConst;
import com.icia.board.dto.BoardDetailDTO;
import com.icia.board.dto.BoardPagingDTO;
import com.icia.board.dto.BoardSaveDTO;
import com.icia.board.dto.BoardUpdateDTO;
import com.icia.board.entity.BoardEntity;
import com.icia.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @Override
    public Page<BoardPagingDTO> paging(Pageable pageable) {
        int page = pageable.getPageNumber(); // pageable 객체에서 .get을 통해 page 정보를 꺼낼 수 있음.
        // JPA가 관리하는 페이지 객체는 0번부터 관리
        // 그래서 삼항 연산자가 필요

        // 요청한 페이지가 1이면 페이지 값을 0으로 하고, 1이 아니면 요청 페이지에서 1을 뺀다.
//        page = page-1;
        page = (page==1)? 0:(page-1);
        // Pageable 타입의 pagealbe 객체를 넘겨주는 findAll() 호출
        Page<BoardEntity> boardEntities = br.findAll(PageRequest.of(page, PagingConst.PAGE_LIMIT, Sort.by(Sort.Direction.DESC, "id")));
        // findAll(PageRequest.of(어떤 페이지를, 어떤 갯수로), 정렬 (어떤 정렬 ,"기준으로 삼을 Entity 클래스의 필드이름"))
        // 기준으로 삼을 Entity 클래스의 필드이름에 언더바( _ ) 가 포함되어있으면 JPA 가 인식을 못해 오류 발생
        // findAll의 리턴값은 Page<Entity타입>로 넘어옴. 따라서 DTO로 변환해주는 작업이 필요함.
        // Page<BoardEntity> -> Page<BoardPagingDTO>
        // 기존 DTO에서 변환해주는 작업을 할 필요 없이 Page객체가 제공해주느 메서드를 사용하면 자동으로 변환시켜줌
        // 아래와 같은 변환용 코드
        Page<BoardPagingDTO> boardList = boardEntities.map(
                board -> new BoardPagingDTO(board.getId(),
                        board.getBoardWriter(),
                        board.getBoardTitle())
        );
        return boardList;
    }
}
