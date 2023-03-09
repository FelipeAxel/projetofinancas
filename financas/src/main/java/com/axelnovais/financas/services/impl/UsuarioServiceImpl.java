package com.axelnovais.financas.services.impl;

import com.axelnovais.financas.exceptions.ErroAutenticacao;
import org.springframework.stereotype.Service;

import com.axelnovais.financas.exceptions.RegraDeNogocioException;
import com.axelnovais.financas.model.entity.Usuario;
import com.axelnovais.financas.model.repository.UsuarioRepository;
import com.axelnovais.financas.services.UsuarioService;

import javax.transaction.Transactional;
import java.util.Optional;


@Service
public class UsuarioServiceImpl implements UsuarioService{
	
	private UsuarioRepository repository;

	
	public UsuarioServiceImpl(UsuarioRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public Usuario autenticar(String email, String senha) {
		Optional<Usuario> usuario = repository.findByEmail(email);

		if(!usuario.isPresent()){
			throw new ErroAutenticacao("Usuário não encontrado para o email informado.");
		}
		if(!usuario.get().getSenha().equals(senha)){
			throw new ErroAutenticacao("senha inválida.");
		}
		return usuario.get();
	}

	@Override
	@Transactional
	public Usuario salvarUsuario(Usuario usuario) {
		validarEmail(usuario.getEmail());
		return repository.save(usuario);
	}

	@Override
	public void validarEmail(String email) {
		boolean existe = repository.existsByEmail(email);
		if(existe) {
			throw new RegraDeNogocioException("Ja existe usuario cadastrado com esse email");
		}
	}

	@Override
	public Optional <Usuario> obterPorId(Long id) {
		return repository.findById(id);
	}

}
