package com.icia.board.controller;

import com.icia.board.dto.CommentDetailDTO;
import com.icia.board.dto.CommentSaveDTO;
import com.icia.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService cs;

    @PostMapping("/save")
    public @ResponseBody List<CommentDetailDTO> save(@ModelAttribute CommentSaveDTO commentSaveDTO) {
        Long commentId = cs.save(commentSaveDTO);
        List<CommentDetailDTO> commentList = cs.findAll(commentSaveDTO.getBoardId());
        return null;
    }

}
