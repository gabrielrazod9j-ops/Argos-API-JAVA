package br.com.fiap.argos.exception;

// Id que nao existe no banco -> 404
public class RecursoNaoEncontradoException extends RuntimeException {

    public RecursoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
