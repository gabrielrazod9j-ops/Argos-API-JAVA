package br.com.fiap.argos.handler;

import java.util.ArrayList;
import java.util.List;

// JSON padrao de erro da API
public class Erro {

    //encapsulamento, dados e atributos
    private int status;
    private String mensagem;
    private List<String> campos;

    //construtor vazio
    public Erro() {
        super();
    }

    //construtor cheio
    public Erro(int status, String mensagem) {
        super();
        this.status = status;
        this.mensagem = mensagem;
        this.campos = new ArrayList<>();
    }

    //construtor cheio com atributo de referencia
    public Erro(int status, String mensagem, List<String> campos) {
        super();
        this.status = status;
        this.mensagem = mensagem;
        this.campos = campos;
    }

    //getter e setter
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public List<String> getCampos() {
        return campos;
    }

    public void setCampos(List<String> campos) {
        this.campos = campos;
    }
}