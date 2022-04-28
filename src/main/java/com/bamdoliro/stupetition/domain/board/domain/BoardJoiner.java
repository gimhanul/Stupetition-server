package com.bamdoliro.stupetition.domain.board.domain;

import com.bamdoliro.stupetition.domain.user.domain.User;
import com.bamdoliro.stupetition.global.entity.BaseTimeEntity;

import javax.persistence.*;

@MappedSuperclass
public abstract class BoardJoiner extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_joiner_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Column(length = 4000, nullable = false)
    private String comment;

    public BoardJoiner(User user, Board board, String comment) {
        this.user = user;
        this.board = board;
        this.comment = comment;
    }

    public void updateComment(String comment) {
        this.comment = comment;
    }
}
