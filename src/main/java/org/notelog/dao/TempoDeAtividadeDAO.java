package org.notelog.dao;

import org.notelog.SimpleLogger;
import org.notelog.util.database.ConexaoMySQL;
import org.notelog.model.TempoDeAtividade;
import org.notelog.util.database.ConexaoSQLServer;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;

public class TempoDeAtividadeDAO {

    SimpleLogger logger;

    {
        try {
            logger = new SimpleLogger("application.log");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void adicionarTempoDeAtividade(TempoDeAtividade tempoDeAtividade) {
        ConexaoMySQL conexaoMySQL = new ConexaoMySQL();
        JdbcTemplate conMySQL = conexaoMySQL.getConexaoDoBanco();

        ConexaoSQLServer conexaoSQLServer = new ConexaoSQLServer();
        JdbcTemplate conSQLServer = conexaoSQLServer.getConexaoDoBanco();

        String sqlInsert = "INSERT INTO TempoDeAtividade (fkNotebook, tempoDeAtividade, tempoInicializado) VALUES (?, ?, ?)";
        String sqlUpdate = "UPDATE TempoDeAtividade SET tempoDeAtividade = ? WHERE tempoInicializado = ? AND fkNotebook = ?";

        try {

                if (tempoDeAtividadeExiste(tempoDeAtividade) == false) {

                    // SQL SERVER

                    // insert
                    conSQLServer.update(sqlInsert, tempoDeAtividade.getFkNotebook(), tempoDeAtividade.getTempoDeAtividade(), tempoDeAtividade.getTempoInicializado());
                    logger.info("Inserindo informações de Tempo de Atividade no Banco de dados (SQLServer)");

                    String selectSQLServer = "SELECT TOP 1 id FROM TempoDeAtividade WHERE fkNotebook = ? ORDER BY id DESC";

                    Integer id = null;

                    id = conSQLServer.queryForObject(selectSQLServer, Integer.class, tempoDeAtividade.getFkNotebook());

                    // MY SQL

                    String mysqlInsert = "INSERT INTO TempoDeAtividade (id, fkNotebook, tempoDeAtividade, tempoInicializado) VALUES (?, ?, ?, ?)";
                    logger.info("Inserindo informações de Tempo de Atividade no Banco de dados (MySQL)");
                    conMySQL.update(mysqlInsert, id, tempoDeAtividade.getFkNotebook(), tempoDeAtividade.getTempoDeAtividade(), tempoDeAtividade.getTempoInicializado());


                } else {

                    // update
                    conSQLServer.update(sqlUpdate, tempoDeAtividade.getTempoDeAtividade(), tempoDeAtividade.getTempoInicializado(), tempoDeAtividade.getFkNotebook());
                    conMySQL.update(sqlUpdate, tempoDeAtividade.getTempoDeAtividade(), tempoDeAtividade.getTempoInicializado(), tempoDeAtividade.getFkNotebook());

                }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean tempoDeAtividadeExiste(TempoDeAtividade tempoDeAtividade) {
        ConexaoSQLServer conexaoSQLServer = new ConexaoSQLServer();
        JdbcTemplate conSQLServer = conexaoSQLServer.getConexaoDoBanco();

        String sql = "SELECT count(*) FROM TempoDeAtividade WHERE fkNotebook = ? AND tempoInicializado = ?";
        Integer quantidade = null;

        quantidade = conSQLServer.queryForObject(sql, Integer.class, tempoDeAtividade.getFkNotebook(), tempoDeAtividade.getTempoInicializado());

        if (quantidade != null && quantidade > 0){
            return true;
        }else{
            return false;
        }

    }

}
