package com.axelnovais.financas.model.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.axelnovais.financas.model.entity.Usuario;

import java.util.Optional;


@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UsuarioRepositoryTest {
	
	@Autowired
	UsuarioRepository repository;

	@Autowired
	TestEntityManager entityManeger;
	@Test
	public void deveVerificarAExistenciaDeUmEmail() {
		Usuario usuario = criarUsuario();
		entityManeger.persist(usuario);
		
		boolean result = repository.existsByEmail("usuario@email.com");
		
		Assertions.assertThat(result).isTrue();
	}

	@Test
	public void retornarFalsoQuandoNaoHouverUsuarioCadastradoComEmail(){

		boolean result = repository.existsByEmail("usuario@email.com");

		Assertions.assertThat(result).isFalse();
	}

	@Test
	public void DevePersistirUmUsuarioNaBaseDeDdos(){
		Usuario usuario = criarUsuario();

		Usuario usuarioSalvo = repository.save(usuario);

		Assertions.assertThat(usuarioSalvo.getId()).isNotNull();
	}

	@Test
	public void deveBuscarUmUsuarioPorEmail(){
		Usuario usuario = criarUsuario();
		entityManeger.persist(usuario);

		Optional <Usuario> result = repository.findByEmail("usuario@email.com");

		Assertions.assertThat(result.isPresent()).isTrue();
	}

	@Test
	public void deveRetoranarVazioAoBuscarPorUmEmailQuandoNaoExisteNaBase(){

		Optional <Usuario> result = repository.findByEmail("usuario@email.com");

		Assertions.assertThat(result.isPresent()).isFalse();
	}

	public static Usuario criarUsuario(){
		return
				Usuario.builder()
						.nome("usuario")
						.email("usuario@email.com")
						.senha("senha")
						.build();
	}
}
