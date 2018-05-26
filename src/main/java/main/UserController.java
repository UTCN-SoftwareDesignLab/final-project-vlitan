package main;

import com.google.api.client.http.HttpRequest;
import main.model.Sequence;
import main.model.User;
import main.service.SequenceService;
import main.service.UserService;
import main.util.Notification;
import org.codehaus.groovy.runtime.dgmimpl.arrays.IntegerArrayGetAtMetaMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
public class UserController {
    @Autowired
    private SequenceService sequenceService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @Order(value = 1)
    public String index(Model model, HttpServletRequest request) {
        //model.addAttribute("message", request.getUserPrincipal().getName());
        model.addAttribute("sequence", new Sequence());
        listSequences(model, request);
        return "user";
    }

    @RequestMapping(value = "/sequences", method = RequestMethod.POST, params = "action=create")
    public String saveSequence(@Validated @ModelAttribute("sequence") Sequence sequence, Model model,  HttpServletRequest request) {
        sequence.setUser(userService.findByEmail(request.getUserPrincipal().getName()).get());
        sequenceService.save(sequence);
        return "redirect:/sequence/" + sequence.getId().toString();
    }

    @RequestMapping(value = "/sequences", method = RequestMethod.POST, params = "action=list")
    public String listSequences(Model model, HttpServletRequest request)
    {
        model.addAttribute("sequenceList", sequenceService.findByUserEmail(request.getUserPrincipal().getName()));
        model.addAttribute("sequence", new Sequence());
        return "user";
    }
}
