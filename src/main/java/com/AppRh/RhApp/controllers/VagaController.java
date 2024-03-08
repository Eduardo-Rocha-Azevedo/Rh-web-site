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
    @Autowired
    private VagaRepository vRepository;

    @Autowired
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

    //? LIST VACANCIES
    @RequestMapping("/vagas")
    public ModelAndView vacancyList(){
        ModelAndView mv = new ModelAndView("vaga/listaVaga");
        Iterable<Vaga> vacancy = vRepository.findAll();
        mv.addObject("vagas", vacancy);
        return mv;
    }

    // 
    @RequestMapping(value = "/{codigo}", method=RequestMethod.GET)
    public ModelAndView detailVacancy(@PathVariable("codigo") long codigo){
        
        Vaga vacancy = vRepository.findByCodigo(codigo);
        ModelAndView mv = new ModelAndView("vaga/detalhesVaga");
        mv.addObject("vaga", vacancy);

        Iterable<Candidate> candidates = cRepository.findByVaga(vacancy);
        mv.addObject("candidatos", candidates);

        return mv;
    }

    //? Delete the vacancy
    @RequestMapping("/deletarVaga")  
    public String deleteVacancy(long codigo){
        Vaga vacancy = vRepository.findByCodigo(codigo);
        vRepository.delete(vacancy);
        return "redirect:/vagas";
    }

    //? Details the vacancy
    public String detailsVacancyPost(@PathVariable("codigo") long codigo, @Valid Candidate candidate, 
            BindingResult result, RedirectAttributes attributes){
        
        if(result.hasErrors()){
            attributes.addFlashAttribute("mensagem", "Verifique os campos!");
            return "redirect:/{codigo}";
        }

        // duplicate rg
        if(cRepository.findByRg(candidate.getRg()) != null){
            attributes.addFlashAttribute("mensagem erro", "RG duplicado!");
            return "redirect:/{codigo}";
        }

        Vaga vacancy = vRepository.findByCodigo(codigo);
        candidate.setVaga(vacancy);
        cRepository.save(candidate);
        attributes.addFlashAttribute("mensagem", "Candidato adicionado com sucesso!");
        return "redirect:/{codigo}";
    }

    //? Delete the candidate by RG
    @RequestMapping("/deletarCandidato") 
    public String deleteCandidate(String rg){
        Candidate candidate = cRepository.findByRg(rg);
        Vaga vacancy = candidate.getVaga();
        String codigo = "" + vacancy.getCodigo();
        cRepository.delete(candidate);
        return "redirect:/" + codigo;
    }

    //? Update metheod
    // form de edicao da vaga
    @RequestMapping(value = "/editar-vaga", method = RequestMethod.GET)
    public ModelAndView editVacancy(Long codigo){
        Vaga vacancy = vRepository.findByCodigo(codigo);
        ModelAndView mv = new ModelAndView("vaga/update-vaga");
        mv.addObject("vaga", vacancy);
        return mv;
    }

    // update the vanacy
    @RequestMapping(value = "/editar-vaga", method = RequestMethod.POST)
    public String updateVacancy(@Valid Vaga vacancy, BindingResult result, RedirectAttributes attributes){
        vRepository.save(vacancy);
        attributes.addFlashAttribute("sucess", "Vaga editada com sucesso!");

        long codeLong = vacancy.getCodigo();
        String code = ""+ codeLong;

        return "redirect:/" + code;
    }
}