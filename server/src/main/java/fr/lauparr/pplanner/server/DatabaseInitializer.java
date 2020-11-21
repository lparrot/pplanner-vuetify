package fr.lauparr.pplanner.server;

import com.github.javafaker.Faker;
import fr.lauparr.pplanner.server.entities.Group;
import fr.lauparr.pplanner.server.entities.Member;
import fr.lauparr.pplanner.server.entities.Project;
import fr.lauparr.pplanner.server.entities.Role;
import fr.lauparr.pplanner.server.repositories.GroupRepository;
import fr.lauparr.pplanner.server.repositories.MemberRepository;
import fr.lauparr.pplanner.server.repositories.ProjectRepository;
import fr.lauparr.pplanner.server.utils.DateTimeUtils;
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
  private MemberRepository memberRepository;
  @Autowired
  private GroupRepository groupRepository;
  @Autowired
  private ProjectRepository projectRepository;
  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public void run(String... args) throws Exception {
    if (projectRepository.count() < 1) {
      Faker faker = Faker.instance();

      // Groups
      Group administrator = Group.builder().name("Administrator").role(Role.ROLE_ADMIN).role(Role.ROLE_USER).build();
      Group user = Group.builder().name("User").role(Role.ROLE_USER).build();
      groupRepository.saveAll(Arrays.asList(administrator, user));

      // Members
      Member member = Member.builder()
        .username("laurent.parrot")
        .password(passwordEncoder.encode("123"))
        .firstName("Laurent")
        .lastName("Parrot")
        .email("kestounet@gmail.com")
        .avatar(faker.avatar().image())
        .birthday(LocalDate.of(1983, 9, 5))
        .group(administrator)
        .build();

      memberRepository.save(member);
      IntStream.rangeClosed(1, 9).forEach(i -> {
        String lastName = faker.name().lastName();
        String firstName = faker.name().firstName();
        String username = String.format("%s.%s", lastName.toLowerCase(), firstName.toLowerCase());
        LocalDate birthday = DateTimeUtils.convertToLocalDate(faker.date().birthday());

        Member generatedMember = Member.builder()
          .username(username)
          .password(passwordEncoder.encode("123"))
          .firstName(firstName)
          .lastName(lastName)
          .email(faker.internet().emailAddress(username))
          .avatar(faker.avatar().image())
          .birthday(birthday)
          .group(user)
          .build();

        memberRepository.save(generatedMember);
      });

      // Projects
      projectRepository.saveAll(
        Arrays.asList(
          Project.builder().name("CCS").description("Carte de circulation sécurisée").build(),
          Project.builder().name("VDD").description("Valise diplomatique défense").build(),
          Project.builder().name("PRDV").description("Eureka - module prise de rendez-vous").build()
        )
      );
    }
  }

}
