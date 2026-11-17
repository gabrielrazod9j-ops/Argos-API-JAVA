package br.com.fiap.argos.controller;

import br.com.fiap.argos.bo.ProdutorBo;
import br.com.fiap.argos.bo.PropriedadeBo;
import br.com.fiap.argos.dto.ProdutorDTO;
import br.com.fiap.argos.dto.PropriedadeDTO;
import br.com.fiap.argos.entities.Produtor;
import br.com.fiap.argos.entities.Propriedade;
import br.com.fiap.argos.exception.ExcecoesConexao;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/produtores")
public class ProdutorController {

    private final ProdutorBo produtorBo;
    private final PropriedadeBo propriedadeBo;

    public ProdutorController(ProdutorBo produtorBo, PropriedadeBo propriedadeBo) {
        this.produtorBo = produtorBo;
        this.propriedadeBo = propriedadeBo;
    }

    // Inserir : 201
    @PostMapping
    public ResponseEntity<Produtor> inserirRs(@Valid @RequestBody ProdutorDTO dto) throws SQLException, ExcecoesConexao {
        Produtor novo = produtorBo.inserirBo(dto);
        URI location = URI.create("/produtores/" + novo.getId());
        return ResponseEntity.created(location).body(novo);
    }

    // Selecionar : 200
    @GetMapping
    public ResponseEntity<List<Produtor>> selecionarRs() throws SQLException, ExcecoesConexao {
        return ResponseEntity.ok(produtorBo.selecionarBo());
    }

    // Buscar por id : 200 ou 404
    @GetMapping("/{id}")
    public ResponseEntity<Produtor> buscarPorIdRs(@PathVariable int id) throws SQLException, ExcecoesConexao {
        return ResponseEntity.ok(produtorBo.buscarPorIdBo(id));
    }

    // Atualizar : 200, 400, 404 ou 409
    @PutMapping("/{id}")
    public ResponseEntity<Produtor> atualizarRs(@PathVariable int id, @Valid @RequestBody ProdutorDTO dto)
            throws SQLException, ExcecoesConexao {
        return ResponseEntity.ok(produtorBo.atualizarBo(id, dto));
    }

    // Deletar : 204, 404 ou 409
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarRs(@PathVariable int id) throws SQLException, ExcecoesConexao {
        produtorBo.deletarBo(id);
        return ResponseEntity.noContent().build();
    }

    // Rota do 1:N:(um produtor tem varias propriedades)
    @GetMapping("/{id}/propriedades") //lista só as propriedades do produtor informado.
    public ResponseEntity<List<Propriedade>> selecionarPropriedadesRs(@PathVariable int id) throws SQLException, ExcecoesConexao {
        return ResponseEntity.ok(propriedadeBo.selecionarPorProdutorBo(id)); // se não existir retorna 404
    }

    // Rota do 1:N: cadastra uma propriedade dentro do produtor {id}
    @PostMapping("/{id}/propriedades") // os dados vem do corpo PropriedadeDTO e validado pelo @valid
    public ResponseEntity<Propriedade> inserirPropriedadeRs(@PathVariable int id,
                                                            @Valid @RequestBody PropriedadeDTO dto)
            throws SQLException, ExcecoesConexao {
        Propriedade nova = propriedadeBo.inserirBo(id, dto);
        URI location = URI.create("/propriedades/" + nova.getId()); //o id do produtor vem da url
        return ResponseEntity.created(location).body(nova);
    }
}
