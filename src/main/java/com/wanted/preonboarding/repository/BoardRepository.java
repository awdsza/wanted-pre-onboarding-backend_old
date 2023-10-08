package com.wanted.preonboarding.repository;

import com.wanted.preonboarding.dto.SearchBoardForm;
import com.wanted.preonboarding.dto.SearchType;
import com.wanted.preonboarding.entity.Board;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardRepository {
    private final EntityManager em;

    public Long save(Board board){
        em.persist(board);
        return board.getId();
    }
    private String findBoardJPAQL(SearchBoardForm form){
        StringBuilder sb = new StringBuilder();
        sb.append("select b from Board b ");
        boolean isFirstCondition = true;

        if(!StringUtils.isBlank(form.getKeyword())){
            if(isFirstCondition){
                sb.append("where ");
                isFirstCondition = false;
            }else{
                sb.append("and ");
            }
            SearchType searchType = form.getSearchType();
            if(searchType == SearchType.TITLE){
                sb.append("title LIKE '%'||:keyword||'%'");
            }else if(searchType == SearchType.CONTENTS){
                sb.append("content LIKE '%'||:keyword||'%'");
            }else {
                sb.append("( title LIKE '%'||:keyword||'%' OR content LIKE '%'||:keyword||'%')");
            }
        }

        return sb.toString();
    }
    public List<Board> findAll(SearchBoardForm form){
        TypedQuery<Board> query = em.createQuery(findBoardJPAQL(form), Board.class);
        if (!StringUtils.isBlank(form.getKeyword())) {
            query = query.setParameter("keyword",form.getKeyword());
        }
        return query
                .setFirstResult((form.getPage()-(form.getPage() == 0 ? 0 : 1)) * 10)
                .setMaxResults(10)
                .getResultList();

    }
    public Board find(Long id){
        return em.find(Board.class,id);
    }
}
