package fr.lauparr.pplanner.server.services;

import fr.lauparr.pplanner.server.entities.ProjectBoard;
import fr.lauparr.pplanner.server.entities.ProjectBoardModule;
import fr.lauparr.pplanner.server.entities.abstracts.ModifiableEntity;
import fr.lauparr.pplanner.server.params.BoardPatchModuleParams;
import fr.lauparr.pplanner.server.params.BoardPatchParams;
import fr.lauparr.pplanner.server.projections.ProjectBoardModuleProj;
import fr.lauparr.pplanner.server.repositories.ProjectBoardModuleRepository;
import fr.lauparr.pplanner.server.repositories.ProjectBoardRepository;
import fr.lauparr.pplanner.server.utils.UtilsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoardService {

  @Autowired
  private ProjectBoardRepository projectBoardRepository;
  @Autowired
  private ProjectBoardModuleRepository projectBoardModuleRepository;

  public ProjectBoard getBoardById(String id) {
    return this.projectBoardRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Impossible de trouver le tableau ayant l'id %s", id)));
  }

  public ProjectBoardModule getBoardModuleById(String id) {
    return this.projectBoardModuleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Impossible de trouver le module ayant l'id %s", id)));
  }

  public ProjectBoard updateBoard(String id, BoardPatchParams params) {
    ProjectBoard board = this.getBoardById(id);
    board.setName(params.getName());
    board.setDescription(params.getDescription());
    board.setVisibility(params.getVisibility());

    return this.projectBoardRepository.save(board);
  }

  public List<ProjectBoardModuleProj> getModules(String id) {
    ProjectBoard board = this.getBoardById(id);
    return UtilsDao.convertListDto(
      board.getModules().stream()
        .sorted(Comparator.comparing(ModifiableEntity::getDateCreation))
        .collect(Collectors.toList()),
      ProjectBoardModuleProj.class);
  }

  public ProjectBoardModule createModule(String id, String type) {
    ProjectBoard board = this.getBoardById(id);
    ProjectBoardModule boardModule = new ProjectBoardModule();
    boardModule.setType(type);
    board.addModule(boardModule);
    return this.projectBoardModuleRepository.save(boardModule);
  }

  public void updateBoardState(String id, BoardPatchModuleParams params) {
    ProjectBoardModule boardModule = this.getBoardModuleById(id);
    boardModule.setState(params.getState());
    this.projectBoardModuleRepository.save(boardModule);
  }
}
