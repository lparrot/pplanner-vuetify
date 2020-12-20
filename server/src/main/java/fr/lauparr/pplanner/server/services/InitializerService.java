package fr.lauparr.pplanner.server.services;

import com.github.javafaker.Faker;
import fr.lauparr.pplanner.server.entities.*;
import fr.lauparr.pplanner.server.repositories.*;
import fr.lauparr.pplanner.server.utils.UtilsDao;
import fr.lauparr.pplanner.server.utils.UtilsDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class InitializerService {

  public final static String FAKE_AVATAR_URL = "https://i.pravatar.cc/150?u=";

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private GroupRepository groupRepository;
  @Autowired
  private ProjectRepository projectRepository;
  @Autowired
  private TeamRepository teamRepository;
  @Autowired
  private MemberRepository memberRepository;

  @Autowired
  private TeamService teamService;
  @Autowired
  private PasswordEncoder passwordEncoder;

  @Transactional
  public void initialize() {
    if (this.projectRepository.count() < 1) {
      Faker faker = Faker.instance();

      // Groups
      Group groupAdministrator = Group.builder().name("Administrator").role(Role.ROLE_ADMIN).role(Role.ROLE_USER).build();
      Group groupUser = Group.builder().name("User").role(Role.ROLE_USER).build();
      this.groupRepository.saveAll(Arrays.asList(groupAdministrator, groupUser));

      // Users
      User user = User.builder()
        .email("kestounet@gmail.com")
        .password(this.passwordEncoder.encode("123"))
        .firstName("Laurent")
        .lastName("Parrot")
        .avatar(InitializerService.FAKE_AVATAR_URL + "kestounet@gmail.com")
        .birthday(LocalDate.of(1983, 9, 5))
        .group(groupAdministrator)
        .build();

      this.userRepository.save(user);
      IntStream.rangeClosed(1, 9).forEach(i -> {
        String lastName = faker.name().lastName();
        String firstName = faker.name().firstName();
        String username = String.format("%s.%s", lastName.toLowerCase(), firstName.toLowerCase());
        String email = faker.internet().emailAddress(username);
        LocalDate birthday = UtilsDateTime.convertToLocalDate(faker.date().birthday());

        User generatedUser = User.builder()
          .email(email)
          .password(this.passwordEncoder.encode("123"))
          .firstName(firstName)
          .lastName(lastName)
          .avatar(InitializerService.FAKE_AVATAR_URL + email)
          .birthday(birthday)
          .group(groupUser)
          .build();

        this.userRepository.save(generatedUser);
      });

      // Projects
      Project projectPplanner = Project.builder().name("PPlanner").description("Project Planner").build();
      Project projectCcs = Project.builder().name("CCS").description("Carte de circulation sécurisée").build();
      Project projectVdd = Project.builder().name("VDD").description("Valise diplomatique défense").build();
      Project projectPrdv = Project.builder().name("PRDV").description("Eureka - module prise de rendez-vous").build();

      this.projectRepository.saveAll(Arrays.asList(projectPplanner, projectCcs, projectVdd, projectPrdv));

      // Teams
      List<Team> teams = Arrays.asList(
        Team.builder().name("Team PPlanner").project(projectPplanner).build(),
        Team.builder().name("Team CCS").project(projectCcs).build(),
        Team.builder().name("Team VDD").project(projectVdd).build(),
        Team.builder().name("Team PRDV").project(projectPrdv).build()
      );

      // Ajout de l'utilisateur par défaut dans l'équipe de chaque projet
      Member member = new Member(user);

      member.setJob(faker.job().title());
      this.memberRepository.save(member);

      teams.forEach(team -> {
        this.teamService.addMember(member, team);
      });

      this.teamRepository.saveAll(teams);

      // Members

      IntStream.rangeClosed(1, 50).forEach(i -> {
        String fullName = faker.name().fullName();
        Member generatedMember = Member.builder()
          .fullname(fullName)
          .job(faker.job().title())
          .avatar(InitializerService.FAKE_AVATAR_URL + fullName)
          .build();

        generatedMember = this.memberRepository.save(generatedMember);

        this.teamService.addMember(generatedMember, UtilsDao.findRandom(this.teamRepository));
      });

    }
  }

}
