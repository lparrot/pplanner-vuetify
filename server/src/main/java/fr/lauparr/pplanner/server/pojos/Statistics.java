package fr.lauparr.pplanner.server.pojos;

import lombok.Data;

@Data
public class Statistics {

  private long members;
  private long files;
  private long backlogs;
  /**
   * Jours/Homme
   */
  private long totalDuration;
  private long iterations;

}
