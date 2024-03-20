package com.AppRH.AppRH.controllers;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.AppRH.AppRH.repository.FuncionarioRepository;
import com.AppRH.AppRH.models.Dependentes;
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

    // Cadastrar um funcionário
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

    // Listar todos os funcionários
    @RequestMapping("/funcionarios")    
    public ModelAndView listFuncionario(){
        ModelAndView mv = new ModelAndView("funcionario/listaFuncionario");
        Iterable<Funcionario> funcionarios = fr.findAll();
        mv.addObject("funcionarios", funcionarios);
        return mv;
    }

    // Listar dependentes
    @RequestMapping(value = "/dependentes/{id}", method = RequestMethod.GET)
    public ModelAndView dependentes(@PathVariable("id")long id){
        Funcionario funcionario = fr.findById(id);
        ModelAndView mv = new ModelAndView("funcionario/dependentes");
        mv.addObject("funcionario", funcionario);

        // Lista de dependentes baseado no funcionário
        Iterable<Dependentes> dependentes = dr.findByFuncionario(funcionario);
        mv.addObject("dependentes", dependentes);
        return mv;
    }

    // Adicionar dependentes
    @RequestMapping(value = "/dependentes/{id}", method = RequestMethod.POST)
    public String addDependentes(@PathVariable("id")long id, @Valid Dependentes dependentes, BindingResult result, RedirectAttributes attributes){
        if(result.hasErrors()){
            attributes.addFlashAttribute("mensagem_erro", "Verifique os campos...");
            return "redirect:/dependentes/{id}";
        }
        
        if(dr.findByCpf(dependentes.getCpf()) != null){
            attributes.addFlashAttribute("mensagem_erro", "CPF já cadastrado!");
            return "redirect:/dependentes/{id}";
        }

        Funcionario funcionario = fr.findById(id);
        dependentes.setFuncionario(funcionario);
        dr.save(dependentes);
        attributes.addFlashAttribute("mensagem", "Dependente cadastrado com sucesso!");
        return "redirect:/dependentes/{id}";
    }

    // Deletar funcionario
    @RequestMapping(value = "/deletarFuncionario")
    public String deletarFuncionario(long id){

        Funcionario funcionario = fr.findById(id);
        fr.delete(funcionario);
        return "redirect:/funcionarios";
    }

    // Editar funcionario
    // form
    @RequestMapping(value = "/update-funcionario", method = RequestMethod.GET)
    public ModelAndView editarFuncionario(long id){

        Funcionario funcionario = fr.findById(id);
        ModelAndView mv = new ModelAndView("funcionario/update-funcionario");
        mv.addObject("funcionario", funcionario);
        return mv;
    }

    // Update funcionario
    @RequestMapping(value = "/editar-funcionario", method = RequestMethod.POST)
    public String editarFuncionarioPost(@Valid Funcionario funcionario, BindingResult result, RedirectAttributes attributes){
        if(result.hasErrors()){
            attributes.addFlashAttribute("mensagem_erro", "Verifique os campos...");
            return "redirect:/editar-funcionario";
        }

        // Redireciona para a mesma página
        fr.save(funcionario);
        attributes.addFlashAttribute("mensagem", "Funcionario editado com sucesso!");
        long idLong = funcionario.getId();
        String id = ""+idLong;
        return "redirect:/dependentes/"+id;
    }

    // Deletar dependente
    @RequestMapping(value = "/deletarDependente")
    public String deletarDependente(String cpf){
        Dependentes dependente = dr.findByCpf(cpf);
        Funcionario funcionario = dependente.getFuncionario();
        String id = "" + funcionario.getId();

        dr.delete(dependente);
        return "redirect:/dependentes/" + id;
    }
}
