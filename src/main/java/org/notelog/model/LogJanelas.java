package org.notelog.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LogJanelas extends LogAbstract {
    private String idJanela;
    private String nomeJanela;
    private Integer bloqueado;
    private Integer fkNotebook;

    // Construtor que inicializa todos os campos
    public LogJanelas(Integer id, String idJanela, String nomeJanela, Integer bloqueado, Integer fkNotebook) {
        super.setId(id);
        this.idJanela = idJanela;
        this.nomeJanela = nomeJanela;
        this.bloqueado = bloqueado;
        this.fkNotebook = fkNotebook;
    }

    // Construtor padrão
    public LogJanelas() {
    }

    // Método para encerrar o processo com base no PID
    public void encerraProcesso(Integer pid) {
        try {
            String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                // Encerra o processo no Windows usando o comando taskkill
                Runtime.getRuntime().exec("taskkill /F /PID " + pid).waitFor();
            } else {
                // Encerra o processo no Linux usando o comando kill
                Runtime.getRuntime().exec("sudo kill -9 " + pid).waitFor();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static boolean verificaProcessoEmExecucao(Integer pid) {
        String os = System.getProperty("os.name").toLowerCase();
        String command = "";

        if (os.contains("win")) {
            command = "tasklist /FI \"PID eq " + pid + "\"";
        } else if (os.contains("nix") || os.contains("nux")) {
            command = "ps -p " + pid;
        } else {
            throw new UnsupportedOperationException("Sistema operacional não suportado");
        }

        try {
            ProcessBuilder builder = new ProcessBuilder();
            if (os.contains("win")) {
                builder.command("cmd.exe", "/c", command);
            } else {
                builder.command("sh", "-c", command);
            }

            Process process = builder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            boolean pidFound = false;

            if (os.contains("win")) {
                while ((line = reader.readLine()) != null) {
                    if (line.contains(String.valueOf(pid))) {
                        pidFound = true;
                        break;
                    }
                }
            } else {
                reader.readLine(); // Skip the header line
                if ((line = reader.readLine()) != null && line.trim().startsWith(String.valueOf(pid))) {
                    pidFound = true;
                }
            }

            reader.close();
            return pidFound;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Getters e Setters
    public Integer getBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(Integer bloqueado) {
        this.bloqueado = bloqueado;
    }

    public String getIdJanela() {
        return idJanela;
    }

    public void setIdJanela(String idJanela) {
        this.idJanela = idJanela;
    }

    public String getNomeJanela() {
        return nomeJanela;
    }

    public void setNomeJanela(String nomeJanela) {
        this.nomeJanela = nomeJanela;
    }

    public Integer getFkNotebook() {
        return fkNotebook;
    }

    public void setFkNotebook(Integer fkNotebook) {
        this.fkNotebook = fkNotebook;
    }

    @Override
    public String toString() {
        return "LogJanelas{" +
                "idLogJanelas=" + super.getId() +
                ", idJanela='" + idJanela + '\'' +
                ", nomeJanela='" + nomeJanela + '\'' +
                ", bloqueado=" + bloqueado +
                ", fkNotebook=" + fkNotebook +
                '}';
    }
}