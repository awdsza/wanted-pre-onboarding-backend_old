package com.wanted.preonboarding.controller;

import com.wanted.preonboarding.dto.BoardForm;
import com.wanted.preonboarding.dto.ResponseDto;
import com.wanted.preonboarding.dto.SearchBoardForm;
import com.wanted.preonboarding.dto.UserDto;
import com.wanted.preonboarding.entity.Board;
import com.wanted.preonboarding.exception.InvalidLoginAccessException;
import com.wanted.preonboarding.exception.InvalidUserDifferentException;
import com.wanted.preonboarding.service.BoardService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RestControllerAdvice
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/board")
    public ResponseEntity<?> postBoard(@RequestAttribute(name="user") UserDto userDto, @Valid @RequestBody BoardForm boardForm){
        boardForm.setAuthor(userDto.getEmail());
        Long id = boardService.createBoard(boardForm);
        return new ResponseEntity<>(new ResponseDto<>("게시판 생성이 완료되었습니다.",null), HttpStatus.CREATED);
    }
    @GetMapping("/board")
    public ResponseEntity<?> selectBoard(@ModelAttribute SearchBoardForm searchForm
            , HttpServletRequest request){
        List<Board> boards = boardService.selectBoardList(searchForm);
        return new ResponseEntity<>(new ResponseDto<>("조회되었습니다",boards),HttpStatus.OK);
    }
    @GetMapping("/board/{id}")
    public ResponseEntity<?> detailBoard(@PathVariable(name="id") long id){
        Board board = boardService.selectBoard(id);
        return new ResponseEntity<>(new ResponseDto<>("조회되었습니다",board),HttpStatus.OK);
    }
    @PutMapping("/board/{id}")
    public ResponseEntity<?> updateBoard(
            @RequestAttribute(name="user") UserDto userDto,
            @PathVariable(name="id") long id, @RequestBody BoardForm boardForm){
        boardForm.setAuthor(userDto.getEmail());
        boardService.updateBoard(id,boardForm);
        return new ResponseEntity<>(new ResponseDto<>("변경되었습니다",null),HttpStatus.OK);
    }

    @DeleteMapping("/board/{id}")
    public ResponseEntity<?> deleteBoard(@RequestAttribute(name="user") UserDto userDto, @PathVariable(name="id") long id){
        boardService.deleteBoard(id,userDto.getEmail());
        return new ResponseEntity<>(new ResponseDto<>("삭제되었습니다",null),HttpStatus.OK);
    }

    @ExceptionHandler(value = InvalidUserDifferentException.class)
    public Object invalidUserDifferentException(InvalidUserDifferentException e){
        return new ResponseEntity<>(new ResponseDto<String>("작성자만 게시물을 수정할 수 있습니다.",e.getMessage()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value= MethodArgumentNotValidException.class)
    public Object methodArgumentNotValidException(MethodArgumentNotValidException e){
        String message = e.getBindingResult()
                .getAllErrors()
                .get(0)
                .getDefaultMessage();
        return new ResponseEntity<>(new ResponseDto<String>("작성에 실패했습니다.",message),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = InvalidLoginAccessException.class)
    public Object invalidLoginAccessException(InvalidLoginAccessException e){
        return new ResponseEntity<>(new ResponseDto<String>("로그인한 사용자만 사용 가능합니다",e.getMessage()),HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(value = ExpiredJwtException.class)
    public Object expiredJwtException(ExpiredJwtException e){
        return new ResponseEntity<>(new ResponseDto<String>("토큰일자가 만료 되었습니다.",null), HttpStatus.BAD_REQUEST);
    }
}
