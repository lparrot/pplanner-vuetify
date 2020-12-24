package fr.lauparr.pplanner.server.projections;

import org.springframework.beans.factory.annotation.Value;

public interface UserProj {

  String getId();

  String getUsername();

  String getEmail();

  String getFirstName();

  String getLastName();

  String getAvatar();

  @Value("#{target.group?.name}")
  String getGroup();

}
