package fr.lauparr.pplanner.server.projections;

import org.springframework.beans.factory.annotation.Value;

public interface MemberProj {

  String getFullname();

  String getJob();

  String getAvatar();

  @Value("#{target.user?.id}")
  String getUserId();

}
