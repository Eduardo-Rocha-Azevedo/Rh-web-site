package com.AppRh.RhApp.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.AppRh.RhApp.models.Candidate;
import com.AppRh.RhApp.models.Vaga;
@Repository
public interface CandidateRepository extends CrudRepository<Candidate, String>{

    Iterable<Candidate> findByVaga(Vaga vaga);

    Candidate findById(long id);

    Candidate findByRg(String rg);

    List<Candidate> findByNameCandidate(String nome);
}
