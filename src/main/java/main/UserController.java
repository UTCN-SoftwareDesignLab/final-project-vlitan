package main;

import com.google.api.client.http.HttpRequest;
import main.model.Sequence;
import main.model.SequenceStatusDto;
import main.model.User;
import main.service.SequenceManagerService;
import main.service.SequenceService;
import main.service.UserService;
import main.util.Notification;
import org.codehaus.groovy.runtime.dgmimpl.arrays.IntegerArrayGetAtMetaMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
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
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {
    @Autowired
    private SequenceService sequenceService;
    @Autowired
    private UserService userService;
    @Autowired
    private SequenceManagerService sequenceManagerService;

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
        model.addAttribute("message", request.getUserPrincipal().getName());
        return "user";
    }

    @RequestMapping(value = "/sequences", method = RequestMethod.POST, params = "action=start")
    public String startSequence(@RequestParam("id") Integer id, Model model, HttpServletRequest request)
    {
        Optional<Sequence> sequenceOpt = sequenceService.findById(id);
        if (sequenceOpt.isPresent()){
            Sequence sequence = sequenceOpt.get();
            if(sequence.getUser().getEmail().equals(request.getUserPrincipal().getName())){
                sequenceManagerService.start(sequence);
            }
            else{
                System.out.println("[UserController] ERROR - the sequence does not belong to the logged user");
            }
        }
        return listSequences(model, request);
    }

    @RequestMapping(value = "/sequences", method = RequestMethod.POST, params = "action=resume")
    public String resumeSequence(@RequestParam("id") Integer id, Model model, HttpServletRequest request)
    {
        Optional<Sequence> sequenceOpt = Optional.empty();
        try{
            sequenceOpt = sequenceService.findById(id);
        }
        catch (Exception e){

        }
        if (!sequenceOpt.isPresent()){
            sequenceOpt = sequenceManagerService.getActiveSequenceByUserId(userService.findByEmail(request.getUserPrincipal().getName()).get().getId());
        }
        if (sequenceOpt.isPresent()){
            Sequence sequence = sequenceOpt.get();
            if(sequence.getUser().getEmail().equals(request.getUserPrincipal().getName())){
                sequenceManagerService.resume(sequence);
            }
            else{
                System.out.println("[UserController] ERROR - the sequence does not belong to the logged user");
            }
        }
        return listSequences(model, request);
    }

    @RequestMapping(value = "/sequences", method = RequestMethod.POST, params = "action=pause")
    public String pauseSequence(@RequestParam("id") Integer id, Model model, HttpServletRequest request)
    {
        Optional<Sequence> sequenceOpt = Optional.empty();
        try{
            sequenceOpt = sequenceService.findById(id);
        }
        catch (Exception e){

        }
        if (!sequenceOpt.isPresent()){
            sequenceOpt = sequenceManagerService.getActiveSequenceByUserId(userService.findByEmail(request.getUserPrincipal().getName()).get().getId());
        }
        if (sequenceOpt.isPresent()) {
            Sequence sequence = sequenceOpt.get();
            if (sequence.getUser().getEmail().equals(request.getUserPrincipal().getName())) {
                sequenceManagerService.pause(sequence);
            } else {
                System.out.println("[UserController] ERROR - the sequence does not belong to the logged user");
            }
        }
        return listSequences(model, request);
    }

    @MessageMapping("/userEndpoint")
    @SendTo("/topic/update")
    public SequenceStatusDto gotNotified(SimpMessageHeaderAccessor headerAccessor) {
//        System.out.println("got called");
        String username = headerAccessor.getUser().getName();
        Integer userId = userService.findByEmail(username).get().getId();
        Optional<Sequence> sequenceOptional = sequenceManagerService.getActiveSequenceByUserId(userId);
        SequenceStatusDto sequenceStatusDto = SequenceStatusDto.builder().name("empty").currentInterval("empty").timeLeft(1).build();
        if (sequenceOptional.isPresent()){
            sequenceStatusDto.setUsername(username);
            sequenceStatusDto.setTimeLeft(sequenceOptional.get().getIntervals().get(sequenceOptional.get().getCurrentIntervalIndex()).getTimeUntilFinished());
            sequenceStatusDto.setName(sequenceOptional.get().getName());
            sequenceStatusDto.setCurrentInterval(sequenceOptional.get().getIntervals().get(sequenceOptional.get().getCurrentIntervalIndex()).getName());
        }
        return sequenceStatusDto;
    }
}
