package fr.lauparr.pplanner.server.projections;

import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public interface TeamSimpleProjection {

  Long getId();

  String getName();

  @Value("#{target.project?.id}")
  Long getProjectId();

  List<MemberSimpleProjection> getMembers();

}
