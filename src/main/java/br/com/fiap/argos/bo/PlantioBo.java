package br.com.fiap.argos.bo;

import br.com.fiap.argos.dao.PlantioDAO;
import br.com.fiap.argos.dto.PlantioDTO;
import br.com.fiap.argos.entities.Plantio;
import br.com.fiap.argos.entities.Propriedade;
import br.com.fiap.argos.exception.ExcecoesConexao;
import br.com.fiap.argos.exception.RecursoNaoEncontradoException;
import br.com.fiap.argos.exception.RegraNegocioException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

//criei o service porque senão eu precisaria instanciar os objetos, com o service, quem cria o objeto é o springboot
@Service
public class PlantioBo {

    private final PlantioDAO plantioDAO;
    private final PropriedadeBo propriedadeBo;

    public PlantioBo(PlantioDAO plantioDAO, PropriedadeBo propriedadeBo) {
        this.plantioDAO = plantioDAO;
        this.propriedadeBo = propriedadeBo;
    }

    // Converte DTO para Entity
    private Plantio converterParaPlantio(PlantioDTO dto, int propriedadeId) {
        return new Plantio(
                0,
                propriedadeId,
                dto.getCulturaId(),
                dto.getAreaHa(),
                dto.getDataPlantio(),
                dto.getDataColheitaPrevista(),
                dto.getEstagio()
        );
    }

    // Inserir um plantio na propriedade
    public Plantio inserirBo(int propriedadeId, PlantioDTO dto) throws SQLException, ExcecoesConexao {
        Propriedade propriedade = propriedadeBo.buscarPorIdBo(propriedadeId);
        validarArea(propriedade, dto, 0);
        Plantio plantio = converterParaPlantio(dto, propriedadeId);
        return plantioDAO.inserir(plantio);
    }

    // Selecionar todos
    public List<Plantio> selecionarBo() throws SQLException, ExcecoesConexao {
        return plantioDAO.selecionar();
    }

    // Selecionar os da propriedade
    public List<Plantio> selecionarPorPropriedadeBo(int propriedadeId) throws SQLException, ExcecoesConexao {
        propriedadeBo.buscarPorIdBo(propriedadeId);
        return plantioDAO.selecionarPorPropriedade(propriedadeId);
    }

    // Buscar por id
    public Plantio buscarPorIdBo(int id) throws SQLException, ExcecoesConexao {
        Plantio plantio = plantioDAO.buscarPorId(id);
        if (plantio == null) {
            throw new RecursoNaoEncontradoException("Plantio " + id + " não encontrado");
        }
        return plantio;
    }

    // Atualizar
    public Plantio atualizarBo(int id, PlantioDTO dto) throws SQLException, ExcecoesConexao {
        Plantio atual = buscarPorIdBo(id);
        Propriedade propriedade = propriedadeBo.buscarPorIdBo(atual.getPropriedadeId());
        validarArea(propriedade, dto, id);
        Plantio plantio = converterParaPlantio(dto, atual.getPropriedadeId());
        plantio.setId(id);
        plantioDAO.atualizar(plantio);
        return plantio;
    }

    // Deletar
    public void deletarBo(int id) throws SQLException, ExcecoesConexao {
        buscarPorIdBo(id);
        plantioDAO.deletar(id);
    }

    // Regra de negocio : a soma das areas plantadas nao pode passar da area da propriedade.
    // Plantio ja colhido, se já estiver em colhido nao ocupa mais a terra.
    private void validarArea(Propriedade propriedade, PlantioDTO dto, int ignorarId) throws SQLException, ExcecoesConexao {
        if ("COLHEITA".equals(dto.getEstagio())) {
            return;
        }
        double jaPlantada = plantioDAO.somarAreaPlantada(propriedade.getId(), ignorarId);
        double livre = propriedade.getAreaHa() - jaPlantada;
        if (dto.getAreaHa() > livre + 0.001) {
            throw new RegraNegocioException("A área plantada passa da área da propriedade. Área livre: "
                    + String.format("%.2f", livre) + " ha");
        }
    }
}
