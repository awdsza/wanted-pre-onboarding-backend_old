package com.wanted.preonboarding.service;

import com.wanted.preonboarding.dto.BoardForm;
import com.wanted.preonboarding.dto.SearchBoardForm;
import com.wanted.preonboarding.entity.Board;
import com.wanted.preonboarding.exception.InvalidUserDifferentException;
import com.wanted.preonboarding.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    @Transactional
    public Long createBoard(BoardForm boardForm){
        Board board = Board.createNewBoard(boardForm);
        return boardRepository.save(board);
    }
    public List<Board> selectBoardList(SearchBoardForm form){
        return boardRepository.findAll(form);
    }

    public Board selectBoard(Long id){
        return boardRepository.find(id);
    }

    @Transactional
    public void updateBoard(Long id, BoardForm boardForm){
        Board board = selectBoard(id);
        if(!board.getCreator().equals(boardForm.getAuthor())){
            throw new InvalidUserDifferentException("작성자만 게시물을 수정할 수 있습니다");
        }
        board.updateBoard(board,boardForm);
    }
    @Transactional
    public void deleteBoard(Long id,String email){
        Board board = boardRepository.find(id);
        if(!board.getCreator().equals(email)){
            throw new InvalidUserDifferentException("작성자만 게시물을 삭제할 수 있습니다");
        }
        boardRepository.delete(board);
    }
}
