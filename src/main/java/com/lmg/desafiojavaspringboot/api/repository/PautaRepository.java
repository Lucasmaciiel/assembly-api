package com.lmg.desafiojavaspringboot.api.repository;

import com.lmg.desafiojavaspringboot.api.model.Pauta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PautaRepository extends JpaRepository<Pauta, Integer> {
}
