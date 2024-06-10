package org.notelog.dao;

import org.notelog.model.LogCpu;
import org.notelog.util.database.ConexaoMySQL;
import org.notelog.util.database.ConexaoSQLServer;
import org.springframework.jdbc.core.JdbcTemplate;

public class LogCpuDAO {
    public void adicionarLogCpu(LogCpu logCpu) {
        ConexaoMySQL conexaoMySQL = new ConexaoMySQL();
        JdbcTemplate conmysql = conexaoMySQL.getConexaoDoBanco();

        ConexaoSQLServer conSQLServer = new ConexaoSQLServer();
        JdbcTemplate consqlserver = conSQLServer.getConexaoDoBanco();

        // SQL SERVER

        String sql = """
        INSERT INTO LogCpu (porcentagemUso, dataLog, fkCPU)
        VALUES (?, ?, ?)
        """;

        Object[] params = {logCpu.getPorcentagemUso(), logCpu.dataHoraAtual(), logCpu.getFkCPU()};

        consqlserver.update(sql, params);

        // MY SQL

        String selectSQLServer = "SELECT TOP 1 id FROM LogCpu WHERE fkCpu = ? ORDER BY id DESC";

        Integer id = consqlserver.queryForObject(selectSQLServer, Integer.class, logCpu.getFkCPU());

        Object[] myparams = {id ,logCpu.getPorcentagemUso(), logCpu.dataHoraAtual(), logCpu.getFkCPU()};

        String mysql = "INSERT INTO LogCpu (id,porcentagemUso, dataLog, fkCPU) VALUES (?, ?, ?, ?)";

        conmysql.update(mysql, myparams);

    }

}
