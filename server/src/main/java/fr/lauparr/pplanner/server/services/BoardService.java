package fr.lauparr.pplanner.server.services;

import fr.lauparr.pplanner.server.entities.ProjectBoard;
import fr.lauparr.pplanner.server.entities.ProjectBoardModule;
import fr.lauparr.pplanner.server.entities.abstracts.ModifiableEntity;
import fr.lauparr.pplanner.server.params.BoardPatchModuleParams;
import fr.lauparr.pplanner.server.params.BoardPatchParams;
import fr.lauparr.pplanner.server.params.BoardPostModuleParams;
import fr.lauparr.pplanner.server.repositories.ProjectBoardModuleRepository;
import fr.lauparr.pplanner.server.repositories.ProjectBoardRepository;
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
    return this.projectBoardRepository.findById(id, ModifiableEntity.whereActives()).orElseThrow(() -> new EntityNotFoundException(String.format("Impossible de trouver le tableau ayant l'id %s", id)));
  }

  public ProjectBoardModule getBoardModuleById(String id) {
    return this.projectBoardModuleRepository.findById(id, ModifiableEntity.whereActives()).orElseThrow(() -> new EntityNotFoundException(String.format("Impossible de trouver le module ayant l'id %s", id)));
  }

  public ProjectBoard updateBoard(String id, BoardPatchParams params) {
    ProjectBoard board = this.getBoardById(id);
    board.setName(params.getName());
    board.setDescription(params.getDescription());
    board.setVisibility(params.getVisibility());

    return this.projectBoardRepository.save(board);
  }

  public List<ProjectBoardModule> getModules(String id) {
    ProjectBoard board = this.getBoardById(id);
    return
      board.getModules().stream()
        .filter(ProjectBoardModule::isActive)
        .sorted(Comparator.comparing(ModifiableEntity::getDateCreation))
        .collect(Collectors.toList());
  }

  public ProjectBoardModule createModule(String id, BoardPostModuleParams params) {
    ProjectBoard board = this.getBoardById(id);
    ProjectBoardModule boardModule = new ProjectBoardModule();
    boardModule.setType(params.getType());
    boardModule.setName(params.getName());
    board.addModule(boardModule);
    return this.projectBoardModuleRepository.save(boardModule);
  }

  public void updateModuleState(String id, BoardPatchModuleParams params) {
    ProjectBoardModule boardModule = this.getBoardModuleById(id);
    boardModule.setState(params.getState());
    this.projectBoardModuleRepository.save(boardModule);
  }

  public void deleteBoard(String id) {
    ProjectBoard board = this.getBoardById(id);
    board.remove();
    board.getModules().forEach(ModifiableEntity::remove);
    this.projectBoardRepository.save(board);
  }

  public void deleteModuleById(String id) {
    ProjectBoardModule module = this.getBoardModuleById(id);
    module.remove();
    this.projectBoardModuleRepository.save(module);
  }
}
