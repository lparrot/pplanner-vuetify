package fr.lauparr.pplanner.server.controllers;

import fr.lauparr.pplanner.server.params.BoardPatchModuleParams;
import fr.lauparr.pplanner.server.params.BoardPatchParams;
import fr.lauparr.pplanner.server.params.BoardPostModuleParams;
import fr.lauparr.pplanner.server.pojos.api.AbstractController;
import fr.lauparr.pplanner.server.projections.ProjectBoardModuleProj;
import fr.lauparr.pplanner.server.projections.ProjectBoardProj;
import fr.lauparr.pplanner.server.services.BoardService;
import fr.lauparr.pplanner.server.utils.UtilsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "${app.api.prefix}/boards")
public class BoardController extends AbstractController {

  @Autowired
  private BoardService boardService;

  @GetMapping("/{id}")
  public ResponseEntity getBoardById(@PathVariable("id") String id) {
    return this.ok(UtilsDao.convertToDto(this.boardService.getBoardById(id), ProjectBoardProj.class));
  }

  @PatchMapping("/{id}")
  public ResponseEntity patchBoardById(@PathVariable("id") String id, @RequestBody BoardPatchParams params) {
    return this.ok(this.boardService.updateBoard(id, params));
  }

  @GetMapping("/{id}/modules")
  public ResponseEntity getBoardModule(@PathVariable("id") String id) {
    return this.ok(UtilsDao.convertListDto(this.boardService.getModules(id), ProjectBoardModuleProj.class));
  }

  @PostMapping("/{id}/modules")
  public ResponseEntity postBoardModule(@PathVariable("id") String id, @RequestBody BoardPostModuleParams params) {
    return this.ok(UtilsDao.convertToDto(this.boardService.createModule(id, params), ProjectBoardModuleProj.class));
  }

  @PatchMapping("/modules/{id}")
  public ResponseEntity patchBoardById(@PathVariable("id") String id, @RequestBody BoardPatchModuleParams params) {
    this.boardService.updateBoardState(id, params);
    return this.ok();
  }

}
