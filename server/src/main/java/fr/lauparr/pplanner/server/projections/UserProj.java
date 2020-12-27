package fr.lauparr.pplanner.server.projections;

import org.springframework.beans.factory.annotation.Value;

public interface UserProj {

  String getId();

  String getUsername();

  String getEmail();

  MemberProj getMember();

  @Value("#{target.group?.name}")
  String getGroup();

}
