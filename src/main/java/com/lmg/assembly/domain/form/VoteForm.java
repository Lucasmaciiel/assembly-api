package com.lmg.assembly.domain.form;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class VoteForm {

    @NotBlank(message = "O CPF é obrigatório")
    private String cpfCooperated;

    @NotNull(message = "Opção é obrigatório")
    private Boolean choice; //opção escolhida

}
