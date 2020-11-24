package fr.lauparr.pplanner.server;

import com.github.javafaker.Faker;
import fr.lauparr.pplanner.server.entities.*;
import fr.lauparr.pplanner.server.repositories.*;
import fr.lauparr.pplanner.server.utils.UtilsDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.stream.IntStream;

@Component
public class DatabaseInitializer implements CommandLineRunner {

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
  private PasswordEncoder passwordEncoder;

  @Override
  public void run(String... args) throws Exception {
    if (this.projectRepository.count() < 1) {
      Faker faker = Faker.instance();

      // Groups
      Group groupAdministrator = Group.builder().name("Administrator").role(Role.ROLE_ADMIN).role(Role.ROLE_USER).build();
      Group groupUser = Group.builder().name("User").role(Role.ROLE_USER).build();
      this.groupRepository.saveAll(Arrays.asList(groupAdministrator, groupUser));

      // Users
      User user = User.builder()
        .username("laurent.parrot")
        .password(this.passwordEncoder.encode("123"))
        .firstName("Laurent")
        .lastName("Parrot")
        .email("kestounet@gmail.com")
        .avatar(faker.avatar().image())
        .birthday(LocalDate.of(1983, 9, 5))
        .group(groupAdministrator)
        .build();

      this.userRepository.save(user);
      IntStream.rangeClosed(1, 9).forEach(i -> {
        String lastName = faker.name().lastName();
        String firstName = faker.name().firstName();
        String username = String.format("%s.%s", lastName.toLowerCase(), firstName.toLowerCase());
        LocalDate birthday = UtilsDateTime.convertToLocalDate(faker.date().birthday());

        User generatedUser = User.builder()
          .username(username)
          .password(this.passwordEncoder.encode("123"))
          .firstName(firstName)
          .lastName(lastName)
          .email(faker.internet().emailAddress(username))
          .avatar(faker.avatar().image())
          .birthday(birthday)
          .group(groupUser)
          .build();

        this.userRepository.save(generatedUser);
      });

      // Projects
      Project projectCcs = Project.builder().name("CCS").description("Carte de circulation sécurisée").build();
      Project projectVdd = Project.builder().name("VDD").description("Valise diplomatique défense").build();
      Project projectPrdv = Project.builder().name("PRDV").description("Eureka - module prise de rendez-vous").build();

      this.projectRepository.saveAll(Arrays.asList(projectCcs, projectVdd, projectPrdv));

      // Teams
      Team teamCcs = Team.builder().name("Team CCS").project(projectCcs).build();
      Team teamVdd = Team.builder().name("Team VDD").project(projectVdd).build();
      Team teamPrdv = Team.builder().name("Team PRDV").project(projectPrdv).build();

      this.teamRepository.saveAll(Arrays.asList(teamCcs, teamVdd, teamPrdv));

      // Members

      /*
      IntStream.rangeClosed(1, 50).forEach(i -> {
        Member member = Member.builder()
          .fullName(faker.name().fullName())
          .avatar(faker.avatar().image())
          .build();

        this.memberRepository.save(member);

        Team team = UtilsDao.findRandom(this.teamRepository);
        team.getMembers().add(member);
        this.teamRepository.save(team);
      });
      */

    }
  }

}
