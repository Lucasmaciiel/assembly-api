package com.lmg.desafiojavaspringboot.repository;

import com.lmg.desafiojavaspringboot.model.Voto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VotoRepository extends JpaRepository<Voto, Integer> {
    Optional<Voto> findByCpf(String cpf);

    Optional<List<Voto>> findByPautaId(Integer id);

    Optional<Voto> findByCpfAndPautaId(String cpf, Integer id);
}
