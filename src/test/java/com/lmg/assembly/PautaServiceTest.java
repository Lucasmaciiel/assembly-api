package com.lmg.assembly;

import com.lmg.assembly.domain.service.PautaService;
import com.lmg.assembly.infrastructure.model.Pauta;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource("/application-test.properties")
@DisplayName("Teste da camada PautaService")
class PautaServiceTest { // Testes de integração

    @Autowired
    private PautaService pautaService;

    @Test
    @DisplayName("Deve cadastrar pauta com sucesso")
    void deveCadastrarPautaComSucesso() {
        var pauta = new Pauta();
        pauta.setName(UUID.randomUUID().toString());

        pauta = pautaService.save(pauta);

        assertThat(pauta).isNotNull();
        assertThat(pauta.getId()).isNotNull();
    }

}
