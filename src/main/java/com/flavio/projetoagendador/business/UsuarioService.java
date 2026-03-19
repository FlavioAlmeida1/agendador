package com.flavio.projetoagendador.business;

import com.flavio.projetoagendador.business.converter.UsuarioConverter;
import com.flavio.projetoagendador.business.dto.UsuarioDTO;
import com.flavio.projetoagendador.infraestructure.entity.Usuario;
import com.flavio.projetoagendador.infraestructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;

    public UsuarioDTO salvarUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }

}
