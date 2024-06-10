package org.notelog.model;

import com.github.britooo.looca.api.core.Looca;

public class LogCpu extends LogAbstract {
    private Double porcentagemUso;
    private Integer fkCPU;

    public LogCpu(Integer fkCPU)
    {
        Looca looca = new Looca();
        this.porcentagemUso = looca.getProcessador().getUso();
        this.fkCPU = fkCPU;
    }

    public Double getPorcentagemUso() {
        return porcentagemUso;
    }

    public void setPorcentagemUso(Double porcentagemUso) {
        this.porcentagemUso = porcentagemUso;
    }

    public Integer getFkCPU() {
        return fkCPU;
    }

    public void setFkCPU(Integer fkCPU) {
        this.fkCPU = fkCPU;
    }


    @Override
    public String toString() {
        return "org.notelog.entidades.log.cpu.LogCPU{" +
                "idLogCPU=" + super.getId() +
                ", porcentagemUso='" + porcentagemUso + '\'' +
                ", fkCPU=" + fkCPU +
                '}';
    }
}
