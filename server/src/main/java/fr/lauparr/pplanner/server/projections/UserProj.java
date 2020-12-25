package fr.lauparr.pplanner.server.projections;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;

public interface UserProj {

  String getId();

  String getUsername();

  String getEmail();

  String getFirstName();

  String getLastName();

  @JsonFormat(pattern = "dd MMMM yyyy")
  LocalDate getBirthday();

  String getAvatar();

  MemberProj getMember();

  @Value("#{target.firstName + ' ' + target.lastName}")
  String getFullname();

  @Value("#{target.group?.name}")
  String getGroup();

}
