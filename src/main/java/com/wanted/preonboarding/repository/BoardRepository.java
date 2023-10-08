package com.wanted.preonboarding.repository;

import com.wanted.preonboarding.entity.Board;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BoardRepository {
    private final EntityManager em;

    public Long save(Board board){
        em.persist(board);
        return board.getId();
    }
}
