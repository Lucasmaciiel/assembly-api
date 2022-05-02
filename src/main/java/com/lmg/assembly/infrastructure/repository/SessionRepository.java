package com.lmg.assembly.infrastructure.repository;

import com.lmg.assembly.infrastructure.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Integer> {

    Optional<Session> findByIdAndPautaId(Integer id, Integer pautaId);

    Long countByPautaId(Integer id);

}
