package com.icia.board.controller;

import com.icia.board.dto.MemberSaveDTO;
import com.icia.board.entity.MemberEntity;
import com.icia.board.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService ms;

    @GetMapping("/save")
    public String saveForm() {
        return "member/save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute MemberSaveDTO memberSaveDTO) {
        Long memberId = ms.save(memberSaveDTO);
        return "index";
    }

}
