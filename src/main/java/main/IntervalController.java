package main;

import main.model.Interval;
import main.model.Sequence;
import main.service.IntervalService;
import main.service.SequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class IntervalController {
    @Autowired
    private IntervalService intervalService;

    @Autowired
    private SequenceService sequenceService;

    @RequestMapping(value = "/sequence/{sequenceId}", method = RequestMethod.GET)
    @Order(value = 1)
    public String index(@PathVariable(value="sequenceId") String id, Model model, HttpSession session) {
        //model.addAttribute("message", request.getUserPrincipal().getName());
        model.addAttribute("interval", new Interval());
        session.setAttribute("sequenceId", id);
        listIntervals(model, session);
        return "sequence";
    }

    @RequestMapping(value = "/intervals", method = RequestMethod.POST, params = "action=create")
    public String createInterval(@Validated @ModelAttribute("interval") Interval interval, BindingResult bindingResult, Model model, HttpSession session)
    {
        if (!bindingResult.hasErrors()) {
         //   model.addAttribute("intervalList", intervalService.findBySequenceId(Integer.parseInt(session.getAttribute("sequenceId").toString())));
            Optional<Sequence> sequenceOptional = sequenceService.findById(Integer.parseInt(session.getAttribute("sequenceId").toString()));
            if (sequenceOptional.isPresent()) {
                Sequence sequence = sequenceOptional.get();
                sequence.insertInterval(interval, Optional.empty());
                sequenceService.save(sequence);
//                intervalService.save(interval);
            }
        }
        listIntervals(model, session);
        return "sequence";
    }

//    @RequestMapping(value = "/intervals", method = RequestMethod.POST, params = "action=list")
    public String listIntervals(Model model, HttpSession session)
    {
        model.addAttribute("intervalList", intervalService.findBySequenceId(Integer.parseInt(session.getAttribute("sequenceId").toString())));
        model.addAttribute("interval", new Interval());
        return "sequence";
    }
}
