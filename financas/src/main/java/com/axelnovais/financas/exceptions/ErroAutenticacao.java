package com.axelnovais.financas.exceptions;

public class ErroAutenticacao extends RuntimeException{
    public ErroAutenticacao(String mensagem){
        super (mensagem);
    }
}
