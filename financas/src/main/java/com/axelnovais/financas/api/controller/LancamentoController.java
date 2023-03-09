package com.axelnovais.financas.api.controller;

import com.axelnovais.financas.dto.LancamentoDTO;
import com.axelnovais.financas.exceptions.RegraDeNogocioException;
import com.axelnovais.financas.model.entity.Lancamento;
import com.axelnovais.financas.model.entity.Usuario;
import com.axelnovais.financas.model.enums.StatusLancamento;
import com.axelnovais.financas.model.enums.TipoLancamento;
import com.axelnovais.financas.services.LancamentoService;
import com.axelnovais.financas.services.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/lancamentos")
public class LancamentoController {

        private LancamentoService service;
        private UsuarioService usuarioService;
        public LancamentoController(LancamentoService service){
            this.service = service;
        }

        @PostMapping
        public ResponseEntity salvar(@RequestBody LancamentoDTO dto){

                return null;
        }

        private Lancamento converter(LancamentoDTO dto){
                Lancamento lancamento = new Lancamento();
                lancamento.setId(dto.getId());
                lancamento.setDescricao(dto.getDescricao());
                lancamento.setAno(dto.getAno());
                lancamento.setMes(dto.getMes());
                lancamento.setValor(dto.getValor());

                Usuario usuario = usuarioService
                        .obterPorId(dto.getUsuario())
                        .orElseThrow(() -> new RegraDeNogocioException("Usuario não encontrado para o ID informado"));

                lancamento.setUsuario(usuario);
                lancamento.setTipo(TipoLancamento.valueOf(dto.getTipo()));
                lancamento.setStatus(StatusLancamento.valueOf(dto.getStatus()));
                return lancamento;
        }
}
