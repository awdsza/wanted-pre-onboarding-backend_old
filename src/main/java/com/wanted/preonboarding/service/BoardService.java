package com.wanted.preonboarding.service;

import com.wanted.preonboarding.dto.BoardForm;
import com.wanted.preonboarding.dto.SearchBoardForm;
import com.wanted.preonboarding.entity.Board;
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
}
