package br.com.fiap.argos.entities;

public class Produtor {

    //encapsulamento, dados e atributos
    private int id;
    private String nome;
    private String telefone;
    private int municipioId;
    private String canalPreferido;
    private String preferenciaLeitura;
    private int anosExperiencia;
    private boolean recebeAter;
    private boolean consentimento;

    //construtor vazio
    public Produtor() {
        super();
    }

    //construtor cheio
    public Produtor(int id, String nome, String telefone, int municipioId, String canalPreferido, String preferenciaLeitura, int anosExperiencia, boolean recebeAter, boolean consentimento) {
        super();
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.municipioId = municipioId;
        this.canalPreferido = canalPreferido;
        this.preferenciaLeitura = preferenciaLeitura;
        this.anosExperiencia = anosExperiencia;
        this.recebeAter = recebeAter;
        this.consentimento = consentimento;
    }

    //getter e setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getMunicipioId() {
        return municipioId;
    }

    public void setMunicipioId(int municipioId) {
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

    public int getAnosExperiencia() {
        return anosExperiencia;
    }

    public void setAnosExperiencia(int anosExperiencia) {
        this.anosExperiencia = anosExperiencia;
    }

    public boolean isRecebeAter() {
        return recebeAter;
    }

    public void setRecebeAter(boolean recebeAter) {
        this.recebeAter = recebeAter;
    }

    public boolean isConsentimento() {
        return consentimento;
    }

    public void setConsentimento(boolean consentimento) {
        this.consentimento = consentimento;
    }

    //To String
    @Override
    public String toString() {
        return "Produtor{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", telefone='" + telefone + '\'' +
                ", municipioId=" + municipioId +
                ", canalPreferido='" + canalPreferido + '\'' +
                ", preferenciaLeitura='" + preferenciaLeitura + '\'' +
                ", anosExperiencia=" + anosExperiencia +
                ", recebeAter=" + recebeAter +
                ", consentimento=" + consentimento +
                '}';
    }
}
