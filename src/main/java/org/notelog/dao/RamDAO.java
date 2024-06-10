package org.notelog.dao;

import org.notelog.SimpleLogger;
import org.notelog.util.database.ConexaoMySQL;
import org.notelog.model.Ram;
import org.notelog.util.database.ConexaoSQLServer;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;

public class  RamDAO {
    SimpleLogger logger;

    {
        try {
            logger = new SimpleLogger("application.log");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Integer adicionarRam(Ram ram) {
        ConexaoMySQL conexaoMySQL = new ConexaoMySQL();
        JdbcTemplate conMySQL = conexaoMySQL.getConexaoDoBanco();

        ConexaoSQLServer conexaoSQLServer = new ConexaoSQLServer();
        JdbcTemplate conSQLServer = conexaoSQLServer.getConexaoDoBanco();

        String sqlInsert = "INSERT INTO Ram (totalMemoria, fkNotebook) VALUES (?, ?)";
        String sqlUpdate = "UPDATE Ram SET totalMemoria = ? WHERE fkNotebook = ?";
        String sqlSelectSQLServer = "SELECT TOP 1 id FROM Ram WHERE fkNotebook = ? ORDER BY id DESC";

        try {
            if (!ramExiste(ram)) {

                    // SQL SERVER
                    conSQLServer.update(sqlInsert, ram.getTotalMemoria(), ram.getFkNotebook());
                    ram.setId(conSQLServer.queryForObject(sqlSelectSQLServer, Integer.class, ram.getFkNotebook()));
                logger.info("Inserindo informações de RAM no Banco de dados (SQLServer)");
                    Integer id = ram.getId();

                    // MY SQL

                    String mysqlInsert = "INSERT INTO Ram (id, totalMemoria, fkNotebook) VALUES (?, ?,?)";
                logger.info("Inserindo informações de RAM no Banco de dados (MySQL)");
                    conMySQL.update(mysqlInsert, id, ram.getTotalMemoria(), ram.getFkNotebook());


            } else {

                    conSQLServer.update(sqlUpdate, ram.getTotalMemoria(), ram.getFkNotebook());
                    conMySQL.update(sqlUpdate, ram.getTotalMemoria(), ram.getFkNotebook());

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ram.getFkNotebook();
    }


    private boolean ramExiste(Ram ram) {
        ConexaoSQLServer conexaoSQLServer = new ConexaoSQLServer();
        JdbcTemplate conSQLServer = conexaoSQLServer.getConexaoDoBanco();

        String sql = "SELECT count(*) FROM Ram WHERE fkNotebook = ? AND totalMemoria = ?";
        Integer quantidade = null;

        try {

            quantidade = conSQLServer.queryForObject(sql, Integer.class, ram.getFkNotebook(), ram.getTotalMemoria());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return quantidade != null && quantidade > 0;
    }

}

