package com.lmg.desafiojavaspringboot.service;

import com.lmg.desafiojavaspringboot.model.Pauta;
import com.lmg.desafiojavaspringboot.repository.PautaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PautaService {

    private final PautaRepository repository;

    public Pauta save(Pauta pauta) {
        return repository.save(pauta);
    }

    public Optional<Pauta> findById(Integer id) {
        return repository.findById(id);
    }

    public void delete(Pauta pauta) {
        if (pauta == null || pauta.getId() == null)
            throw new IllegalArgumentException("Pauta inválida");
        repository.delete(pauta);
    }

    public List<Pauta> findAll() {
        return repository.findAll();

    }
}
