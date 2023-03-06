package com.axelnovais.financas.api.controller;

import com.axelnovais.financas.dto.UsuarioDTO;
import com.axelnovais.financas.exceptions.RegraDeNogocioException;
import com.axelnovais.financas.model.entity.Usuario;
import com.axelnovais.financas.services.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private UsuarioService service;
    public UsuarioController(UsuarioService service){
        this.service =service;
    }
            @PostMapping
            public ResponseEntity salvar(@RequestBody UsuarioDTO dto ){
                Usuario usuario = Usuario.builder()
                        .nome(dto.getNome())
                        .email(dto.getEmail())
                        .senha((dto.getSenha()))
                        .build();

                try{
                    Usuario usuarioSalvo = service.salvarUsuario(usuario);
                    return new ResponseEntity(usuarioSalvo, HttpStatus.CREATED);
                }catch(RegraDeNogocioException e){
                    return ResponseEntity.badRequest().body(e.getMessage());
                }
            }
}
