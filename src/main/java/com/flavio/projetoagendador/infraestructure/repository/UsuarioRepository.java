package com.flavio.projetoagendador.infraestructure.repository;


import com.flavio.projetoagendador.infraestructure.entity.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsByEmail(String email);

    Optional<Usuario> findByEmail(String email);

    @Transactional
    void deleteByEmail(String email);
}

/*Optinal <> : É uma classe do JavaUtil, que ela é utilizada para evitar o retorno de informações nulas.
 * Quando for buscar alguma informção no banco de dados e não existir ao inves de trazer uma informação nula
 * e trazer o "nullpointerException" um erro que vai fazer o progrma quebrar o sistema vai desconsiderar.

*/
