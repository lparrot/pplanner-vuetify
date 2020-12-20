package fr.lauparr.pplanner.server.params;

import fr.lauparr.pplanner.server.enums.BoardVisibility;
import lombok.Data;

@Data
public class ProjectPostBoardParams {
  private String name;
  private BoardVisibility visibility;
}
