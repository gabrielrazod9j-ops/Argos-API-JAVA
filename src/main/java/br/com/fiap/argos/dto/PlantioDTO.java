package br.com.fiap.argos.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public class PlantioDTO {

    @NotNull(message = "A cultura é obrigatória")
    private Integer culturaId;

    @NotNull(message = "A área é obrigatória")
    @Positive(message = "A área deve ser maior que zero")
    private Double areaHa;

    @NotNull(message = "A data do plantio é obrigatória")
    @PastOrPresent(message = "A data do plantio deve ser hoje ou no passado")
    private LocalDate dataPlantio;

    @NotNull(message = "A data da colheita é obrigatória")
    @Future(message = "A data da colheita deve ser no futuro")
    private LocalDate dataColheitaPrevista;

    @NotBlank(message = "O estágio é obrigatório")
    @Pattern(regexp = "PREPARO|PLANTIO|CRESCIMENTO|FLORACAO|COLHEITA", message = "Estágio inválido")
    private String estagio;

    public PlantioDTO() {
        super();
    }

    public PlantioDTO(Integer culturaId, Double areaHa, LocalDate dataPlantio, LocalDate dataColheitaPrevista, String estagio) {
        super();
        this.culturaId = culturaId;
        this.areaHa = areaHa;
        this.dataPlantio = dataPlantio;
        this.dataColheitaPrevista = dataColheitaPrevista;
        this.estagio = estagio;
    }

    public Integer getCulturaId() {
        return culturaId;
    }

    public void setCulturaId(Integer culturaId) {
        this.culturaId = culturaId;
    }

    public Double getAreaHa() {
        return areaHa;
    }

    public void setAreaHa(Double areaHa) {
        this.areaHa = areaHa;
    }

    public LocalDate getDataPlantio() {
        return dataPlantio;
    }

    public void setDataPlantio(LocalDate dataPlantio) {
        this.dataPlantio = dataPlantio;
    }

    public LocalDate getDataColheitaPrevista() {
        return dataColheitaPrevista;
    }

    public void setDataColheitaPrevista(LocalDate dataColheitaPrevista) {
        this.dataColheitaPrevista = dataColheitaPrevista;
    }

    public String getEstagio() {
        return estagio;
    }

    public void setEstagio(String estagio) {
        this.estagio = estagio;
    }
}
