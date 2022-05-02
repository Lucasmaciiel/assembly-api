package com.lmg.assembly.domain.service;

import com.lmg.assembly.common.exception.EntityNotFoundException;
import com.lmg.assembly.infrastructure.model.Pauta;
import com.lmg.assembly.infrastructure.repository.PautaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PautaService {

    public static final String INVALID_PAUTA = "Pauta inválida";

    private final PautaRepository repository;

    public Pauta save(Pauta pauta) {
        return repository.save(pauta);
    }

    public Optional<Pauta> findById(Integer id) {
        Optional<Pauta> idPesquisado = repository.findById(id);
        if (!idPesquisado.isPresent())
            throw new EntityNotFoundException("Pauta com ID: " + id + " não existe");
        return idPesquisado;
    }

    public void delete(Pauta pauta) {
        if (Objects.isNull(pauta) || Objects.isNull(pauta.getId()))
            throw new IllegalArgumentException(INVALID_PAUTA);
        repository.delete(pauta);
    }

    public List<Pauta> findAll() {
        return repository.findAll();

    }
}
