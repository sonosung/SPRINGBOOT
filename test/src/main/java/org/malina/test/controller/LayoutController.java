package org.malina.test.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/layout")
@Log4j2
public class LayoutController {
    @GetMapping({"/main"})
    public void mainPage(){
        log.info("testing...");
    }

}
