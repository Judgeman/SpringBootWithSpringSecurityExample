package de.judgeman.SpringBootWithSpringSecurityExample.Controllers;

import de.judgeman.SpringBootWithSpringSecurityExample.Services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home(HttpServletRequest request, Model model) {

        model.addAttribute("authenticatedUser", userService.getAuthenticatedUser());

        return "home";
    }

    @GetMapping("/forAll")
    public String forAll(Model model) {

        model.addAttribute("authenticatedUser", userService.getAuthenticatedUser());

        return "forAll";
    }
}
