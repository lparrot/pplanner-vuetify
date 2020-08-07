package fr.lauparr.pplanner.server.controllers;

import fr.lauparr.pplanner.server.pojos.AbstractController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "${info.api.prefix}/security")
public class SecurityController extends AbstractController {

}
