package br.com.fiap.argos.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public class ProdutorDTO {

    @NotBlank(message = "O nome é obrigatório")
    @Size(max = 80, message = "O nome deve ter no máximo 80 caracteres")
    private String nome;

    @NotBlank(message = "O telefone é obrigatório")
    @Pattern(regexp = "\\d{10,11}", message = "O telefone deve ter 10 ou 11 números")
    private String telefone;

    @NotNull(message = "O município é obrigatório")
    private Integer municipioId;

    @NotBlank(message = "O canal preferido é obrigatório")
    @Pattern(regexp = "APP|AUDIO|SMS|WHATSAPP", message = "O canal deve ser APP, AUDIO, SMS ou WHATSAPP")
    private String canalPreferido;

    @NotBlank(message = "A preferência de leitura é obrigatória")
    @Pattern(regexp = "OUVIR|LER|AMBOS", message = "A preferência deve ser OUVIR, LER ou AMBOS")
    private String preferenciaLeitura;

    @PositiveOrZero(message = "Os anos de experiência não podem ser negativos")
    private Integer anosExperiencia;

    @NotNull(message = "Informe se recebe assistência técnica")
    private Boolean recebeAter;

    @NotNull(message = "O consentimento é obrigatório")
    @AssertTrue(message = "Sem consentimento não há cadastro")
    private Boolean consentimento;

    public ProdutorDTO() {
        super();
    }

    public ProdutorDTO(String nome, String telefone, Integer municipioId, String canalPreferido, String preferenciaLeitura, Integer anosExperiencia, Boolean recebeAter, Boolean consentimento) {
        super();
        this.nome = nome;
        this.telefone = telefone;
        this.municipioId = municipioId;
        this.canalPreferido = canalPreferido;
        this.preferenciaLeitura = preferenciaLeitura;
        this.anosExperiencia = anosExperiencia;
        this.recebeAter = recebeAter;
        this.consentimento = consentimento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Integer getMunicipioId() {
        return municipioId;
    }

    public void setMunicipioId(Integer municipioId) {
        this.municipioId = municipioId;
    }

    public String getCanalPreferido() {
        return canalPreferido;
    }

    public void setCanalPreferido(String canalPreferido) {
        this.canalPreferido = canalPreferido;
    }

    public String getPreferenciaLeitura() {
        return preferenciaLeitura;
    }

    public void setPreferenciaLeitura(String preferenciaLeitura) {
        this.preferenciaLeitura = preferenciaLeitura;
    }

    public Integer getAnosExperiencia() {
        return anosExperiencia;
    }

    public void setAnosExperiencia(Integer anosExperiencia) {
        this.anosExperiencia = anosExperiencia;
    }

    public Boolean getRecebeAter() {
        return recebeAter;
    }

    public void setRecebeAter(Boolean recebeAter) {
        this.recebeAter = recebeAter;
    }

    public Boolean getConsentimento() {
        return consentimento;
    }

    public void setConsentimento(Boolean consentimento) {
        this.consentimento = consentimento;
    }
}
