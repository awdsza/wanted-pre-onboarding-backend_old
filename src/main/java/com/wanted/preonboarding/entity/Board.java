package com.wanted.preonboarding.entity;

import com.wanted.preonboarding.dto.BoardForm;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Setter(AccessLevel.PRIVATE)
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="board_id")
    private Long id;
    private String title;
    private String content;

    @Column(updatable = false)
    private String creator;
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime create_date;
    @LastModifiedBy
    private String modifier;
    @LastModifiedDate
    private LocalDateTime modified_date;

    public Board(String title, String content, String creator) {
        this.title = title;
        this.content = content;
        this.creator = creator;
    }

    public static Board createNewBoard(BoardForm form ){
        String title = form.getTitle();
        String content = form.getContent();
        String creator = form.getAuthor();
        return new Board (title,content,creator);
    }
    public void updateBoard(Board board, BoardForm boardForm){
        board.setModifier(boardForm.getAuthor());
        if(!StringUtils.isBlank(boardForm.getTitle())){
            board.setTitle(boardForm.getTitle());
        }
        if(!StringUtils.isBlank(boardForm.getContent())){
            board.setContent(boardForm.getContent());
        }
    }

}
