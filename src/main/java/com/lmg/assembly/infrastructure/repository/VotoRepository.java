package com.lmg.assembly.infrastructure.repository;

import com.lmg.assembly.infrastructure.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VotoRepository extends JpaRepository<Vote, Integer> {
    Optional<Vote> findByCpf(String cpf);

    Optional<List<Vote>> findByPautaId(Integer id);

    Optional<Vote> findByCpfAndPautaId(String cpf, Integer id);
}
