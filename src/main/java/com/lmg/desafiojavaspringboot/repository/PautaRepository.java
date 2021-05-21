package com.lmg.desafiojavaspringboot.repository;

import com.lmg.desafiojavaspringboot.model.Pauta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PautaRepository extends JpaRepository<Pauta, Integer> {
}
