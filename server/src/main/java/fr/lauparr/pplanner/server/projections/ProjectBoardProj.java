package fr.lauparr.pplanner.server.projections;

import fr.lauparr.pplanner.server.enums.BoardVisibility;

public interface ProjectBoardProj {

  String getId();

  String getName();

  String getDescription();

  BoardVisibility getVisibility();

}
