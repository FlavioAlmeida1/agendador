package com.flavio.projetoagendador.business.converter;

import com.flavio.projetoagendador.business.dto.EnderecoDTO;
import com.flavio.projetoagendador.business.dto.TelefoneDTO;
import com.flavio.projetoagendador.business.dto.UsuarioDTO;
import com.flavio.projetoagendador.infraestructure.entity.Endereco;
import com.flavio.projetoagendador.infraestructure.entity.Telefone;
import com.flavio.projetoagendador.infraestructure.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UsuarioConverter {
    public Usuario paraUsuario(UsuarioDTO usuarioDTO) {
        return Usuario.builder()
                .nome(usuarioDTO.getNome())
                .email(usuarioDTO.getEmail())
                .senha(usuarioDTO.getSenha())
                .enderecos(paraListarEndereco(usuarioDTO.getEnderecos()))
                .telefones(paraListarTelefones(usuarioDTO.getTelefones()))
                .build();
    }

    public List<Endereco> paraListarEndereco(List<EnderecoDTO> enderecoDTOS) {
        //return enderecoDTOS.stream().map(this::paraEndereco).toList(); forma mais avançada.
        List<Endereco> enderecos = new ArrayList<>();
        for (EnderecoDTO enderecoDTO : enderecoDTOS) {
            enderecos.add(paraEndereco(enderecoDTO));
        }
        return enderecos;
    }

    public Endereco paraEndereco(EnderecoDTO enderecoDTO) {
        return Endereco.builder()
                .rua(enderecoDTO.getRua())
                .numero(enderecoDTO.getNumero())
                .cidade(enderecoDTO.getCidade())
                .complemento(enderecoDTO.getComplemento())
                .cep(enderecoDTO.getCep())
                .bairro(enderecoDTO.getBairro())
                .estado(enderecoDTO.getEstado())
                .build();
    }

    public List<Telefone> paraListarTelefones(List<TelefoneDTO> telefoneDTO) {
        return telefoneDTO.stream().map(this::paraTelefone).toList();
    }

    public Telefone paraTelefone(TelefoneDTO telefoneDTO) {
        return Telefone.builder()
                .numero(telefoneDTO.getNumero())
                .ddd(telefoneDTO.getDdd())
                .build();
    }

    //**************Convertendo para dto************************************
    public UsuarioDTO paraUsuarioDTO(Usuario usuarioDTO) {
        return UsuarioDTO.builder()
                .nome(usuarioDTO.getNome())
                .email(usuarioDTO.getEmail())
                .senha(usuarioDTO.getSenha())
                .enderecos(paraListarEnderecosDTO(usuarioDTO.getEnderecos()))
                .telefones(paraListarTelefonesDTO(usuarioDTO.getTelefones()))
                .build();
    }

    public List<EnderecoDTO> paraListarEnderecosDTO(List<Endereco> enderecoDTOS) {
        List<EnderecoDTO> enderecos = new ArrayList<>();
        for (Endereco enderecoDTO : enderecoDTOS) {
            enderecos.add(paraEnderecoDTO(enderecoDTO));
        }
        return enderecos;
    }

    public EnderecoDTO paraEnderecoDTO(Endereco endereco) {
        return EnderecoDTO.builder()
                .id(endereco.getId())
                .rua(endereco.getRua())
                .numero(endereco.getNumero())
                .cidade(endereco.getCidade())
                .complemento(endereco.getComplemento())
                .cep(endereco.getCep())
                .bairro(endereco.getBairro())
                .estado(endereco.getEstado())
                .build();
    }

    public List<TelefoneDTO> paraListarTelefonesDTO(List<Telefone> telefoneDTO) {
        return telefoneDTO.stream().map(this::paraTelefoneDTO).toList();
    }

    public TelefoneDTO paraTelefoneDTO(Telefone telefone) {
        return TelefoneDTO.builder()
                .id(telefone.getId())
                .numero(telefone.getNumero())
                .ddd(telefone.getDdd())
                .build();
    }

    public Usuario updateUsuario(UsuarioDTO usuarioDTO, Usuario entity){
        return Usuario.builder()
                .id(entity.getId())
                .nome(usuarioDTO.getNome() != null ? usuarioDTO.getNome() : entity.getNome())
                .email(usuarioDTO.getEmail() != null ? usuarioDTO.getEmail() : entity.getEmail())
                .senha(usuarioDTO.getSenha() != null ? usuarioDTO.getSenha() : entity.getSenha())
                .enderecos(entity.getEnderecos())
                .telefones(entity.getTelefones())
                .build();
    }

    public Endereco updateEndereco(EnderecoDTO enderecoDTO, Endereco entity){
        return Endereco.builder()
                .id(entity.getId())
                .rua(enderecoDTO.getRua() != null ? enderecoDTO.getRua() : entity.getRua())
                .numero(enderecoDTO.getNumero() != null ? enderecoDTO.getNumero() : entity.getNumero())
                .complemento(enderecoDTO.getComplemento() != null ? enderecoDTO.getComplemento() : entity.getComplemento())
                .bairro(enderecoDTO.getBairro() != null ? enderecoDTO.getBairro() : entity.getBairro())
                .estado(enderecoDTO.getEstado() != null ? enderecoDTO.getEstado() : entity.getEstado())
                .cep(enderecoDTO.getCep() != null ? enderecoDTO.getCep() : entity.getCep())
                .cidade(enderecoDTO.getCidade() != null ? enderecoDTO.getCidade() : entity.getCidade())
                .usuario_id(entity.getUsuario_id())
                .build();
    }
    public Telefone updateTelefone(TelefoneDTO dto, Telefone entity ){
        return Telefone.builder()
                .id(entity.getId())
                .numero(dto.getNumero() != null ? dto.getNumero() : entity.getNumero())
                .ddd(dto.getDdd() != null ? dto.getDdd() : entity.getDdd())
                .usuario_id(entity.getUsuario_id())
                .build();

    }
    public Endereco paraEnderecoEntity(EnderecoDTO dto, Long idUsuario) {
        return Endereco.builder()
                .rua(dto.getRua())
                .numero(dto.getNumero())
                .cidade(dto.getCidade())
                .complemento(dto.getComplemento())
                .cep(dto.getCep())
                .estado(dto.getEstado())
                .bairro(dto.getBairro())
                .usuario_id(idUsuario)
                .build();
    }
    public Telefone paraTelefoneEntity(TelefoneDTO dto, Long idUsuario){
        return Telefone.builder()
                .ddd(dto.getDdd())
                .numero(dto.getNumero())
                .usuario_id(idUsuario)
                .build();
    }


}
