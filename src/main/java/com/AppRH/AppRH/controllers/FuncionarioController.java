package com.AppRH.AppRH.controllers;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.AppRH.AppRH.repository.FuncionarioRepository;
import com.AppRH.AppRH.models.Funcionario;
import com.AppRH.AppRH.repository.DependentesRepository;

@Controller
public class FuncionarioController {
    @Autowired
    private FuncionarioRepository fr;

    @Autowired
    private DependentesRepository dr;

    @RequestMapping(value = "/cadastrarFuncionario", method = RequestMethod.GET)
    public String form() {
        return "funcionario/formFuncionario";
    }

    @RequestMapping(value = "/cadastrarFuncionario", method = RequestMethod.POST)
    public String form(@Valid Funcionario funcionario, BindingResult result, RedirectAttributes attributes) {

        if (result.hasErrors()) {
            attributes.addFlashAttribute("mensagem", "Verifique os campos...");
            return "redirect:/cadastrarFuncionario";
        }

        fr.save(funcionario);
        attributes.addFlashAttribute("mensagem", "Funcionario cadastrado com sucesso!");
        return "redirect:/cadastrarFuncionario";
    }
}
