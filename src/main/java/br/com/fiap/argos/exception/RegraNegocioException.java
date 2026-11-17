package br.com.fiap.argos.exception;

// serve para quando uma regra do sistema for quebrada, usei o RuntimeException para a exceção subir sozinha no handler
// para não precisar escrever em cada metodo bo e do controller as excecoes. ai retorna o erro 409.
public class RegraNegocioException extends RuntimeException {

    public RegraNegocioException(String mensagem) {
        super(mensagem);
    }
}
