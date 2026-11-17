package br.com.fiap.argos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class PropriedadeDTO {

    @NotBlank(message = "O nome da propriedade é obrigatório")
    @Size(max = 80, message = "O nome deve ter no máximo 80 caracteres")
    private String nome;

    @NotNull(message = "O município é obrigatório")
    private Integer municipioId;

    @NotNull(message = "A área é obrigatória")
    @Positive(message = "A área deve ser maior que zero")
    private Double areaHa;

    @Pattern(regexp = "CHUVA|POCO|RIO|ACUDE|CISTERNA|REDE", message = "Fonte de água inválida")
    private String fonteAgua;

    @Pattern(regexp = "NAO|GOTEJAMENTO|ASPERSAO|OUTRA", message = "Irrigação inválida")
    private String irrigacao;

    public PropriedadeDTO() {
        super();
    }

    public PropriedadeDTO(String nome, Integer municipioId, Double areaHa, String fonteAgua, String irrigacao) {
        super();
        this.nome = nome;
        this.municipioId = municipioId;
        this.areaHa = areaHa;
        this.fonteAgua = fonteAgua;
        this.irrigacao = irrigacao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getMunicipioId() {
        return municipioId;
    }

    public void setMunicipioId(Integer municipioId) {
        this.municipioId = municipioId;
    }

    public Double getAreaHa() {
        return areaHa;
    }

    public void setAreaHa(Double areaHa) {
        this.areaHa = areaHa;
    }

    public String getFonteAgua() {
        return fonteAgua;
    }

    public void setFonteAgua(String fonteAgua) {
        this.fonteAgua = fonteAgua;
    }

    public String getIrrigacao() {
        return irrigacao;
    }

    public void setIrrigacao(String irrigacao) {
        this.irrigacao = irrigacao;
    }
}
