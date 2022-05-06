package com.lmg.assembly.domain.service;

import com.lmg.assembly.common.exception.EntityInUseException;
import com.lmg.assembly.common.exception.EntityNotFoundException;
import com.lmg.assembly.infrastructure.model.Pauta;
import com.lmg.assembly.infrastructure.repository.PautaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PautaService {

    public static final String INVALID_PAUTA = "Pauta inválida";

    private final PautaRepository repository;

    @Transactional
    public Pauta save(Pauta pauta) {
        return repository.save(pauta);
    }

    public Pauta findById(Integer id) {
        Optional<Pauta> idPesquisado = repository.findById(id);
        if (!idPesquisado.isPresent())
            throw new EntityNotFoundException("Pauta com ID: " + id + " não existe");
        return idPesquisado.get();
    }

    @Transactional
    public void delete(Integer pautaId) {
        var pauta = this.findById(pautaId);
        try {
            repository.delete(pauta);
            repository.flush();
        }catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(String.format("Pauta de código %d não pode ser removida, pois está em uso", pautaId));
        }
    }

    public List<Pauta> findAll() {
        return repository.findAll();

    }
}
