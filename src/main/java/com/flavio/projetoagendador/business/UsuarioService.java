package com.flavio.projetoagendador.business;

import com.flavio.projetoagendador.business.converter.UsuarioConverter;
import com.flavio.projetoagendador.business.dto.UsuarioDTO;
import com.flavio.projetoagendador.infraestructure.entity.Usuario;
import com.flavio.projetoagendador.infraestructure.exception.ConflictException;
import com.flavio.projetoagendador.infraestructure.exception.ResourceNotFoundException;
import com.flavio.projetoagendador.infraestructure.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UsuarioDTO salvarUsuario(UsuarioDTO usuarioDTO) {
        emailExite(usuarioDTO.getEmail());
        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }

    public void emailExite(String email) {
        try {
            boolean existe = verificaEmailExiste(email);
            if (existe) {
                throw new ConflictException("Email já cadastrado! " + email);
            }
        } catch (Exception e) {
            throw new ConflictException("email já cadastrado!", e.getCause());
        }
    }

    public boolean verificaEmailExiste(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    public UsuarioDTO buscarUsuarioPorEmail(String email) {
        try {
            return usuarioConverter.paraUsuarioDTO(
                    usuarioRepository.findByEmail(email).orElseThrow(
                            () -> new ResourceNotFoundException("Email não encontrado! " + email)
                    )
            );
        }catch(ResourceNotFoundException e){
            throw new ResourceNotFoundException("Email não encontrado! " + email);
        }
    }

    public void deletarUsuarioPorEmail(String email) {
        usuarioRepository.deleteByEmail(email);
    }


}
