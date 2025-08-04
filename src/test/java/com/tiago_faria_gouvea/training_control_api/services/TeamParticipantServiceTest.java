package com.tiago_faria_gouvea.training_control_api.services;

import com.tiago_faria_gouvea.training_control_api.domains.Employee;
import com.tiago_faria_gouvea.training_control_api.domains.Team;
import com.tiago_faria_gouvea.training_control_api.domains.TeamParticipant;
import com.tiago_faria_gouvea.training_control_api.dtos.TeamParticipantDTO;
import com.tiago_faria_gouvea.training_control_api.infra.config.exception.ResourceNotFoundException;
import com.tiago_faria_gouvea.training_control_api.repositories.IEmployeeRepository;
import com.tiago_faria_gouvea.training_control_api.repositories.ITeamParticipantRepository;
import com.tiago_faria_gouvea.training_control_api.repositories.ITeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class TeamParticipantServiceTest {

  @Mock
  private ITeamParticipantRepository repository;

  @Mock
  private ITeamRepository teamRepository;

  @Mock
  private IEmployeeRepository employeeRepository;

  @InjectMocks
  private TeamParticipantService teamParticipantService;

  private TeamParticipantDTO validDTO;
  private TeamParticipant teamParticipant;

  @BeforeEach
  void setup() {
    validDTO = new TeamParticipantDTO();
    validDTO.setTeamCode(100);
    validDTO.setParticipantCode(200);

    teamParticipant = TeamParticipant.builder()
        .teamCode(validDTO.getTeamCode())
        .participantCode(validDTO.getParticipantCode())
        .build();
  }

  @Test
  @DisplayName("should save team participant when team and participant exist")
  void saveTeamParticipantCase1() {
    Mockito.when(teamRepository.findByCode(100))
        .thenReturn(Optional.of(new Team()));
    Mockito.when(employeeRepository.findByCode(200))
        .thenReturn(Optional.of(new Employee()));
    teamParticipantService.save(validDTO);
    Mockito.verify(repository).save(Mockito.any(TeamParticipant.class));
  }

  @Test
  @DisplayName(
      "should throw ResourceNotFoundException when team does not exist on save"
  )
  void saveTeamParticipantCase2() {
    Mockito.when(teamRepository.findByCode(100)).thenReturn(Optional.empty());
    assertThrows(
        ResourceNotFoundException.class,
        () -> teamParticipantService.save(validDTO)
    );
    Mockito.verify(repository, Mockito.never()).save(Mockito.any());
  }

  @Test
  @DisplayName(
      "should throw ResourceNotFoundException when participant does not exist on save"
  )
  void saveTeamParticipantCase3() {
    Mockito.when(teamRepository.findByCode(100)).thenReturn(Optional.of(new Team()));
    Mockito.when(employeeRepository.findByCode(200)).thenReturn(Optional.empty());
    assertThrows(ResourceNotFoundException.class, () -> teamParticipantService.save(validDTO));
    Mockito.verify(repository, Mockito.never()).save(Mockito.any());
  }

  @Test
  @DisplayName("should delete team participant when participant exists")
  void deleteTeamParticipantCase1() {
    Mockito.when(repository.findByCode(1)).thenReturn(Optional.of(teamParticipant));
    teamParticipantService.delete(1);
    Mockito.verify(repository).delete(1);
  }

  @Test
  @DisplayName(
      "should throw ResourceNotFoundException when participant does not exist on delete"
  )
  void deleteTeamParticipantCase2() {
    Mockito.when(repository.findByCode(1)).thenReturn(Optional.empty());
    assertThrows(
        ResourceNotFoundException.class,
        () -> teamParticipantService.delete(1)
    );
    Mockito.verify(repository, Mockito.never()).delete(Mockito.anyInt());
  }

  @Test
  @DisplayName("should return list of participants by team")
  void findParticipantsByTeamTeamParticipantCase1() {
    List<Employee> expected = List.of(new Employee());
    Mockito.when(repository.findParticipantsByTeam(100)).thenReturn(expected);
    List<Employee> result = teamParticipantService.findParticipantsByTeam(100);
    assertEquals(expected, result);
  }
}