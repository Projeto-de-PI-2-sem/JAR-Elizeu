package org.notelog.model;

import com.github.britooo.looca.api.core.Looca;

public class Ram {
    private Integer id;
    private String totalMemoria;
    private Integer fkNotebook;

    public Ram(Integer fkNotebook)
    {
        Looca looca = new Looca();
        this.totalMemoria = looca.getMemoria().getTotal().toString();
        this.fkNotebook = fkNotebook;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTotalMemoria() {
        return totalMemoria;
    }

    public void setTotalMemoria(String totalMemoria) {
        this.totalMemoria = totalMemoria;
    }

    public Integer getFkNotebook() {
        return fkNotebook;
    }

    public void setFkNotebook(Integer fkNotebook) {
        this.fkNotebook = fkNotebook;
    }

    @Override
    public String toString() {
        return "Ram{" +
                "totalMemoria='" + totalMemoria + '\'' +
                '}';
    }
}
