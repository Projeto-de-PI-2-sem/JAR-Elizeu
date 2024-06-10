package org.notelog.dao;

import org.notelog.SimpleLogger;
import org.notelog.util.database.ConexaoMySQL;
import org.notelog.model.Funcionario;
import org.notelog.util.database.ConexaoSQLServer;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.notelog.model.Notebook.pegarNumeroSerial;

public class FuncionarioDAO {

    SimpleLogger logger;

    {
        try {
            logger = new SimpleLogger("application.log");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Funcionario verificaUsuario(String email, String senha) {
        ConexaoMySQL conexaoMySQL = new ConexaoMySQL();
        JdbcTemplate conmysql = conexaoMySQL.getConexaoDoBanco();

        ConexaoSQLServer conSQLServer = new ConexaoSQLServer();
        JdbcTemplate consqlserver = conSQLServer.getConexaoDoBanco();

        String sql = """
        SELECT F.id, F.nome, F.email, F.fkEmpresa FROM Funcionario AS F WHERE F.email = ? AND F.senha = ?
        """;

        Object[] params = {email, senha};
        Funcionario usuario = null;

            try {
                usuario = consqlserver.queryForObject(sql, params, new BeanPropertyRowMapper<>(Funcionario.class));
            } catch (EmptyResultDataAccessException e) {
                // Usuário não encontrado no SQL Server
                logger.warning(usuario +"não encontrado no sistema");
            }

            if(usuario != null){
                String sqluser = """
                    SELECT COUNT(*) FROM Empresa WHERE id = ?
                """;
                Integer count = conmysql.queryForObject(sqluser, Integer.class, usuario.getFkEmpresa());
                logger.info(usuario + "encontrado no sistema");

                if (count == 0){
                    String sqlUpdate = "update Empresa set id = ? WHERE nome = 'Empresa' AND id = 1;";
                    conmysql.update(sqlUpdate, usuario.getFkEmpresa());
                }
            }


        return usuario;
    }


    public Boolean temVinculo(String numeroSerial) {

        ConexaoSQLServer conSQLServer = new ConexaoSQLServer();
        JdbcTemplate consqlserver = conSQLServer.getConexaoDoBanco();

        String sql = """
        SELECT COUNT(*) FROM Funcionario AS F
        JOIN Notebook AS N ON F.id = N.fkFuncionario
        WHERE N.numeroSerial = ?
        """;

        Object[] params = {numeroSerial};
        Integer funcionarioJaAtrelado = null;

        funcionarioJaAtrelado = consqlserver.queryForObject(sql, params, Integer.class);

        if(funcionarioJaAtrelado == null || funcionarioJaAtrelado == 0){
            return false;
        }else{
            return true;
        }
    }


    public Funcionario pegaFuncionarioPeloNumeroSerial() {
        ConexaoSQLServer conSQLServer = new ConexaoSQLServer();
        JdbcTemplate consqlserver = conSQLServer.getConexaoDoBanco();

        String numeroSerial = pegarNumeroSerial(); // Implemente a função pegarNumeroSerial() conforme necessário

        String sql = """
        SELECT Funcionario.* FROM Funcionario
        JOIN Notebook ON Funcionario.id = Notebook.fkFuncionario
        WHERE Notebook.numeroSerial = ?
        """;

        Object[] params = {numeroSerial};
        Funcionario funcionario = null;


            try {
                funcionario = consqlserver.queryForObject(sql, params, new BeanPropertyRowMapper<>(Funcionario.class));
            } catch (EmptyResultDataAccessException e) {
                // Nenhum funcionário encontrado no SQL Server
            } catch (Exception e) {
                // Lidar com outras exceções, se necessário
                e.printStackTrace();
            }

        return funcionario;
    }


    public List<Funcionario> buscarFuncionarios(Integer fkEmpresa) {

        ConexaoSQLServer conSQLServer = new ConexaoSQLServer();
        JdbcTemplate consqlserver = conSQLServer.getConexaoDoBanco();

        String sql = """
        SELECT F.id, F.nome FROM Funcionario AS F
        LEFT JOIN Notebook ON F.id = Notebook.fkFuncionario
        WHERE Notebook.fkFuncionario IS NULL AND F.fkEmpresa = ?
        """;

        Object[] params = {fkEmpresa};
        List<Funcionario> funcionarios = null;

        funcionarios = consqlserver.query(sql, params, new BeanPropertyRowMapper<>(Funcionario.class));


        return funcionarios != null ? new ArrayList<>(funcionarios) : new ArrayList<>();
    }


}
