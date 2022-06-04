package com.bamdoliro.stupetition.domain.school.service;

import com.bamdoliro.stupetition.domain.school.domain.School;
import com.bamdoliro.stupetition.domain.school.domain.repository.SchoolRepository;
import com.bamdoliro.stupetition.domain.school.facade.SchoolFacade;
import com.bamdoliro.stupetition.domain.school.presentation.dto.response.SchoolResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SchoolService {

    private final SchoolRepository schoolRepository;
    private final SchoolFacade schoolFacade;

    @Transactional(readOnly = true)
    public List<SchoolResponseDto> searchSchool(String schoolName) {
        List<School> result = schoolRepository.findByNameContaining(schoolName);
        return result.stream().map(SchoolResponseDto::fromEntity)
                .collect(Collectors.toList());
    }
}
