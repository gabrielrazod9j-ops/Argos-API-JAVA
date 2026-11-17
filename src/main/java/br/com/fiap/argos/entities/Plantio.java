package br.com.fiap.argos.entities;

import java.time.LocalDate;

public class Plantio {

    //encapsulamento,dados e atributos
    private int id;
    private int propriedadeId;
    private int culturaId;
    private double areaHa;
    private LocalDate dataPlantio;
    private LocalDate dataColheitaPrevista;
    private String estagio;

    //construtor vazio
    public Plantio() {
        super();
    }

    //construtor cheio
    public Plantio(int id, int propriedadeId, int culturaId, double areaHa, LocalDate dataPlantio, LocalDate dataColheitaPrevista, String estagio) {
        super();
        this.id = id;
        this.propriedadeId = propriedadeId;
        this.culturaId = culturaId;
        this.areaHa = areaHa;
        this.dataPlantio = dataPlantio;
        this.dataColheitaPrevista = dataColheitaPrevista;
        this.estagio = estagio;
    }

    //getter e setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPropriedadeId() {
        return propriedadeId;
    }

    public void setPropriedadeId(int propriedadeId) {
        this.propriedadeId = propriedadeId;
    }

    public int getCulturaId() {
        return culturaId;
    }

    public void setCulturaId(int culturaId) {
        this.culturaId = culturaId;
    }

    public double getAreaHa() {
        return areaHa;
    }

    public void setAreaHa(double areaHa) {
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

    //to string
    @Override
    public String toString() {
        return "Plantio{" +
                "id=" + id +
                ", propriedadeId=" + propriedadeId +
                ", culturaId=" + culturaId +
                ", areaHa=" + areaHa +
                ", dataPlantio=" + dataPlantio +
                ", dataColheitaPrevista=" + dataColheitaPrevista +
                ", estagio='" + estagio + '\'' +
                '}';
    }
}
