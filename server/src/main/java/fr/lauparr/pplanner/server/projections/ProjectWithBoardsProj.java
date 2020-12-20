package fr.lauparr.pplanner.server.projections;

import java.util.List;

public interface ProjectWithBoardsProj {
  String getId();

  String getName();

  List<ProjectBoardProj> getBoards();
}
