package com.flavio.projetoagendador.business.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnderecoDTO {
    private String rua;
    private Long numero;
    private String complemento;
    private String bairro;
    private String cep;
    private String cidade;
    private String estado;
}
