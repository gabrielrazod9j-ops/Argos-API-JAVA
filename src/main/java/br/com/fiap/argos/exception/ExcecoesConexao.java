package br.com.fiap.argos.exception;

import java.sql.SQLException;

public class ExcecoesConexao extends Exception {

    public ExcecoesConexao() {
    }

    public ExcecoesConexao(Exception e) {
        super(definirMensagem(e));
        System.out.println(getMessage());
    }

    private static String definirMensagem(Exception e) {
        if (e instanceof ClassNotFoundException) {
            return "Sem comunicação com o Banco de Dados";
        } else if (e instanceof SQLException) {
            return "Informações de usuário incorretas, acesso negado";
        } else {
            return "Falha desconhecida";
        }
    }
}
