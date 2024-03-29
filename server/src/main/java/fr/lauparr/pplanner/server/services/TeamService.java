package fr.lauparr.pplanner.server.services;

import fr.lauparr.pplanner.server.entities.Member;
import fr.lauparr.pplanner.server.entities.Team;
import fr.lauparr.pplanner.server.exceptions.MessageException;
import fr.lauparr.pplanner.server.projections.TeamProj;
import fr.lauparr.pplanner.server.repositories.MemberRepository;
import fr.lauparr.pplanner.server.repositories.TeamRepository;
import fr.lauparr.pplanner.server.utils.UtilsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamService {

  @Autowired
  private MemberRepository memberRepository;
  @Autowired
  private TeamRepository teamRepository;

  public void addMember(Member member, Team team) {
    if (team == null) {
      throw new MessageException("L'équipe ne peut pas être nulle");
    }

    if (member.getUser() != null) {
      member = this.memberRepository.findMemberByUser(member.getUser()).orElse(member);
    }

    team.addMember(member);
    this.teamRepository.save(team);
  }

  public TeamProj findTeamByProjectId(String id) {
    return UtilsDao.convertToDto(this.teamRepository.findByProjectId(id), TeamProj.class);
  }
}
