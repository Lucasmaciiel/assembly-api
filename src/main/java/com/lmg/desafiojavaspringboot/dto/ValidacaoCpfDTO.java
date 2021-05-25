package com.lmg.desafiojavaspringboot.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ValidacaoCpfDTO implements Serializable {
    private static final long serialVersionUID = 633031857370234293L;

    private String status;
}