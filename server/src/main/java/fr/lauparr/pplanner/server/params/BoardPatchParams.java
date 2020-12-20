package fr.lauparr.pplanner.server.params;

import fr.lauparr.pplanner.server.enums.BoardVisibility;
import lombok.Data;

@Data
public class BoardPatchParams {

  private String name;
  private String description;
  private BoardVisibility visibility;

}
