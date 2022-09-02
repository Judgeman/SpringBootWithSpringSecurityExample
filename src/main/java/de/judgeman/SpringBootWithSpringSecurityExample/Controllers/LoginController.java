package de.judgeman.SpringBootWithSpringSecurityExample.Controllers;

import de.judgeman.SpringBootWithSpringSecurityExample.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login(Model model) {

        model.addAttribute("defaultAdminUsername", userService.getDefaultAdminUsername());
        model.addAttribute("defaultAdminPassword", userService.getDefaultAdminPassword());
        model.addAttribute("defaultUserUsername", userService.getDefaultUserUsername());
        model.addAttribute("defaultUserPassword", userService.getDefaultUserPassword());

        return "login";
    }
}
