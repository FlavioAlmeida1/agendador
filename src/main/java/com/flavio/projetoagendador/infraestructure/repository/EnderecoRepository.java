package com.flavio.projetoagendador.infraestructure.repository;


import com.flavio.projetoagendador.infraestructure.entity.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
}
