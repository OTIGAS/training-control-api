package com.tiago_faria_gouvea.training_control_api.repositories;

import com.tiago_faria_gouvea.training_control_api.domains.Team;

import java.util.List;
import java.util.Optional;


public interface ITeamRepository {
  Integer save(Team team);

  Integer update(Integer teamCode, Team team);

  Integer delete(Integer teamCode);

  List<Team> findByCourseCode(Integer courseCode);

  Optional<Team> findByCode(Integer code);
}
