package com.wanted.preonboarding.service;

import com.wanted.preonboarding.dto.BoardForm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;

@SpringBootTest//부트를 띄운 상태로 테스트
@Transactional
class BoardServiceTest {

    @Autowired
    private BoardService boardService;

    @Test
    public void 게시판등록테스트(){
        BoardForm boardForm = new BoardForm();
        boardForm.setTitle("안녕하세요 첫글이네요.");
        boardForm.setContent("안녕하세요 첫글이네요. \n 반가워요. ");
        boardForm.setAuthor("lee1234@wanted.com");
        Long id = boardService.createBoard(boardForm);
        Assertions.assertNotNull(id);
    }
    @Test
    public void 게시판등록제목없는거테스트(){
        BoardForm boardForm = new BoardForm();
        //boardForm.setTitle("안녕하세요 첫글이네요.");
        boardForm.setContent("안녕하세요 첫글이네요. \n 반가워요. ");
        boardForm.setAuthor("lee1234@wanted.com");
        Assertions.assertThrows(DataIntegrityViolationException.class,()->
                boardService.createBoard(boardForm));

    }
    @Test
    public void 게시판등록작성자없는거테스트(){
        BoardForm boardForm = new BoardForm();
        boardForm.setTitle("안녕하세요 첫글이네요.");
        boardForm.setContent("안녕하세요 첫글이네요. \n 반가워요. ");
//        boardForm.setAuthor("lee1234@wanted.com");
        Assertions.assertThrows(DataIntegrityViolationException.class,()->
                boardService.createBoard(boardForm));

    }
}