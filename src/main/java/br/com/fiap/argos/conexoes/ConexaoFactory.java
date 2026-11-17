package br.com.fiap.argos.conexoes;

import br.com.fiap.argos.exception.ExcecoesConexao;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexaoFactory {

    private final static String URL = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:orcl";

    // metodo de conexao com o banco de dados
    public Connection conexao() throws ExcecoesConexao {

        String usuario = System.getenv("DB_USUARIO");
        String senha = System.getenv("DB_SENHA");

        try {
            // Driver oracle
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // Retornar conexao
            return DriverManager.getConnection(URL, usuario, senha);
        } catch (Exception e) {
            throw new ExcecoesConexao(e);
        }
    }
}
