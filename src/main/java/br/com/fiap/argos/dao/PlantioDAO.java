package br.com.fiap.argos.dao;

import br.com.fiap.argos.entities.Plantio;
import br.com.fiap.argos.conexoes.ConexaoFactory;
import br.com.fiap.argos.exception.ExcecoesConexao;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PlantioDAO {

    // Insert (o id vem da sequence sq_tb_plantio)
    public Plantio inserir(Plantio plantio) throws SQLException, ExcecoesConexao {
        try (Connection minhaConexao = new ConexaoFactory().conexao()) {
            PreparedStatement seq = minhaConexao.prepareStatement("SELECT sq_tb_plantio.NEXTVAL FROM dual");
            ResultSet rs = seq.executeQuery();
            rs.next();
            plantio.setId(rs.getInt(1));
            rs.close();
            seq.close();

            PreparedStatement stmt = minhaConexao.prepareStatement(
                    "INSERT INTO tb_plantio (cd_plantio, cd_propriedade, cd_cultura, nr_area_ha, dt_plantio, " +
                    "dt_colheita_prevista, ds_estagio) VALUES (?, ?, ?, ?, ?, ?, ?)");
            stmt.setInt(1, plantio.getId());
            stmt.setInt(2, plantio.getPropriedadeId());
            stmt.setInt(3, plantio.getCulturaId());
            stmt.setDouble(4, plantio.getAreaHa());
            stmt.setDate(5, Date.valueOf(plantio.getDataPlantio()));
            stmt.setDate(6, Date.valueOf(plantio.getDataColheitaPrevista()));
            stmt.setString(7, plantio.getEstagio());

            stmt.executeUpdate();
            stmt.close();
            return plantio;
        }
    }

    // Select de todos
    public List<Plantio> selecionar() throws SQLException, ExcecoesConexao {
        return consultar("SELECT * FROM tb_plantio ORDER BY cd_plantio", null);
    }

    // Select dos plantios de uma propriedade (rota /propriedades/{id}/plantios)
    public List<Plantio> selecionarPorPropriedade(int propriedadeId) throws SQLException, ExcecoesConexao {
        return consultar("SELECT * FROM tb_plantio WHERE cd_propriedade = ? ORDER BY cd_plantio", propriedadeId);
    }

    // Buscar por id (devolve null se nao existir)
    public Plantio buscarPorId(int id) throws SQLException, ExcecoesConexao {
        List<Plantio> lista = consultar("SELECT * FROM tb_plantio WHERE cd_plantio = ?", id);
        return lista.isEmpty() ? null : lista.get(0);
    }

    // regra de negocio:
    // soma a area dos plantios que ainda nao foram colhidos.
    // ignorarId tira da conta o plantio que esta sendo atualizado (no PUT).
    public double somarAreaPlantada(int propriedadeId, int ignorarId) throws SQLException, ExcecoesConexao {
        double soma;
        try (Connection minhaConexao = new ConexaoFactory().conexao()) {
            PreparedStatement stmt = minhaConexao.prepareStatement(
                    "SELECT NVL(SUM(nr_area_ha), 0) FROM tb_plantio " +
                    "WHERE cd_propriedade = ? AND ds_estagio <> 'COLHEITA' AND cd_plantio <> ?");
            stmt.setInt(1, propriedadeId);
            stmt.setInt(2, ignorarId);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            soma = rs.getDouble(1);
            rs.close();
            stmt.close();
        }
        return soma;
    }

    // Update (a propriedade dona nao muda)
    public void atualizar(Plantio plantio) throws SQLException, ExcecoesConexao {
        try (Connection minhaConexao = new ConexaoFactory().conexao()) {
            PreparedStatement stmt = minhaConexao.prepareStatement(
                    "UPDATE tb_plantio SET cd_cultura = ?, nr_area_ha = ?, dt_plantio = ?, " +
                    "dt_colheita_prevista = ?, ds_estagio = ? WHERE cd_plantio = ?");
            stmt.setInt(1, plantio.getCulturaId());
            stmt.setDouble(2, plantio.getAreaHa());
            stmt.setDate(3, Date.valueOf(plantio.getDataPlantio()));
            stmt.setDate(4, Date.valueOf(plantio.getDataColheitaPrevista()));
            stmt.setString(5, plantio.getEstagio());
            stmt.setInt(6, plantio.getId());

            stmt.executeUpdate();
            stmt.close();
        }
    }

    // Delete
    public void deletar(int id) throws SQLException, ExcecoesConexao {
        try (Connection minhaConexao = new ConexaoFactory().conexao()) {
            PreparedStatement stmt = minhaConexao.prepareStatement(
                    "DELETE FROM tb_plantio WHERE cd_plantio = ?");
            stmt.setInt(1, id);

            stmt.executeUpdate();
            stmt.close();
        }
    }

    //transforma cada linha do banco em objeto, foi feio para não repetir o codigo acima denovo
    private List<Plantio> consultar(String sql, Integer parametro) throws SQLException, ExcecoesConexao {
        List<Plantio> lista = new ArrayList<>();
        try (Connection minhaConexao = new ConexaoFactory().conexao()) {
            PreparedStatement stmt = minhaConexao.prepareStatement(sql);
            if (parametro != null) {
                stmt.setInt(1, parametro);
            }
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Plantio plantio = new Plantio();
                plantio.setId(rs.getInt("cd_plantio"));
                plantio.setPropriedadeId(rs.getInt("cd_propriedade"));
                plantio.setCulturaId(rs.getInt("cd_cultura"));
                plantio.setAreaHa(rs.getDouble("nr_area_ha"));
                plantio.setDataPlantio(rs.getDate("dt_plantio").toLocalDate());
                plantio.setDataColheitaPrevista(rs.getDate("dt_colheita_prevista").toLocalDate());
                plantio.setEstagio(rs.getString("ds_estagio"));
                lista.add(plantio);
            }
            rs.close();
            stmt.close();
        }
        return lista;
    }
}
