package com.AppRH.AppRH.repository;

import org.springframework.data.repository.CrudRepository;

import com.AppRH.AppRH.models.Funcionario;

public interface FuncionarioRepository extends CrudRepository<Funcionario, String>{

    Funcionario findById(long id);
    
    //busca
    Funcionario findByNome(String nome);
}
    

