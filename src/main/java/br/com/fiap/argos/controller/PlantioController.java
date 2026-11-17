package br.com.fiap.argos.controller;

import br.com.fiap.argos.bo.PlantioBo;
import br.com.fiap.argos.dto.PlantioDTO;
import br.com.fiap.argos.entities.Plantio;
import br.com.fiap.argos.exception.ExcecoesConexao;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/plantios")
public class PlantioController {

    private final PlantioBo plantioBo;

    public PlantioController(PlantioBo plantioBo) {
        this.plantioBo = plantioBo;
    }

    // Selecionar : 200
    @GetMapping
    public ResponseEntity<List<Plantio>> selecionarRs() throws SQLException, ExcecoesConexao {
        return ResponseEntity.ok(plantioBo.selecionarBo());
    }

    // Buscar por id : 200 ou 404
    @GetMapping("/{id}")
    public ResponseEntity<Plantio> buscarPorIdRs(@PathVariable int id) throws SQLException, ExcecoesConexao {
        return ResponseEntity.ok(plantioBo.buscarPorIdBo(id));
    }

    // Atualizar : 200, 400, 404 ou 409
    @PutMapping("/{id}")
    public ResponseEntity<Plantio> atualizarRs(@PathVariable int id, @Valid @RequestBody PlantioDTO dto)
            throws SQLException, ExcecoesConexao {
        return ResponseEntity.ok(plantioBo.atualizarBo(id, dto));
    }

    // Deletar : 204 ou 404
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarRs(@PathVariable int id) throws SQLException, ExcecoesConexao {
        plantioBo.deletarBo(id);
        return ResponseEntity.noContent().build();
    }
}
