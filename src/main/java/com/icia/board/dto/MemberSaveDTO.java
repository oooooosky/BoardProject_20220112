package com.icia.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberSaveDTO {

    private String memberEmail;
    private String memberPassword;
    private String memberName;

}
