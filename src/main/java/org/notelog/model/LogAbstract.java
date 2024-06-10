package org.notelog.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class LogAbstract {
    private Integer id;
    private String dataLog;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDataLog() {
        return dataLog;
    }

    public void setDataLog(String dataLog) {
        this.dataLog = dataLog;
    }


    public static String dataHoraAtual(){
        LocalDateTime dataHoraAtual = LocalDateTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dataHoraAtual.format(formato);
    }

}
