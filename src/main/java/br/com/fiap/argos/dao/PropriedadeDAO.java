package br.com.fiap.argos.dao;

import br.com.fiap.argos.entities.Propriedade;
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
public class PropriedadeDAO {

    // Insert (o id vem da sequence sq_tb_propriedade)
    public Propriedade inserir(Propriedade propriedade) throws SQLException, ExcecoesConexao {
        try (Connection minhaConexao = new ConexaoFactory().conexao()) {
            PreparedStatement seq = minhaConexao.prepareStatement("SELECT sq_tb_propriedade.NEXTVAL FROM dual");
            ResultSet rs = seq.executeQuery();
            rs.next();
            propriedade.setId(rs.getInt(1));
            rs.close();
            seq.close();

            PreparedStatement stmt = minhaConexao.prepareStatement(
                    "INSERT INTO tb_propriedade (cd_propriedade, cd_produtor, cd_municipio, nm_propriedade, " +
                    "nr_area_ha, ds_fonte_agua, ds_irrigacao) VALUES (?, ?, ?, ?, ?, ?, ?)");
            stmt.setInt(1, propriedade.getId());
            stmt.setInt(2, propriedade.getProdutorId());
            stmt.setInt(3, propriedade.getMunicipioId());
            stmt.setString(4, propriedade.getNome());
            stmt.setDouble(5, propriedade.getAreaHa());
            stmt.setString(6, propriedade.getFonteAgua());
            stmt.setString(7, propriedade.getIrrigacao());

            stmt.executeUpdate();
            stmt.close();
            return propriedade;
        }
    }

    // Select de todas
    public List<Propriedade> selecionar() throws SQLException, ExcecoesConexao {
        return consultar("SELECT * FROM tb_propriedade ORDER BY cd_propriedade", null);
    }

    // Select das propriedades de um produtor (rota /produtores/{id}/propriedades)
    public List<Propriedade> selecionarPorProdutor(int produtorId) throws SQLException, ExcecoesConexao {
        return consultar("SELECT * FROM tb_propriedade WHERE cd_produtor = ? ORDER BY cd_propriedade", produtorId);
    }

    // Buscar por id (devolve null se nao existir)
    public Propriedade buscarPorId(int id) throws SQLException, ExcecoesConexao {
        List<Propriedade> lista = consultar("SELECT * FROM tb_propriedade WHERE cd_propriedade = ?", id);
        return lista.isEmpty() ? null : lista.get(0);
    }

    // Update (o produtor dono nao muda)
    public void atualizar(Propriedade propriedade) throws SQLException, ExcecoesConexao {
        try (Connection minhaConexao = new ConexaoFactory().conexao()) {
            PreparedStatement stmt = minhaConexao.prepareStatement(
                    "UPDATE tb_propriedade SET cd_municipio = ?, nm_propriedade = ?, nr_area_ha = ?, " +
                    "ds_fonte_agua = ?, ds_irrigacao = ? WHERE cd_propriedade = ?");
            stmt.setInt(1, propriedade.getMunicipioId());
            stmt.setString(2, propriedade.getNome());
            stmt.setDouble(3, propriedade.getAreaHa());
            stmt.setString(4, propriedade.getFonteAgua());
            stmt.setString(5, propriedade.getIrrigacao());
            stmt.setInt(6, propriedade.getId());

            stmt.executeUpdate();
            stmt.close();
        }
    }

    // Delete
    public void deletar(int id) throws SQLException, ExcecoesConexao {
        try (Connection minhaConexao = new ConexaoFactory().conexao()) {
            PreparedStatement stmt = minhaConexao.prepareStatement(
                    "DELETE FROM tb_propriedade WHERE cd_propriedade = ?");
            stmt.setInt(1, id);

            stmt.executeUpdate();
            stmt.close();
        }
    }

    private List<Propriedade> consultar(String sql, Integer parametro) throws SQLException, ExcecoesConexao {
        List<Propriedade> lista = new ArrayList<>();
        try (Connection minhaConexao = new ConexaoFactory().conexao()) {
            PreparedStatement stmt = minhaConexao.prepareStatement(sql);
            if (parametro != null) {
                stmt.setInt(1, parametro);
            }
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Propriedade propriedade = new Propriedade();
                propriedade.setId(rs.getInt("cd_propriedade"));
                propriedade.setProdutorId(rs.getInt("cd_produtor"));
                propriedade.setMunicipioId(rs.getInt("cd_municipio"));
                propriedade.setNome(rs.getString("nm_propriedade"));
                propriedade.setAreaHa(rs.getDouble("nr_area_ha"));
                propriedade.setFonteAgua(rs.getString("ds_fonte_agua"));
                propriedade.setIrrigacao(rs.getString("ds_irrigacao"));
                lista.add(propriedade);
            }
            rs.close();
            stmt.close();
        }
        return lista;
    }
}
