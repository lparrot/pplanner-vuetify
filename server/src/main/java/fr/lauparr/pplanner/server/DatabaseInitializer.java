package fr.lauparr.pplanner.server;

import fr.lauparr.pplanner.server.services.InitializerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer implements CommandLineRunner {

  @Autowired
  private InitializerService initializerService;

  @Override
  public void run(String... args) throws Exception {
    this.initializerService.initialize();
  }

}
