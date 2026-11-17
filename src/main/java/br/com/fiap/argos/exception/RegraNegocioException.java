package br.com.fiap.argos.exception;

// Regra de negocio violada (area excedida, telefone repetido, registro com filhos) -> 409
public class RegraNegocioException extends RuntimeException {

    public RegraNegocioException(String mensagem) {
        super(mensagem);
    }
}
