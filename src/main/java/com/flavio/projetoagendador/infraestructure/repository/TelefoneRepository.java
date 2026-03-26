package com.flavio.projetoagendador.infraestructure.repository;


import com.flavio.projetoagendador.infraestructure.entity.Telefone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TelefoneRepository  extends JpaRepository<Telefone, Long> {
}
