package br.com.fiap.argos.entities;

public class Propriedade {

    //encapsulamento, dados e atributos
    private int id;
    private int produtorId;
    private int municipioId;
    private String nome;
    private double areaHa;
    private String fonteAgua;
    private String irrigacao;

    //construtor vazio
    public Propriedade() {
        super();
    }

    //construtor cheio
    public Propriedade(int id, int produtorId, int municipioId, String nome, double areaHa, String fonteAgua, String irrigacao) {
        super();
        this.id = id;
        this.produtorId = produtorId;
        this.municipioId = municipioId;
        this.nome = nome;
        this.areaHa = areaHa;
        this.fonteAgua = fonteAgua;
        this.irrigacao = irrigacao;
    }

    //getter e setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProdutorId() {
        return produtorId;
    }

    public void setProdutorId(int produtorId) {
        this.produtorId = produtorId;
    }

    public int getMunicipioId() {
        return municipioId;
    }

    public void setMunicipioId(int municipioId) {
        this.municipioId = municipioId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getAreaHa() {
        return areaHa;
    }

    public void setAreaHa(double areaHa) {
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

    //To String
    @Override
    public String toString() {
        return "Propriedade{" +
                "id=" + id +
                ", produtorId=" + produtorId +
                ", municipioId=" + municipioId +
                ", nome='" + nome + '\'' +
                ", areaHa=" + areaHa +
                ", fonteAgua='" + fonteAgua + '\'' +
                ", irrigacao='" + irrigacao + '\'' +
                '}';
    }
}
