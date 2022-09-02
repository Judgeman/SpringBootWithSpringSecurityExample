package de.judgeman.SpringBootWithSpringSecurityExample.Controllers;

import de.judgeman.SpringBootWithSpringSecurityExample.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/admin")
    public String admin(Model model) {

        model.addAttribute("authenticatedUser", userService.getAuthenticatedUser());

        return "admin";
    }
}
