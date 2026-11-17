package br.com.fiap.argos.bo;

import br.com.fiap.argos.dao.ProdutorDAO;
import br.com.fiap.argos.dao.PropriedadeDAO;
import br.com.fiap.argos.dto.ProdutorDTO;
import br.com.fiap.argos.entities.Produtor;
import br.com.fiap.argos.exception.ExcecoesConexao;
import br.com.fiap.argos.exception.RecursoNaoEncontradoException;
import br.com.fiap.argos.exception.RegraNegocioException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

//criei o service porque senão eu precisaria instanciar os objetos, com o service, quem cria o objeto é o springboot
@Service
public class ProdutorBo {

    private final ProdutorDAO produtorDAO;
    private final PropriedadeDAO propriedadeDAO;

    public ProdutorBo(ProdutorDAO produtorDAO, PropriedadeDAO propriedadeDAO) {
        this.produtorDAO = produtorDAO;
        this.propriedadeDAO = propriedadeDAO;
    }

    // Converte DTO para Entity
    private Produtor converterParaProdutor(ProdutorDTO dto) {
        return new Produtor(
                0,
                dto.getNome(),
                dto.getTelefone(),
                dto.getMunicipioId(),
                dto.getCanalPreferido(),
                dto.getPreferenciaLeitura(),
                dto.getAnosExperiencia() == null ? 0 : dto.getAnosExperiencia(),
                dto.getRecebeAter(),
                dto.getConsentimento()
        );
    }

    // Inserir
    public Produtor inserirBo(ProdutorDTO dto) throws SQLException, ExcecoesConexao {
        // Regra de negocio (RN02): telefone unico
        if (produtorDAO.telefoneEmUso(dto.getTelefone(), 0)) {
            throw new RegraNegocioException("Já existe um produtor com o telefone " + dto.getTelefone());
        }
        Produtor produtor = converterParaProdutor(dto);
        return produtorDAO.inserir(produtor);
    }

    // Selecionar
    public List<Produtor> selecionarBo() throws SQLException, ExcecoesConexao {
        return produtorDAO.selecionar();
    }

    // Buscar por id
    public Produtor buscarPorIdBo(int id) throws SQLException, ExcecoesConexao {
        Produtor produtor = produtorDAO.buscarPorId(id);
        if (produtor == null) {
            throw new RecursoNaoEncontradoException("Produtor " + id + " não encontrado");
        }
        return produtor;
    }

    // Atualizar
    public Produtor atualizarBo(int id, ProdutorDTO dto) throws SQLException, ExcecoesConexao {
        buscarPorIdBo(id);
        // Regra de negocio: o telefone nao pode ser de outro produtor
        if (produtorDAO.telefoneEmUso(dto.getTelefone(), id)) {
            throw new RegraNegocioException("Já existe um produtor com o telefone " + dto.getTelefone());
        }
        Produtor produtor = converterParaProdutor(dto);
        produtor.setId(id);
        produtorDAO.atualizar(produtor);
        return produtor;
    }

    // Deletar
    public void deletarBo(int id) throws SQLException, ExcecoesConexao {
        buscarPorIdBo(id);
        // Regra de negocio: produtor com propriedades nao pode ser removido
        if (!propriedadeDAO.selecionarPorProdutor(id).isEmpty()) {
            throw new RegraNegocioException("O produtor tem propriedades cadastradas e não pode ser removido");
        }
        produtorDAO.deletar(id);
    }
}
