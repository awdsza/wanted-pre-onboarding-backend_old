package com.wanted.preonboarding.service;

import com.wanted.preonboarding.dto.BoardForm;
import com.wanted.preonboarding.entity.Board;
import com.wanted.preonboarding.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    @Transactional
    public Long createBoard(BoardForm boardForm){
        Board board = Board.createNewBoard(boardForm);
        return boardRepository.save(board);
    }
}
