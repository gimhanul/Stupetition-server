package com.bamdoliro.stupetition.domain.board.domain;

import com.bamdoliro.stupetition.domain.board.domain.type.Status;
import com.bamdoliro.stupetition.domain.school.domain.School;
import com.bamdoliro.stupetition.domain.user.domain.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "board_table")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id")
    private School school;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "board")
    private List<BoardJoiner> agreer = new ArrayList<>();

    @Column(length = 20, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", length = 4000, nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(length = 9, nullable = false)
    private Status status;

    @Column
    private int numberOfAgreers;

    @Builder
    public Board(School school, User user, String title, String content) {
        this.school = school;
        this.user = user;
        this.title = title;
        this.content = content;
        this.status = Status.PETITION;
        this.numberOfAgreers = 0;
    }

    public void updateBoard(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
