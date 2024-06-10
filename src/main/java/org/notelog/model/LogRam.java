package org.notelog.model;

import com.github.britooo.looca.api.core.Looca;

public class LogRam extends LogAbstract {
    private Integer fkRAM;
    private String usoMemoria;
    private String memoriaDisponivel;

    public LogRam(Integer fkRAM) {
        Looca looca = new Looca();
        this.fkRAM = fkRAM;
        this.usoMemoria = looca.getMemoria().getEmUso().toString();
        this.memoriaDisponivel = looca.getMemoria().getDisponivel().toString();
    }


    public Integer getFkRAM() {
        return fkRAM;
    }

    public void setFkRAM(Integer fkRAM) {
        this.fkRAM = fkRAM;
    }

    public String getUsoMemoria() {
        return usoMemoria;
    }

    public void setUsoMemoria(String usoMemoria) {
        this.usoMemoria = usoMemoria;
    }

    public String getMemoriaDisponivel() {
        return memoriaDisponivel;
    }

    public void setMemoriaDisponivel(String memoriaDisponivel) {
        this.memoriaDisponivel = memoriaDisponivel;
    }

    @Override
    public String toString() {
        return "org.notelog.entidades.logs.ram.LogRAM{" +
                "idLogRAM=" + super.getId() +
                ", fkRAM=" + fkRAM +
                ", usoMemoria='" + usoMemoria + '\'' +
                ", memoriaDisponivel='" + memoriaDisponivel + '\'' +
                '}';
    }
}
