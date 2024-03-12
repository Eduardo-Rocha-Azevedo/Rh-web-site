package com.AppRH.AppRH.repository;

import org.springframework.data.repository.CrudRepository;
import java.lang.Iterable;
import com.AppRH.AppRH.models.Dependentes;
import com.AppRH.AppRH.models.Funcionario;
import java.util.List;


public interface DependentesRepository extends CrudRepository<Dependentes, String>{
    
    Iterable <Dependentes> findByFuncionario(Funcionario funcionario);

    Dependentes findByCpf(String cpf);

    Dependentes findById(long id);

    List<Dependentes> findByNome(String nome);

}
