package com.axelnovais.financas.services.impl;

import com.axelnovais.financas.exceptions.RegraDeNogocioException;
import com.axelnovais.financas.model.entity.Lancamento;
import com.axelnovais.financas.model.enums.StatusLancamento;
import com.axelnovais.financas.model.repository.LancamentoRepository;
import com.axelnovais.financas.services.LancamentoService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class LancamentoServiceImpl implements LancamentoService {

    private LancamentoRepository repository;

    public LancamentoServiceImpl(LancamentoRepository repository){
        this.repository = repository;
    }
    @Override
    @Transactional
    public Lancamento salvar(Lancamento lancamento) {
        validar(lancamento);
        lancamento.setStatus(StatusLancamento.PENDENTE);
        return repository.save(lancamento);

    }

    @Override
    @Transactional
    public Lancamento atualizar(Lancamento lancamento) {
        Objects.requireNonNull(lancamento.getId());
        validar(lancamento);
        return repository.save(lancamento);
    }

    @Override
    @Transactional
    public void deletar(Lancamento lancamento) {
        Objects.requireNonNull(lancamento.getId());
        repository.delete(lancamento);
    }

    @Override
    @Transactional
    public List<Lancamento> buscar(Lancamento lancamentoFiltro) {
        Example example = Example.of(lancamentoFiltro, ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));

        return repository.findAll(example);
    }

    @Override
    public void atualizarStatus(Lancamento lancamento, StatusLancamento status) {
        lancamento.setStatus(status);
        atualizar(lancamento);
    }

    @Override
    public void validar(Lancamento lancamento) {
        if(lancamento.getDescricao() == null || lancamento.getDescricao().trim().equals("")){
            throw new RegraDeNogocioException("Informe uma descrição válida.");
        }
        if(lancamento.getMes() == null || lancamento.getMes() <1 || lancamento.getMes()>12){
            throw new RegraDeNogocioException("Informe um mês válido.");
        }

        if(lancamento.getAno() == null || lancamento.getAno().toString().length()!= 4){
            throw new RegraDeNogocioException("Informe um ano válido");
        }

        if(lancamento.getUsuario() == null || lancamento.getUsuario().getId() == null){
            throw new RegraDeNogocioException(("Informe um usuario"));
        }
        if(lancamento.getValor() == null || lancamento.getValor().compareTo(BigDecimal.ZERO)<1){
            throw new RegraDeNogocioException("Informe um valor válido");
        }
        if(lancamento.getTipo() == null){
            throw new RegraDeNogocioException("Informe um tipo de lancamento");
        }
    }

    @Override
    public Optional<Lancamento> obterPorId(Long id) {
        return repository.findById(id);
    }
}
