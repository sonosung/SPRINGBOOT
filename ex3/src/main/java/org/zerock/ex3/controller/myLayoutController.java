package org.zerock.ex3.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/myLayout")
@Log4j2
public class myLayoutController {
    @GetMapping({"/wooxtravel"})
    public void myWooxtravel(){

        log.info("wooxtravel...............");
    }
}
