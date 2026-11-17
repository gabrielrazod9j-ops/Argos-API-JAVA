package br.com.fiap.argos.handler;

import br.com.fiap.argos.exception.ExcecoesConexao;
import br.com.fiap.argos.exception.RecursoNaoEncontradoException;
import br.com.fiap.argos.exception.RegraNegocioException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.ArrayList;
import java.util.List;

// Trata os erros de todos os controllers e devolve sempre o mesmo JSON (classe Erro)
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 400: campo invalido no DTO (@Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Erro> validacao(MethodArgumentNotValidException e) {
        List<String> campos = new ArrayList<>();
        for (FieldError erro : e.getBindingResult().getFieldErrors()) {
            campos.add(erro.getField() + ": " + erro.getDefaultMessage());
        }
        return ResponseEntity.status(400).body(new Erro(400, "Dados inválidos", campos));
    }

    // 400: JSON quebrado ou com tipo errado
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Erro> jsonInvalido(HttpMessageNotReadableException e) {
        return ResponseEntity.status(400).body(new Erro(400, "JSON inválido"));
    }

    // 404: id nao encontrado
    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<Erro> naoEncontrado(RecursoNaoEncontradoException e) {
        return ResponseEntity.status(404).body(new Erro(404, e.getMessage()));
    }

    // 404: rota que nao existe
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Erro> rotaInexistente(NoResourceFoundException e) {
        return ResponseEntity.status(404).body(new Erro(404, "Rota não encontrada"));
    }

    // 405: metodo nao aceito na rota
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Erro> metodoNaoPermitido(HttpRequestMethodNotSupportedException e) {
        return ResponseEntity.status(405).body(new Erro(405, "Método não permitido"));
    }

    // 409: regra de negocio violada
    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<Erro> regraNegocio(RegraNegocioException e) {
        return ResponseEntity.status(409).body(new Erro(409, e.getMessage()));
    }

    // 500: qualquer outro erro (banco fora do ar, erro de SQL...). O detalhe aparece so no console.
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Erro> erroInterno(Exception e) {
        System.out.println("Erro: " + e);
        String mensagem = (e instanceof ExcecoesConexao) ? e.getMessage() : "Erro interno no servidor";
        return ResponseEntity.status(500).body(new Erro(500, mensagem));
    }
}