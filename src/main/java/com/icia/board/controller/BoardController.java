package com.icia.board.controller;

import com.icia.board.common.PagingConst;
import com.icia.board.dto.BoardDetailDTO;
import com.icia.board.dto.BoardPagingDTO;
import com.icia.board.dto.BoardSaveDTO;
import com.icia.board.dto.BoardUpdateDTO;
import com.icia.board.service.BoardService;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;

// 잘 보고 import 할 것!!!
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j // 로그를 기록할 수 있는 라이브러리(어노테이션)
@RequestMapping("/board")
public class BoardController {

    private final BoardService bs;

    // 글작성 페이지 요청
    @GetMapping("/save")
    public String saveForm(Model model) {
        model.addAttribute("board", new BoardSaveDTO());
        return "board/save";
    }

    // 글작성 저장
    // thymeleaf를 쓰지 않는다면 @ModelAttribute 생략 가능
    @PostMapping("/save")
    public String save(@Validated @ModelAttribute("board") BoardSaveDTO boardSaveDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "board/save";
        }

        bs.save(boardSaveDTO);
        return "index";
    }

    // 글목록 출력
    @GetMapping("/")
    public String findAll(Model model) {
        log.info("findAll 호출");
        List<BoardDetailDTO> boardDetailDTOList = bs.findAll();
        model.addAttribute("boardList", boardDetailDTOList);
        return "board/findAll";
    }

    // 페이징처리(board?page=5)
    // 5번글(/board/5)
    // @PageableDefault(page=1) : 별다른 페이징 요청이 없을 때 (처음 요청) 1페이지를 내보내겠다.
    @GetMapping
    public String paging(@PageableDefault(page = 1) Pageable pageable, Model model) {
        Page<BoardPagingDTO> boardList = bs.paging(pageable);
        model.addAttribute("boardList", boardList);

        // startPage, endPage 계산
        // 현재 2페이지 일 때 시작 페이지 1, 끝 페이지 3
        // 현재 7페이지 일 때 시작 페이지 6, 끝 페이지 8
        int startPage = (((int)(Math.ceil((double) pageable.getPageNumber()/PagingConst.BLOCK_LIMIT)))-1)*PagingConst.BLOCK_LIMIT + 1;
        int endPage = ((startPage+PagingConst.BLOCK_LIMIT-1)<boardList.getTotalPages())?startPage+PagingConst.BLOCK_LIMIT-1 : boardList.getTotalPages();

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "board/paging";
    }

    @GetMapping("/{boardId}")
    // {}앞에 / 가 붙어도 생략가능
    public String findById(@PathVariable Long boardId, Model model) {
        // 변수를 찍고싶다면 {} 가 반드시 필요. {}안에 변수가 들어감
        // 로그 종류
        // info : 하나하나 동작을 전부 기록
        // warn : 경고, 에러는 아니지만 문제의 소지가 있다.
        // error : 문제 발생
        log.info("글보기 메서드 호출. 요청글번호 {}", boardId);
        BoardDetailDTO boardDetailDTO = bs.findById(boardId);
        model.addAttribute("boardDetailDTO",boardDetailDTO);
        return "board/findById";
    }

//    @PostMapping("/{boardId}")
//    public @ResponseBody BoardDetailDTO ajaxFindById(@PathVariable Long boardId) {
//        BoardDetailDTO boardDetailDTO = bs.findById(boardId);
//        return boardDetailDTO;
//    }

    // ResponseEntity로 데이토와 상태코드를 같이 보내는 방법.
    @PostMapping("/{boardId}")
    public ResponseEntity findById2(@PathVariable Long boardId) {
        BoardDetailDTO boardDetailDTO = bs.findById(boardId);
        return new ResponseEntity<BoardDetailDTO>(boardDetailDTO, HttpStatus.OK);
        // 보내고자 하는 데이터의 타입을 <> 안에 써야한다. (생략가능하긴하지만 쓰는게 좋음)
        // 양식 : new ResponseEntity<보내고자하는 데이터 타입>(데이터 변수이름, 상태코드)

        // HttpStatus.BAD_REQUEST를 통해 잘못 들어온 요청에 대해 분기처리를 할 수 있다.
    }

    @GetMapping("/update/{boardId}")
    public String updateForm(@PathVariable Long boardId, Model model) {
        BoardDetailDTO boardDetailDTO = bs.findById(boardId);
        model.addAttribute("boardDetailDTO",boardDetailDTO);
        return "board/update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute BoardUpdateDTO boardUpdateDTO) {

        // 내가 한 것
//        BoardDetailDTO boardDetailDTO = bs.findById(boardUpdateDTO.getBoardId());
//        if (boardDetailDTO.getBoardPassword().equals(boardUpdateDTO.getBoardPassword())) {
//            bs.update(boardUpdateDTO);
//            return "redirect:/board/"+boardUpdateDTO.getBoardId();
//        } else {
//            return "redirect:/board/";
//        }

        // 선생님이 한 것
        bs.update(boardUpdateDTO);
        return "redirect:/board/"+boardUpdateDTO.getBoardId();
    }

    @PutMapping("/{boardId}")
    public ResponseEntity update2(@RequestBody BoardUpdateDTO boardUpdateDTO) {

        // 내가 한 것
//        System.out.println(boardUpdateDTO.getBoardPassword());
//        BoardDetailDTO boardDetailDTO = bs.findById(boardUpdateDTO.getBoardId());
//        System.out.println(boardDetailDTO);
//        if (boardDetailDTO.getBoardPassword().equals(boardUpdateDTO.getBoardPassword())) {
//            bs.update(boardUpdateDTO);
//            return new ResponseEntity(HttpStatus.OK);
//        } else {
//            return new ResponseEntity(HttpStatus.BAD_REQUEST);
//        }

        // 선생님이 한 것
        bs.update(boardUpdateDTO);
        return new ResponseEntity(HttpStatus.OK);

    }

}
