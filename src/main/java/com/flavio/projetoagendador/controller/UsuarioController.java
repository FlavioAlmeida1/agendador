package com.flavio.projetoagendador.controller;

import com.flavio.projetoagendador.business.UsuarioService;
import com.flavio.projetoagendador.business.dto.EnderecoDTO;
import com.flavio.projetoagendador.business.dto.TelefoneDTO;
import com.flavio.projetoagendador.business.dto.UsuarioDTO;
import com.flavio.projetoagendador.infraestructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<UsuarioDTO> salvaUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        return ResponseEntity.ok(usuarioService.salvarUsuario(usuarioDTO));
    }

    @PostMapping("/login")
    public String login(@RequestBody UsuarioDTO usuarioDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usuarioDTO.getEmail(), usuarioDTO.getSenha())
        );
        return "Bearer " + jwtUtil.generateToken(authentication.getName());
    }

    @GetMapping
    public ResponseEntity<UsuarioDTO> buscsaUsuarioPorEmail(@RequestParam("email") String email) {
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorEmail(email));
    }                                                       //ResponseEntity e usado para os verbos no http retornar corretamente.

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deletaUsuarioPorEmail(@PathVariable String email) {
        usuarioService.deletarUsuarioPorEmail(email);
        return ResponseEntity.ok().build();

    }

    @PutMapping
    public ResponseEntity<UsuarioDTO>atualizaDadosUsuario(@RequestBody UsuarioDTO dto,
                                                     @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(usuarioService.atualizaDadosUsuario(token, dto));
    }
    @PutMapping("/endereco")
    public ResponseEntity<EnderecoDTO>atualizaEndereco(@RequestBody EnderecoDTO enderecoDTO,
                                                       @RequestParam("id") Long id){
        return ResponseEntity.ok(usuarioService.atualizaEndereco(id, enderecoDTO));
    }

    @PutMapping("/telefone")
    public ResponseEntity<TelefoneDTO>atualizaTelefone(@RequestBody TelefoneDTO dto,
                                                       @RequestParam("id")Long id){
        return ResponseEntity.ok(usuarioService.atualizaTelefone(id, dto));

    }
    @PostMapping("/endereco")
    public ResponseEntity<EnderecoDTO>cadastraEndereco(@RequestBody EnderecoDTO dto,
                                                    @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(usuarioService.cadastraEndereco(token, dto));
    }
    @PostMapping("/telefone")
    public ResponseEntity<TelefoneDTO>cadastraTelefone(@RequestBody TelefoneDTO dto,
                                                       @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(usuarioService.cadastraTelefone(token, dto));
    }
}
