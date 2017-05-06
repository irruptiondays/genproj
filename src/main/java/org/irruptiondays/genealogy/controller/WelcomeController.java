package org.irruptiondays.genealogy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by TValentine on 5/6/17.
 */
@Controller
//@RequestMapping(value = { "/welcome/", "/welcome" })
public class WelcomeController {

    @RequestMapping("/")
    public String test() {
        return "welcome";
    }

}
