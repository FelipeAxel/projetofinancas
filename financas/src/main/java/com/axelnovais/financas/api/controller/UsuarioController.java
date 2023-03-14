package com.axelnovais.financas.api.controller;

import com.axelnovais.financas.dto.UsuarioDTO;
import com.axelnovais.financas.exceptions.ErroAutenticacao;
import com.axelnovais.financas.exceptions.RegraDeNogocioException;
import com.axelnovais.financas.model.entity.Usuario;
import com.axelnovais.financas.services.LancamentoService;
import com.axelnovais.financas.services.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private UsuarioService service;
    private LancamentoService lancamentoService;

    public UsuarioController(UsuarioService service, LancamentoService lancamentoService) {
        this.service = service;
        this.lancamentoService = lancamentoService;
    }


    @PostMapping("/autenticar")
    public ResponseEntity autenticar(@RequestBody UsuarioDTO dto){
        try{
            Usuario usuarioAutenticado = service.autenticar(dto.getEmail(), dto.getSenha());
            return ResponseEntity.ok(usuarioAutenticado);
        }catch (ErroAutenticacao e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping
    public ResponseEntity salvar(@RequestBody UsuarioDTO dto) {
        Usuario usuario = Usuario.builder()
                .nome(dto.getNome())
                .email(dto.getEmail())
                .senha((dto.getSenha()))
                .build();

        try {
            Usuario usuarioSalvo = service.salvarUsuario(usuario);
            return new ResponseEntity(usuarioSalvo, HttpStatus.CREATED);
        } catch (RegraDeNogocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("{id}/saldo")
    public ResponseEntity obterSaldo( @PathVariable("id") Long id ) {
        Optional<Usuario> usuario = service.obterPorId(id);

        if(!usuario.isPresent()) {
            return new ResponseEntity( HttpStatus.NOT_FOUND );
        }

        BigDecimal saldo = lancamentoService.obterSaldoPorUsuario(id);
        return ResponseEntity.ok(saldo);
    }
}
