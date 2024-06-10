package org.notelog.dao;

import org.notelog.model.Empresa;
import org.notelog.util.database.ConexaoMySQL;
import org.notelog.util.database.ConexaoSQLServer;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Collections;

public class EmpresaDAO {

    public Empresa consultaEmpresaCPU(Integer fkCPU) {
        ConexaoSQLServer conSQLServer = new ConexaoSQLServer();
        JdbcTemplate consqlserver = conSQLServer.getConexaoDoBanco();

        String sqlserver = """
        SELECT TOP 1 * FROM Empresa
        JOIN Notebook ON Empresa.id = Notebook.fkEmpresa
        JOIN Cpu ON Cpu.fkNotebook = Notebook.id
        JOIN LogCpu ON LogCpu.fkCpu = Cpu.id
        WHERE LogCpu.fkCpu = ?
        """;

        Object[] params = {fkCPU};
        Empresa empresa = null;


            try {
                empresa = consqlserver.queryForObject(sqlserver, params, new BeanPropertyRowMapper<>(Empresa.class));
            } catch (EmptyResultDataAccessException e) {
                // Nenhuma empresa encontrada no SQL Server
            }


        return empresa;
    }


    public Empresa consultaEmpresaRAM(Integer fkRAM) {
        ConexaoSQLServer conSQLServer = new ConexaoSQLServer();
        JdbcTemplate consqlserver = conSQLServer.getConexaoDoBanco();

        String sqlserver = """
        SELECT TOP 1 * FROM Empresa
        JOIN Notebook ON Empresa.id = Notebook.fkEmpresa
        JOIN Ram ON Ram.fkNotebook = Notebook.id
        JOIN LogRam ON LogRam.fkRam = Ram.id
        WHERE LogRam.fkRam = ?
        """;

        Object[] params = {fkRAM};
        Empresa empresa = null;

            try {
                empresa = consqlserver.queryForObject(sqlserver, params, new BeanPropertyRowMapper<>(Empresa.class));
            } catch (EmptyResultDataAccessException e) {
                // Nenhuma empresa encontrada no SQL Server
            }


        return empresa;
    }


//    public Empresa consultaEmpresaDisco(Integer fkDisco) {
//        ConexaoMySQL conexaoMySQL = new ConexaoMySQL();
//        JdbcTemplate conmysql = conexaoMySQL.getConexaoDoBanco();
//
//        ConexaoSQLServer conSQLServer = new ConexaoSQLServer();
//        JdbcTemplate consqlserver = conSQLServer.getConexaoDoBanco();
//
//        String mysql = """
//        SELECT * FROM Empresa
//        JOIN Notebook ON Empresa.id = Notebook.fkEmpresa
//        JOIN Cpu ON Cpu.fkNotebook = Notebook.id
//        JOIN LogCpu ON LogCpu.fkCpu = Cpu.id
//        WHERE LogCpu.fkCpu = ?
//        LIMIT 1
//        """;
//
//        String sqlserver = """
//        SELECT TOP 1 * FROM Empresa
//        JOIN Notebook ON Empresa.id = Notebook.fkEmpresa
//        JOIN Cpu ON Cpu.fkNotebook = Notebook.id
//        JOIN LogCpu ON LogCpu.fkCpu = Cpu.id
//        WHERE LogCpu.fkCpu = ?
//        """;
//
//        Object[] params = {fkDisco};
//        Empresa empresa = null;
//
//            try {
//                empresa = conmysql.queryForObject(mysql, params, new BeanPropertyRowMapper<>(Empresa.class));
//            } catch (EmptyResultDataAccessException e) {
//                // Nenhuma empresa encontrada no MySQL, continuar a busca no SQL Server se estiver ativo
//            }
//
//            try {
//                empresa = consqlserver.queryForObject(sqlserver, params, new BeanPropertyRowMapper<>(Empresa.class));
//            } catch (EmptyResultDataAccessException e) {
//                // Nenhuma empresa encontrada no SQL Server
//            }
//
//
//        return empresa;
//    }



}
