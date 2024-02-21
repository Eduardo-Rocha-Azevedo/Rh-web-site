package com.AppRh.RhApp.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.AppRh.RhApp.models.Vaga;

public interface VagaRepository extends CrudRepository<Vaga, String>{
    Vaga findByCodigo(long id);
    List<Vaga> findByNome(String nome);
}
