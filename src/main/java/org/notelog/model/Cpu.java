package org.notelog.model;

import com.github.britooo.looca.api.core.Looca;

public class Cpu {
    private Integer id;
    private String nome;
    private Integer numeroFisico;
    private Integer numerologico;
    private String frequencia;
    private String idFisicoProcessador;
    private Integer fkNotebook;

    public Cpu(Integer fkNotebook) {
        Looca looca = new Looca();
        this.nome = looca.getProcessador().getNome();
        this.numeroFisico = looca.getProcessador().getNumeroCpusFisicas();
        this.numerologico = looca.getProcessador().getNumeroCpusLogicas();
        this.frequencia = looca.getProcessador().getFrequencia().toString();
        this.idFisicoProcessador = looca.getProcessador().getId();
        this.fkNotebook = fkNotebook;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getNumeroFisico() {
        return numeroFisico;
    }

    public void setNumeroFisico(Integer numeroFisico) {
        this.numeroFisico = numeroFisico;
    }

    public Integer getNumerologico() {
        return numerologico;
    }

    public void setNumerologico(Integer numerologico) {
        this.numerologico = numerologico;
    }

    public String getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(String frequencia) {
        this.frequencia = frequencia;
    }

    public String getIdFisicoProcessador() {
        return idFisicoProcessador;
    }

    public void setIdFisicoProcessador(String idFisicoProcessador) {
        this.idFisicoProcessador = idFisicoProcessador;
    }

    public Integer getFkNotebook() {
        return fkNotebook;
    }

    public void setFkNotebook(Integer fkNotebook) {
        this.fkNotebook = fkNotebook;
    }

    @Override
    public String toString() {
        return "Cpu{" +
                ", nome='" + nome + '\'' +
                ", numeroFisico=" + numeroFisico +
                ", numerologico=" + numerologico +
                ", frequencia='" + frequencia + '\'' +
                ", idFisicoProcessador='" + idFisicoProcessador + '\'' +
                ", fkNotebook='" + fkNotebook + '\'' +
                '}';
    }
}
