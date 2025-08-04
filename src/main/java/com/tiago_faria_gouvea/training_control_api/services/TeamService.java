package com.tiago_faria_gouvea.training_control_api.services;

import com.tiago_faria_gouvea.training_control_api.domains.Team;
import com.tiago_faria_gouvea.training_control_api.dtos.TeamDTO;
import com.tiago_faria_gouvea.training_control_api.config.exception.CourseChangeNotAllowedException;
import com.tiago_faria_gouvea.training_control_api.config.exception.InvalidDateRangeException;
import com.tiago_faria_gouvea.training_control_api.config.exception.ResourceNotFoundException;
import com.tiago_faria_gouvea.training_control_api.repositories.ICourseRepository;
import com.tiago_faria_gouvea.training_control_api.repositories.ITeamRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {
  private final ITeamRepository repository;
  private final ICourseRepository courseRepository;

  public TeamService(
      ITeamRepository repository,
      ICourseRepository courseRepository
  ) {
    this.repository = repository;
    this.courseRepository = courseRepository;
  }

  public void save(TeamDTO teamDTO) {
    if (isStartBeforeEnd(teamDTO)) {
      throw new InvalidDateRangeException(
          "A data de início deve ser anterior ou igual à data de término."
      );
    }
    this.courseRepository.findByCode(teamDTO.getCourseCode())
        .orElseThrow(() -> new ResourceNotFoundException(
            "Curso não encontrado"
        ));
    Team team = mappingToDomain(teamDTO);
    this.repository.save(team);
  }

  public void update(Integer teamCode, TeamDTO teamDTO) {
    if (isStartBeforeEnd(teamDTO)) {
      throw new InvalidDateRangeException(
          "A data de início deve ser anterior ou igual à data de término."
      );
    }

    Team teamFound = this.repository.findByCode(teamCode)
        .orElseThrow(() -> new ResourceNotFoundException(
            "Turma não encontrado"
        ));

    if (!teamFound.getCourseCode().equals(teamDTO.getCourseCode())) {
      throw new CourseChangeNotAllowedException(
          "Não se pode mudar o curso de uma turma depois que já foi criada."
      );
    }

    Team team = mappingToDomain(teamDTO);

    this.repository.update(teamCode, team);
  }

  public void delete(Integer teamCode) {
    this.repository.findByCode(teamCode)
        .orElseThrow(() -> new ResourceNotFoundException(
            "Turma não encontrada"
        ));

    this.repository.delete(teamCode);
  }

  public List<Team> findByCourseCode(Integer courseCode) {
    return this.repository.findByCourseCode(courseCode);
  }

  public boolean isStartBeforeEnd(TeamDTO teamDTO) {
    return teamDTO.getStartDate().isAfter(teamDTO.getEndDate());
  }

  public Team mappingToDomain(TeamDTO teamDTO) {
    return Team.builder()
        .startDate(teamDTO.getStartDate())
        .endDate(teamDTO.getEndDate())
        .location(teamDTO.getLocation())
        .courseCode(teamDTO.getCourseCode())
        .build();
  }
}

