package com.wanted.preonboarding.service;

import com.wanted.preonboarding.dto.BoardForm;
import com.wanted.preonboarding.dto.SearchBoardForm;
import com.wanted.preonboarding.dto.SearchType;
import com.wanted.preonboarding.entity.Board;
import com.wanted.preonboarding.exception.InvalidUserDifferentException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

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
        assertThrows(DataIntegrityViolationException.class,()->
                boardService.createBoard(boardForm));

    }
    @Test
    public void 게시판등록작성자없는거테스트(){
        BoardForm boardForm = new BoardForm();
        boardForm.setTitle("안녕하세요 첫글이네요.");
        boardForm.setContent("안녕하세요 첫글이네요. \n 반가워요. ");
//        boardForm.setAuthor("lee1234@wanted.com");
        assertThrows(DataIntegrityViolationException.class,()->
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
        assertThrows(InvalidDataAccessApiUsageException.class,()->
                boardService.selectBoard(null));
    }
    @Test
    public void 게시판수정사용자넣지않는상태테스트(){
        BoardForm boardForm = new BoardForm();
        boardForm.setContent("");
        assertThrows(InvalidUserDifferentException.class,()->
                boardService.updateBoard(10L, boardForm));
    }
    @Test
    public void 게시판수정사용자작성자다른상태테스트(){
        BoardForm boardForm = new BoardForm();
        boardForm.setContent("");
        boardForm.setAuthor("sample@wanted.com");
        assertThrows(InvalidUserDifferentException.class,()->
                boardService.updateBoard(10L, boardForm));
    }
    @Test
    public void 게시판수정정상작동테스트(){
        BoardForm boardForm = new BoardForm();
        boardForm.setContent("나는 솔로아니다....");
        boardForm.setAuthor("test@test.com");
        boardService.updateBoard(10L, boardForm);
        Assertions.assertEquals(boardForm.getContent(),"나는 솔로아니다....");
    }
    @Test
    public void 게시판삭제테스트(){
        Long id = 10L;
        String email = "lee1234@wanted.com";
        boardService.deleteBoard(id,email);
        Board board = boardService.selectBoard(id);
        Assertions.assertNull(board);
    }
}