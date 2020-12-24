package fr.lauparr.pplanner.server.projections;

import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public interface TeamProj {

  String getId();

  String getName();

  @Value("#{target.project?.id}")
  String getProjectId();

  List<MemberProj> getMembers();

}
