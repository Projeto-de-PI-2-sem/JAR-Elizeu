package org.notelog.util.database;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

public class ConexaoMySQL {
    private JdbcTemplate conexaoDoBanco;

    public ConexaoMySQL() {
        try {
            BasicDataSource dataSource = new BasicDataSource();
            dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
            dataSource.setUrl("jdbc:mysql://localhost:3306/notelog");
            dataSource.setUsername("notelogUser");
            dataSource.setPassword("notelikeag0d*");

            conexaoDoBanco = new JdbcTemplate(dataSource);
        } catch (DataAccessException e) {
            // Logar ou tratar a exceção
            System.err.println("Erro ao configurar conexão MySQL: " + e.getMessage());
            conexaoDoBanco = null; // Definir como null se a configuração falhar
        }
    }

    public JdbcTemplate getConexaoDoBanco() {
        return conexaoDoBanco;
    }
}