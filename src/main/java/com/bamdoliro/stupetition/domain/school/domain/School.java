package com.bamdoliro.stupetition.domain.school.domain;

import com.bamdoliro.stupetition.domain.user.domain.User;
import com.bamdoliro.stupetition.domain.user.domain.repository.UserRepository;
import com.bamdoliro.stupetition.global.entity.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "tbl_school")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class School extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "school_id")
    private Long id;

    @Column(length = 20, nullable = false, unique = true)
    private String name;

    @Column(length = 20, nullable = true)
    private String abbreviation;

    @OneToMany(mappedBy = "school")
    private List<User> membersOfSchool = new ArrayList<>();

    @Builder
    public School(String name, String abbreviation) {
        this.name = name;
        this.abbreviation = abbreviation;
    }

    public int getNumberOfStudents(UserRepository userRepository) {
        return userRepository.countBySchool(this);
    }
}
