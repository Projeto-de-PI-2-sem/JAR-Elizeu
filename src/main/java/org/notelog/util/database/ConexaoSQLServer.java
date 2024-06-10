package org.notelog.util.database;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;
import java.sql.Connection;
import java.sql.SQLException;

public class ConexaoSQLServer {
    private JdbcTemplate conexaoDoBanco;

    public ConexaoSQLServer() {
        try {
            BasicDataSource dataSource = new BasicDataSource();
            dataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            dataSource.setUrl("jdbc:sqlserver://54.158.158.60:1433;database=notelog;encrypt=true;trustServerCertificate=true");
            dataSource.setUsername("sa");
            dataSource.setPassword("notelikeag0d*");

            // Configuração adicional para confiar em todos os certificados (não recomendado em produção)
            TrustManager[] trustAllCertificates = new TrustManager[] {
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) {
                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] x509Certificates, String s) {
                        }

                        @Override
                        public X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }
                    }
            };

            // Estabelece confiança em todos os certificados SSL (não recomendado em produção)
            // Remove estas linhas se você estiver usando um certificado confiável
            javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext.getInstance("SSL");
            sc.init(null, trustAllCertificates, new java.security.SecureRandom());
            javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            conexaoDoBanco = new JdbcTemplate(dataSource);
        } catch (Exception e) {
            // Logar ou tratar a exceção
            System.err.println("Erro ao configurar conexão SQLServer: " + e.getMessage());
            conexaoDoBanco = null; // Definir como null se a configuração falhar
        }
    }

    public JdbcTemplate getConexaoDoBanco() {
        return conexaoDoBanco;
    }
}