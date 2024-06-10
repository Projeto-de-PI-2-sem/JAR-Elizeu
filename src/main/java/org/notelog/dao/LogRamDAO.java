package org.notelog.dao;

import org.notelog.model.LogRam;
import org.notelog.util.database.ConexaoMySQL;
import org.notelog.util.database.ConexaoSQLServer;
import org.springframework.jdbc.core.JdbcTemplate;

public class LogRamDAO {
    public void adicionarLogRam(LogRam logRam) {
        ConexaoMySQL conexaoMySQL = new ConexaoMySQL();
        JdbcTemplate conmysqL = conexaoMySQL.getConexaoDoBanco();

        ConexaoSQLServer conexaoSQLServer = new ConexaoSQLServer();
        JdbcTemplate consqlserver = conexaoSQLServer.getConexaoDoBanco();

        String sql = "INSERT INTO LogRam (fkRAM, usoMemoria, memoriaDisponivel, dataLog) VALUES (?, ?, ?, ?)";

        try {
            // SQL SERVER

            consqlserver.update(sql, logRam.getFkRAM(), logRam.getUsoMemoria(), logRam.getMemoriaDisponivel(), logRam.dataHoraAtual());

            String sqlSelectSQLServer = "SELECT TOP 1 id FROM LogRam WHERE fkRam = ? ORDER BY id DESC";

            Integer id = consqlserver.queryForObject(sqlSelectSQLServer, Integer.class, logRam.getFkRAM());

            // MY SQL

            String mysql = "INSERT INTO LogRam (id ,fkRAM, usoMemoria, memoriaDisponivel, dataLog) VALUES (?, ?, ?, ?, ?)";

            conmysqL.update(mysql, id, logRam.getFkRAM(), logRam.getUsoMemoria(), logRam.getMemoriaDisponivel(), logRam.dataHoraAtual());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
