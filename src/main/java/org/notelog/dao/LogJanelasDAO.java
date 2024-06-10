package org.notelog.dao;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.janelas.Janela;
import com.github.britooo.looca.api.group.janelas.JanelaGrupo;
import org.notelog.model.LogJanelas;
import org.notelog.util.database.ConexaoMySQL;
import org.notelog.util.database.ConexaoSQLServer;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;


public class LogJanelasDAO {

    private boolean logJanelasExiste(LogJanelas janela) {
        ConexaoSQLServer conSQLServer = new ConexaoSQLServer();
        JdbcTemplate consqlserver = conSQLServer.getConexaoDoBanco();

        Integer quantidade = null;

        quantidade = consqlserver.queryForObject("SELECT COUNT(*) FROM LogJanelas WHERE idJanela = ? AND fkNotebook = ?", Integer.class, janela.getIdJanela(), janela.getFkNotebook());

        if (quantidade != null && quantidade > 0){
            return true;
        }else {
            return false;
        }
    }


    public void adicionarNovoLogJanelas(Integer fkNotebook) {
        Looca looca = new Looca();
        JanelaGrupo grupoDeJanelas = looca.getGrupoDeJanelas();
        List<Janela> janelas = grupoDeJanelas.getJanelasVisiveis();

        for (Janela janela : janelas) {
            LogJanelas novaLogJanela = new LogJanelas(null, janela.getPid().toString(), janela.getTitulo(), 0, fkNotebook);

                    if (logJanelasExiste(novaLogJanela) == false) {
                        adicionarLogJanelas(novaLogJanela);
                    }

        }
    }


    public void adicionarLogJanelas(LogJanelas logJanelas) {
        ConexaoMySQL conexaoMySQL = new ConexaoMySQL();
        JdbcTemplate conmysql = conexaoMySQL.getConexaoDoBanco();

        ConexaoSQLServer conSQLServer = new ConexaoSQLServer();
        JdbcTemplate consqlserver = conSQLServer.getConexaoDoBanco();

        // SQL SERVER

        String sql = String.format(
                """
                        INSERT INTO LogJanelas (idJanela, nomeJanela, bloqueado, fkNotebook) VALUES ('%s', '%s', %d, %d);""",
                logJanelas.getIdJanela(),
                logJanelas.getNomeJanela().isEmpty() ? '.' : logJanelas.getNomeJanela(),
                0,
                logJanelas.getFkNotebook()
        );
            consqlserver.update(sql);

            String sqlSelect = "SELECT TOP 1 id FROM LogJanelas WHERE fkNotebook = ? ORDER BY id DESC";

            Integer id = consqlserver.queryForObject(sqlSelect, Integer.class, logJanelas.getFkNotebook());

        // MY SQL

        String mysql = String.format(
                """
                INSERT INTO LogJanelas (id ,idJanela, nomeJanela, bloqueado, fkNotebook) VALUES (%d,'%s', '%s', %d, %d);
                """,
                id,
                logJanelas.getIdJanela(),
                logJanelas.getNomeJanela().isEmpty() ? '.' : logJanelas.getNomeJanela(),
                0,
                logJanelas.getFkNotebook()
        );


        conmysql.update(mysql);
    }

    public List<LogJanelas> selecionarJanelas(Integer idNotebook){
        ConexaoSQLServer conSQLServer = new ConexaoSQLServer();
        JdbcTemplate consqlserver = conSQLServer.getConexaoDoBanco();

        String sql = "SELECT * FROM LogJanelas WHERE fkNotebook = ?";

        List<LogJanelas> listaLogJanelas = new ArrayList<>();

            listaLogJanelas = consqlserver.query(sql, new BeanPropertyRowMapper<>(LogJanelas.class), idNotebook);


        return listaLogJanelas;
    }


}

