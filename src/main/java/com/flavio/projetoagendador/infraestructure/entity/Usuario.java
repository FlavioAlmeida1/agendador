package com.flavio.projetoagendador.infraestructure.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuario")
@Builder
public class Usuario {
    @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)  //para gerar o id automaticamente no banco de dados
    private Long id;
    @Column(name = "nome", length = 200)
    private String nome;
    @Column(name = "email", length = 150)
    private String email;
    @Column(name = "senha")
    private String senha;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id") // para ligar uma tabela a outra
    private List<Endereco> enderecos;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn( name = "usuario_id",referencedColumnName = "id")
    private  List <Telefone> telefones;


}
