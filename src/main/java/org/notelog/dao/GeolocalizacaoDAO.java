package org.notelog.dao;

import org.notelog.model.Geolocalizacao;
import org.notelog.util.database.ConexaoMySQL;
import org.notelog.util.database.ConexaoSQLServer;
import org.springframework.jdbc.core.JdbcTemplate;

public class GeolocalizacaoDAO {
    public void adicionaGeolocalizacao(Integer fkNotebook, Geolocalizacao geolocalizacao) {
        ConexaoMySQL conexaoMySQL = new ConexaoMySQL();
        JdbcTemplate conmysql = conexaoMySQL.getConexaoDoBanco();
//
        ConexaoSQLServer conSQLServer = new ConexaoSQLServer();
        JdbcTemplate consqlserver = conSQLServer.getConexaoDoBanco();

        String publicIPAddress = geolocalizacao.ObterIP();
        String jsonString = geolocalizacao.ObterGeoPorIP(publicIPAddress);
        geolocalizacao.preencherDados(jsonString);


        // SQL SERVER

        String sql = """
        INSERT INTO Geolocalizacao (enderecoIp, pais, cidade, nomeRegiao, latitude, longitude, timezone, companiaInternet, fkNotebook)
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        Object[] params = {
                geolocalizacao.getIp(), geolocalizacao.getCountryName(), geolocalizacao.getCityName(),
                geolocalizacao.getRegionName(), geolocalizacao.getLatitude(), geolocalizacao.getLongitude(),
                geolocalizacao.getTimeZone(), geolocalizacao.getAs(), fkNotebook
        };

        consqlserver.update(sql, params);

        String selectSQLServer = "SELECT TOP 1 id FROM Geolocalizacao WHERE fkNotebook = ? ORDER BY id DESC";

        Integer id = consqlserver.queryForObject(selectSQLServer, Integer.class, fkNotebook);


        // MYSQL

        String mysql = """
        INSERT INTO Geolocalizacao (id, enderecoIp, pais, cidade, nomeRegiao, latitude, longitude, timezone, companiaInternet, fkNotebook)
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        Object[] myparams = {
                id, geolocalizacao.getIp(), geolocalizacao.getCountryName(), geolocalizacao.getCityName(),
                geolocalizacao.getRegionName(), geolocalizacao.getLatitude(), geolocalizacao.getLongitude(),
                geolocalizacao.getTimeZone(), geolocalizacao.getAs(), fkNotebook
        };

        conmysql.update(mysql, myparams);



    }

}

