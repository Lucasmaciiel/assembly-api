package com.lmg.assembly.domain.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ValidationCpfDTO implements Serializable {
    private static final long serialVersionUID = 633031857370234293L;

    private String status;

}