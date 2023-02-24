package com.bamdoliro.stupetition.domain.petition.service;

import com.bamdoliro.stupetition.domain.petition.domain.Approver;
import com.bamdoliro.stupetition.domain.petition.domain.Petition;
import com.bamdoliro.stupetition.domain.petition.domain.repository.ApproverRepository;
import com.bamdoliro.stupetition.domain.petition.facade.ApproverFacade;
import com.bamdoliro.stupetition.domain.petition.facade.PetitionFacade;
import com.bamdoliro.stupetition.domain.school.domain.School;
import com.bamdoliro.stupetition.domain.user.domain.User;
import com.bamdoliro.stupetition.domain.user.domain.type.Authority;
import com.bamdoliro.stupetition.domain.user.facade.UserFacade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ApproverServiceTest {

    @InjectMocks
    private ApproverService approverService;

    @Mock private ApproverRepository approverRepository;
    @Mock private UserFacade userFacade;
    @Mock private PetitionFacade petitionFacade;
    @Mock private ApproverFacade approverFacade;

    private final School defaultSchool = School.builder()
            .name("부산소프트웨어마이스터고등학교")
            .emailDomain("bssm.hs.kr")
            .build();

    private final User defaultUser = createUser(Authority.ROLE_STUDENT);

    private final Petition defaultPetition = Petition.builder()
            .school(defaultSchool)
            .user(defaultUser)
            .title("title")
            .content("content")
            .build();

    private User createUser(Authority authority) {
        return User.builder()
                .authority(authority)
                .build();
    }

    private Approver createApprover(User user) {
        return Approver.builder()
                .user(user)
                .petition(defaultPetition)
                .build();
    }

    @DisplayName("[Service] Petition 에 동의")
    @Test
    void givenAgreePetitionRequestDto_whenAgreeingToPetition_thenCreatePetitionJoiner() {
        // given
        User student = createUser(Authority.ROLE_STUDENT);
        Approver agreer = createApprover(student);
        ArgumentCaptor<Approver> captor = ArgumentCaptor.forClass(Approver.class);

        given(userFacade.getCurrentUser()).willReturn(student);
        given(petitionFacade.findPetitionById(1L)).willReturn(defaultPetition);
        willDoNothing().given(approverFacade).checkApprovePetition(student, defaultPetition);
        given(approverRepository.save(any())).willReturn(agreer);

        // when
        approverService.approvePetition(1L);

        // then
        verify(approverFacade, times(1))
                .checkApprovePetition(student, defaultPetition);
        verify(approverRepository, times(1)).save(captor.capture());
        Approver savedApprover = captor.getValue();
        assertEquals(student, savedApprover.getUser());
        assertEquals(defaultPetition, savedApprover.getPetition());
    }
}