package fr.lauparr.pplanner.server.projections;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;

public interface MemberProj {

  String getFirstName();

  String getLastName();

  String getEmail();

  String getJob();

  String getAvatar();

  @Value("#{target.firstName + ' ' + target.lastName}")
  String getFullname();

  @JsonFormat(pattern = "dd MMMM yyyy")
  LocalDate getBirthday();

  @Value("#{target.user?.id}")
  String getUserId();

}
