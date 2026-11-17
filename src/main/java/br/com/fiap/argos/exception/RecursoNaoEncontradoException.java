package br.com.fiap.argos.exception;

// serve para quando o id pedido não existir no banco, ai retorna o erro 404
public class RecursoNaoEncontradoException extends RuntimeException {

    public RecursoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
