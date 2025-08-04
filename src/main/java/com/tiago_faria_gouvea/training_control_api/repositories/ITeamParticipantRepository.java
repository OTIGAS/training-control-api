package com.tiago_faria_gouvea.training_control_api.repositories;

import com.tiago_faria_gouvea.training_control_api.domains.Employee;
import com.tiago_faria_gouvea.training_control_api.domains.TeamParticipant;

import java.util.List;
import java.util.Optional;


public interface ITeamParticipantRepository {
  Integer save(TeamParticipant teamParticipant);

  Integer delete(Integer teamParticipantCode);

  List<Employee> findParticipantsByTeam(Integer teamCode);

  Optional<TeamParticipant> findByCode(Integer code);
}
