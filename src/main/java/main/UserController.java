package main;

import main.model.User;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @Order(value = 1)
    public String index(Model model) {
        return "admin";
    }
}
