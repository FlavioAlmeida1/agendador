package com.flavio.projetoagendador.business;

import com.flavio.projetoagendador.business.converter.UsuarioConverter;
import com.flavio.projetoagendador.business.dto.EnderecoDTO;
import com.flavio.projetoagendador.business.dto.TelefoneDTO;
import com.flavio.projetoagendador.business.dto.UsuarioDTO;
import com.flavio.projetoagendador.infraestructure.entity.Endereco;
import com.flavio.projetoagendador.infraestructure.entity.Telefone;
import com.flavio.projetoagendador.infraestructure.entity.Usuario;
import com.flavio.projetoagendador.infraestructure.exception.ConflictException;
import com.flavio.projetoagendador.infraestructure.exception.ResourceNotFoundException;
import com.flavio.projetoagendador.infraestructure.repository.EnderecoRepository;
import com.flavio.projetoagendador.infraestructure.repository.TelefoneRepository;
import com.flavio.projetoagendador.infraestructure.repository.UsuarioRepository;
import com.flavio.projetoagendador.infraestructure.security.JwtUtil;
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
    private final JwtUtil jwtUtil;
    private final TelefoneRepository telefoneRepository;
    private final EnderecoRepository enderecoRepository;

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
                    usuarioRepository.findByEmail(email)
                            .orElseThrow(
                    () -> new ResourceNotFoundException("Email não encontrado! " + email)
                    )
            );
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Email não encontrado! " + email);
        }
    }

    public void deletarUsuarioPorEmail(String email) {
        usuarioRepository.deleteByEmail(email);
    }

    public UsuarioDTO atualizaDadosUsuario(String token, UsuarioDTO dto) {
        // Aqui buscamos o email do usuario atraves do token( tirar a obrigatoriedade do email).
        String email = jwtUtil.extrairEmailToken(token.substring(7));

        // Criptografia de senha
        dto.setSenha(dto.getSenha() != null ? passwordEncoder.encode(dto.getSenha()) : null);

        // Busca os dados do usuario no banco de dados.
        Usuario usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("Email não encontrado! " + email));

        // Mesclou os dados que recebemos na requisição DTO coms os dados do bando de dados.
        Usuario usuario = usuarioConverter.updateUsuario(dto, usuarioEntity);

        // Salvou os dados do usuário convertido e depois pegou o retorno e converteu para UsuarioDTO.
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));

    }

    public EnderecoDTO atualizaEndereco(Long idEndereco, EnderecoDTO enderecoDTO) {
        Endereco entity = enderecoRepository.findById(idEndereco).orElseThrow(() ->
                new ResourceNotFoundException("Id não encontrado! " + idEndereco));

        Endereco endereco = usuarioConverter.updateEndereco(enderecoDTO, entity);

        return usuarioConverter.paraEnderecoDTO(enderecoRepository.save(endereco));

    }



    public TelefoneDTO atualizaTelefone(Long idTelefone, TelefoneDTO telefoneDTO) {
        Telefone entity = telefoneRepository.findById(idTelefone).orElseThrow(() ->
                new ResourceNotFoundException("Id não encontrado! " + idTelefone));

        Telefone telefone = usuarioConverter.updateTelefone(telefoneDTO, entity);

        return usuarioConverter.paraTelefoneDTO(telefoneRepository.save(telefone));
    }

    public EnderecoDTO cadastraEndereco(String token, EnderecoDTO enderecoDTO) {
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("Email não encontrado! " + email));

        Endereco endereco = usuarioConverter.paraEnderecoEntity(enderecoDTO, usuario.getId());
        return usuarioConverter.paraEnderecoDTO(enderecoRepository.save(endereco));
    }
    public TelefoneDTO cadastraTelefone(String token, TelefoneDTO dto){
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("Email não localizado! " + email));

        Telefone telefone = usuarioConverter.paraTelefoneEntity(dto, usuario.getId());
        return usuarioConverter.paraTelefoneDTO(telefoneRepository.save(telefone));
    }

    }
