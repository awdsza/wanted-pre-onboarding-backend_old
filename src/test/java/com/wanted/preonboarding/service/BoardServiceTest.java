package com.wanted.preonboarding.service;

import com.wanted.preonboarding.dto.BoardForm;
import com.wanted.preonboarding.dto.SearchBoardForm;
import com.wanted.preonboarding.dto.SearchType;
import com.wanted.preonboarding.entity.Board;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

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
    @Test
    public void 게시판검색테스트(){
        SearchBoardForm searchBoardForm = SearchBoardForm.createForm("일본", SearchType.TITLE,1);
        List<Board> list = boardService.selectBoardList(searchBoardForm);
        System.out.println("list.size() = " + list.size());
        Assertions.assertNotNull(list.size());
    }
    @Test
    public void 게시판상세검색테스트(){
        Board board = boardService.selectBoard(10L);
        Assertions.assertNotNull(board.getTitle());
    }
    @Test
    public void 게시판상세검색예외테스트(){
        Assertions.assertThrows(InvalidDataAccessApiUsageException.class,()->
                boardService.selectBoard(null));
    }
}