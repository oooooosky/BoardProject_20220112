package com.icia.board.dto;

import com.icia.board.entity.BoardEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardDetailDTO {

    private Long boardId;
    private String boardWriter;
    private String boardPassword;
    private String boardTitle;
    private String boardContents;
//    private LocalDateTime boardDate;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public static BoardDetailDTO toBoardDetailDTO(BoardEntity boardEntity) {
        BoardDetailDTO boardDetailDTO = new BoardDetailDTO();
        boardDetailDTO.setBoardId(boardEntity.getId());
        boardDetailDTO.setBoardWriter(boardEntity.getBoardWriter());
        boardDetailDTO.setBoardPassword(boardEntity.getBoardPassword());
        boardDetailDTO.setBoardTitle(boardEntity.getBoardTitle());
        boardDetailDTO.setBoardContents(boardEntity.getBoardContents());
//        boardDetailDTO.setBoardDate(boardEntity.getBoardDate());
        boardDetailDTO.setCreateTime(boardEntity.getCreateTime());
        boardDetailDTO.setUpdateTime(boardEntity.getUpdateTime());
        return boardDetailDTO;
    }

    public static List<BoardDetailDTO> toBoardDetailDTOList(List<BoardEntity> boardEntityList) {
        List<BoardDetailDTO> boardDetailDTOList = new ArrayList<>();
        for(BoardEntity b: boardEntityList) {
            boardDetailDTOList.add(toBoardDetailDTO(b));
        }
        return boardDetailDTOList;
    }
}
