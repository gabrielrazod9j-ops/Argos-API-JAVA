package br.com.fiap.argos.bo;

import br.com.fiap.argos.dao.PlantioDAO;
import br.com.fiap.argos.dao.PropriedadeDAO;
import br.com.fiap.argos.dto.PropriedadeDTO;
import br.com.fiap.argos.entities.Propriedade;
import br.com.fiap.argos.exception.ExcecoesConexao;
import br.com.fiap.argos.exception.RecursoNaoEncontradoException;
import br.com.fiap.argos.exception.RegraNegocioException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

//criei o service porque senão eu precisaria instanciar os objetos, com o service, quem cria o objeto é o springboot
@Service
public class PropriedadeBo {

    private final PropriedadeDAO propriedadeDAO;
    private final PlantioDAO plantioDAO;
    private final ProdutorBo produtorBo;

    public PropriedadeBo(PropriedadeDAO propriedadeDAO, PlantioDAO plantioDAO, ProdutorBo produtorBo) {
        this.propriedadeDAO = propriedadeDAO;
        this.plantioDAO = plantioDAO;
        this.produtorBo = produtorBo;
    }

    // Converte DTO para Entity
    private Propriedade converterParaPropriedade(PropriedadeDTO dto, int produtorId) {
        return new Propriedade(
                0,
                produtorId,
                dto.getMunicipioId(),
                dto.getNome(),
                dto.getAreaHa(),
                dto.getFonteAgua(),
                dto.getIrrigacao()
        );
    }

    // Inserir uma propriedade para o produtor (POST /produtores/{id}/propriedades)
    public Propriedade inserirBo(int produtorId, PropriedadeDTO dto) throws SQLException, ExcecoesConexao {
        produtorBo.buscarPorIdBo(produtorId);
        Propriedade propriedade = converterParaPropriedade(dto, produtorId);
        return propriedadeDAO.inserir(propriedade);
    }

    // Selecionar todas
    public List<Propriedade> selecionarBo() throws SQLException, ExcecoesConexao {
        return propriedadeDAO.selecionar();
    }

    // Selecionar as do produtor (GET /produtores/{id}/propriedades)
    public List<Propriedade> selecionarPorProdutorBo(int produtorId) throws SQLException, ExcecoesConexao {
        produtorBo.buscarPorIdBo(produtorId);
        return propriedadeDAO.selecionarPorProdutor(produtorId);
    }

    // Buscar por id
    public Propriedade buscarPorIdBo(int id) throws SQLException, ExcecoesConexao {
        Propriedade propriedade = propriedadeDAO.buscarPorId(id);
        if (propriedade == null) {
            throw new RecursoNaoEncontradoException("Propriedade " + id + " não encontrada");
        }
        return propriedade;
    }

    // Atualizar
    public Propriedade atualizarBo(int id, PropriedadeDTO dto) throws SQLException, ExcecoesConexao {
        Propriedade atual = buscarPorIdBo(id);
        // Regra de negocio: a area nova nao pode ser menor que a area ja plantada
        double areaPlantada = plantioDAO.somarAreaPlantada(id, 0);
        if (dto.getAreaHa() < areaPlantada) {
            throw new RegraNegocioException("A área nova é menor que a área já plantada (" + areaPlantada + " ha)");
        }
        Propriedade propriedade = converterParaPropriedade(dto, atual.getProdutorId());
        propriedade.setId(id);
        propriedadeDAO.atualizar(propriedade);
        return propriedade;
    }

    // Deletar
    public void deletarBo(int id) throws SQLException, ExcecoesConexao {
        buscarPorIdBo(id);
        // Regra de negocio: propriedade com plantios nao pode ser removida
        if (!plantioDAO.selecionarPorPropriedade(id).isEmpty()) {
            throw new RegraNegocioException("A propriedade tem plantios cadastrados e não pode ser removida");
        }
        propriedadeDAO.deletar(id);
    }
}
