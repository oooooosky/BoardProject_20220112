package com.icia.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardSaveDTO {

    @NotBlank(message = "작성자는 필수입니다.")
    private String boardWriter;
    @NotBlank(message = "비밀번호는 필수입니다.")
    private String boardPassword;
    @NotBlank(message = "제목은 필수입니다.")
    private String boardTitle;
    @NotBlank(message = "내용은 필수입니다.")
    private String boardContents;

}
