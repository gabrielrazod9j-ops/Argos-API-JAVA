package br.com.fiap.argos.dao;

import br.com.fiap.argos.entities.Produtor;
import br.com.fiap.argos.conexoes.ConexaoFactory;
import br.com.fiap.argos.exception.ExcecoesConexao;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProdutorDAO {

    // Insert (o id vem da sequence sq_tb_produtor)
    public Produtor inserir(Produtor produtor) throws SQLException, ExcecoesConexao {
        try (Connection minhaConexao = new ConexaoFactory().conexao()) {
            produtor.setId(proximoId(minhaConexao));

            PreparedStatement stmt = minhaConexao.prepareStatement(
                    "INSERT INTO tb_produtor (cd_produtor, nm_produtor, nr_telefone, cd_municipio, " +
                    "ds_canal_preferido, ds_preferencia_leitura, nr_anos_experiencia, fl_recebe_ater, " +
                    "fl_assistido, fl_consentimento, dt_consentimento) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, 'N', ?, SYSDATE)");
            stmt.setInt(1, produtor.getId());
            stmt.setString(2, produtor.getNome());
            stmt.setString(3, produtor.getTelefone());
            stmt.setInt(4, produtor.getMunicipioId());
            stmt.setString(5, produtor.getCanalPreferido());
            stmt.setString(6, produtor.getPreferenciaLeitura());
            stmt.setInt(7, produtor.getAnosExperiencia());
            stmt.setString(8, produtor.isRecebeAter() ? "S" : "N");
            stmt.setString(9, produtor.isConsentimento() ? "S" : "N");

            stmt.executeUpdate();
            stmt.close();
            return produtor;
        }
    }

    // Select
    public List<Produtor> selecionar() throws SQLException, ExcecoesConexao {
        List<Produtor> listaProdutores = new ArrayList<>();
        try (Connection minhaConexao = new ConexaoFactory().conexao()) {
            PreparedStatement stmt = minhaConexao.prepareStatement(
                    "SELECT * FROM tb_produtor ORDER BY cd_produtor");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                listaProdutores.add(montar(rs));
            }
            rs.close();
            stmt.close();
        }
        return listaProdutores;
    }

    // Buscar por id (devolve null se nao existir)
    public Produtor buscarPorId(int id) throws SQLException, ExcecoesConexao {
        Produtor produtor = null;
        try (Connection minhaConexao = new ConexaoFactory().conexao()) {
            PreparedStatement stmt = minhaConexao.prepareStatement(
                    "SELECT * FROM tb_produtor WHERE cd_produtor = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                produtor = montar(rs);
            }
            rs.close();
            stmt.close();
        }
        return produtor;
    }

    // Verifica se o telefone ja e de outro produtor (RN02)
    public boolean telefoneEmUso(String telefone, int ignorarId) throws SQLException, ExcecoesConexao {
        boolean emUso;
        try (Connection minhaConexao = new ConexaoFactory().conexao()) {
            PreparedStatement stmt = minhaConexao.prepareStatement(
                    "SELECT COUNT(*) FROM tb_produtor WHERE nr_telefone = ? AND cd_produtor <> ?");
            stmt.setString(1, telefone);
            stmt.setInt(2, ignorarId);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            emUso = rs.getInt(1) > 0;
            rs.close();
            stmt.close();
        }
        return emUso;
    }

    // Update
    public void atualizar(Produtor produtor) throws SQLException, ExcecoesConexao {
        try (Connection minhaConexao = new ConexaoFactory().conexao()) {
            PreparedStatement stmt = minhaConexao.prepareStatement(
                    "UPDATE tb_produtor SET nm_produtor = ?, nr_telefone = ?, cd_municipio = ?, " +
                    "ds_canal_preferido = ?, ds_preferencia_leitura = ?, nr_anos_experiencia = ?, " +
                    "fl_recebe_ater = ? WHERE cd_produtor = ?");
            stmt.setString(1, produtor.getNome());
            stmt.setString(2, produtor.getTelefone());
            stmt.setInt(3, produtor.getMunicipioId());
            stmt.setString(4, produtor.getCanalPreferido());
            stmt.setString(5, produtor.getPreferenciaLeitura());
            stmt.setInt(6, produtor.getAnosExperiencia());
            stmt.setString(7, produtor.isRecebeAter() ? "S" : "N");
            stmt.setInt(8, produtor.getId());

            stmt.executeUpdate();
            stmt.close();
        }
    }

    // Delete
    public void deletar(int id) throws SQLException, ExcecoesConexao {
        try (Connection minhaConexao = new ConexaoFactory().conexao()) {
            PreparedStatement stmt = minhaConexao.prepareStatement(
                    "DELETE FROM tb_produtor WHERE cd_produtor = ?");
            stmt.setInt(1, id);

            stmt.executeUpdate();
            stmt.close();
        }
    }

    private int proximoId(Connection minhaConexao) throws SQLException {
        PreparedStatement stmt = minhaConexao.prepareStatement("SELECT sq_tb_produtor.NEXTVAL FROM dual");
        ResultSet rs = stmt.executeQuery();
        rs.next();
        int id = rs.getInt(1);
        rs.close();
        stmt.close();
        return id;
    }

    // Converte a linha do banco em objeto
    private Produtor montar(ResultSet rs) throws SQLException {
        Produtor produtor = new Produtor();
        produtor.setId(rs.getInt("cd_produtor"));
        produtor.setNome(rs.getString("nm_produtor"));
        produtor.setTelefone(rs.getString("nr_telefone"));
        produtor.setMunicipioId(rs.getInt("cd_municipio"));
        produtor.setCanalPreferido(rs.getString("ds_canal_preferido"));
        produtor.setPreferenciaLeitura(rs.getString("ds_preferencia_leitura"));
        produtor.setAnosExperiencia(rs.getInt("nr_anos_experiencia"));
        produtor.setRecebeAter("S".equals(rs.getString("fl_recebe_ater")));
        produtor.setConsentimento("S".equals(rs.getString("fl_consentimento")));
        return produtor;
    }
}
