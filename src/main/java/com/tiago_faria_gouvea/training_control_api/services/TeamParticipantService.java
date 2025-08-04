package com.tiago_faria_gouvea.training_control_api.services;

import com.tiago_faria_gouvea.training_control_api.domains.Employee;
import com.tiago_faria_gouvea.training_control_api.domains.TeamParticipant;
import com.tiago_faria_gouvea.training_control_api.dtos.TeamParticipantDTO;
import com.tiago_faria_gouvea.training_control_api.config.exception.ResourceNotFoundException;
import com.tiago_faria_gouvea.training_control_api.repositories.IEmployeeRepository;
import com.tiago_faria_gouvea.training_control_api.repositories.ITeamParticipantRepository;
import com.tiago_faria_gouvea.training_control_api.repositories.ITeamRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamParticipantService {
  private final ITeamParticipantRepository repository;
  private final ITeamRepository teamRepository;
  private final IEmployeeRepository employeeRepository;

  public TeamParticipantService(
      ITeamParticipantRepository repository,
      ITeamRepository teamRepository,
      IEmployeeRepository employeeRepository
  ) {
    this.repository = repository;
    this.teamRepository = teamRepository;
    this.employeeRepository = employeeRepository;
  }

  public void save(TeamParticipantDTO teamParticipantDTO) {
    this.teamRepository.findByCode(teamParticipantDTO.getTeamCode())
        .orElseThrow(() -> new ResourceNotFoundException(
            "Turma não encontrado"
        ));

    this.employeeRepository.findByCode(teamParticipantDTO.getParticipantCode())
        .orElseThrow(() -> new ResourceNotFoundException(
            "Participante não encontrado"
        ));

    TeamParticipant teamParticipant = mappingToDomain(teamParticipantDTO);

    this.repository.save(teamParticipant);
  }

  public void delete(Integer teamParticipantCode) {
    this.repository.findByCode(teamParticipantCode)
        .orElseThrow(() -> new ResourceNotFoundException(
            "Participante não encontrado"
        ));
    this.repository.delete(teamParticipantCode);
  }

  public List<Employee> findParticipantsByTeam(Integer teamCode) {
    this.teamRepository.findByCode(teamCode)
        .orElseThrow(() -> new ResourceNotFoundException(
            "Turma não encontrado"
        ));
    return this.repository.findParticipantsByTeam(teamCode);
  }

  public TeamParticipant mappingToDomain(
      TeamParticipantDTO teamParticipantDTO
  ) {
    return TeamParticipant.builder()
        .teamCode(teamParticipantDTO.getTeamCode())
        .participantCode(teamParticipantDTO.getParticipantCode())
        .build();
  }
}
