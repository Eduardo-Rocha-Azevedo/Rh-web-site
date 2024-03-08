package com.AppRh.RhApp.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.AppRh.RhApp.models.Vaga;

@Repository
public interface VagaRepository extends CrudRepository<Vaga, String>{
    Vaga findByCodigo(long codigo);
    List<Vaga> findByNome(String nome);
}
