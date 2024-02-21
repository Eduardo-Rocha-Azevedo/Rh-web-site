package com.AppRh.RhApp.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.AppRh.RhApp.models.Vaga;
import com.AppRh.RhApp.repository.CandidateRepository;
import com.AppRh.RhApp.repository.VagaRepository;
import com.AppRh.RhApp.models.Candidate;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class VagaController {
    
    private VagaRepository vRepository;
    private CandidateRepository cRepository;

    //? REGISTER VACANCY
    @RequestMapping(value = "/cadastrarVaga", method=RequestMethod.GET)
    public String form(){
        return "vaga/formVaga";
    }

    @RequestMapping(value = "/cadastrarVaga", method=RequestMethod.POST)
    public String form(@Valid Vaga vaga, BindingResult result, RedirectAttributes attributes){
        if(result.hasErrors()){
            attributes.addFlashAttribute("mensagem", "Verifique os campos!");
            return "redirect:/cadastrarVaga";
        }
        vRepository.save(vaga);
        attributes.addFlashAttribute("mensagem", "Vaga cadastrada com sucesso!");
        return "redirect:/cadastrarVaga";  
    }
}
