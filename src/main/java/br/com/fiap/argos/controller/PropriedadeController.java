package br.com.fiap.argos.controller;

import br.com.fiap.argos.bo.PlantioBo;
import br.com.fiap.argos.bo.PropriedadeBo;
import br.com.fiap.argos.dto.PropriedadeDTO;
import br.com.fiap.argos.dto.PlantioDTO;
import br.com.fiap.argos.entities.Plantio;
import br.com.fiap.argos.entities.Propriedade;
import br.com.fiap.argos.exception.ExcecoesConexao;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/propriedades")
public class PropriedadeController {

    private final PropriedadeBo propriedadeBo;
    private final PlantioBo plantioBo;

    public PropriedadeController(PropriedadeBo propriedadeBo, PlantioBo plantioBo) {
        this.propriedadeBo = propriedadeBo;
        this.plantioBo = plantioBo;
    }

    // Selecionar -> 200
    @GetMapping
    public ResponseEntity<List<Propriedade>> selecionarRs() throws SQLException, ExcecoesConexao {
        return ResponseEntity.ok(propriedadeBo.selecionarBo());
    }

    // Buscar por id -> 200 ou 404
    @GetMapping("/{id}")
    public ResponseEntity<Propriedade> buscarPorIdRs(@PathVariable int id) throws SQLException, ExcecoesConexao {
        return ResponseEntity.ok(propriedadeBo.buscarPorIdBo(id));
    }

    // Atualizar -> 200, 400, 404 ou 409
    @PutMapping("/{id}")
    public ResponseEntity<Propriedade> atualizarRs(@PathVariable int id, @Valid @RequestBody PropriedadeDTO dto)
            throws SQLException, ExcecoesConexao {
        return ResponseEntity.ok(propriedadeBo.atualizarBo(id, dto));
    }

    // Deletar -> 204, 404 ou 409
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarRs(@PathVariable int id) throws SQLException, ExcecoesConexao {
        propriedadeBo.deletarBo(id);
        return ResponseEntity.noContent().build();
    }

    // Rota do 1:N(uma propriedade tem varios plantios)
    //se a propriedade não existir, o bo lança RecursoNaoEncontradoExceptios.
    @GetMapping("/{id}/plantios") //lista só os plantios da propriedade informada.
    public ResponseEntity<List<Plantio>> selecionarPlantiosRs(@PathVariable int id) throws SQLException, ExcecoesConexao {
        return ResponseEntity.ok(plantioBo.selecionarPorPropriedadeBo(id));
    }

    // Rota do 1:N(cadastra um plantio dentro da propriedade)
    @PostMapping("/{id}/plantios") //cadastra um plantio pelo id
    public ResponseEntity<Plantio> inserirPlantioRs(@PathVariable int id, @Valid @RequestBody PlantioDTO dto)
            throws SQLException, ExcecoesConexao {
        Plantio novo = plantioBo.inserirBo(id, dto);
        URI location = URI.create("/plantios/" + novo.getId()); //o id da propriedade vem da url
        return ResponseEntity.created(location).body(novo);
    }
}
