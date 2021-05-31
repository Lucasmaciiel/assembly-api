package com.lmg.desafiojavaspringboot.api.repository;

import com.lmg.desafiojavaspringboot.api.model.Sessao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessaoRepository extends JpaRepository<Sessao, Integer> {

    Optional<Sessao> findByIdAndPautaId(Integer id, Integer pautaId);

    Long countByPautaId(Integer id);

}
