package org.notelog.service;

import org.notelog.dao.FuncionarioDAO;
import org.notelog.dao.NotebookDAO;
import org.notelog.model.Funcionario;
import org.notelog.model.Notebook;
import org.notelog.util.database.ConexaoSQLServer;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;
import java.util.Scanner;
import java.security.SecureRandom;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import static org.notelog.app.system.MonitoramentoSystem.escolherMonitoramento;
import static org.notelog.model.Notebook.pegarNumeroSerial;

public class LoginService {
    public static void vincularFuncionario(Funcionario usuarioMaster) {
        FuncionarioDAO userDAO = new FuncionarioDAO();
        NotebookDAO notebookDAO = new NotebookDAO();
        String numeroSerial = pegarNumeroSerial();

        if (userDAO.temVinculo(numeroSerial) == false){
            Scanner scanner = new Scanner(System.in);
            Scanner scannerStr = new Scanner(System.in);
            ASCIIService ascii = new ASCIIService();

            if (userDAO.buscarFuncionarios(usuarioMaster.getFkEmpresa()).size() <= 0) {

                ascii.VinculoFuncionarioASCII();

                Notebook notebook = new Notebook(scanner.nextInt(), usuarioMaster.getFkEmpresa());

            } else {
                ascii.cadastrarFuncionarioASCII();

                Boolean emailValido = false;
                Boolean nomeValido = false;
                Boolean cargoValido = false;
                String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*";

                do {
                    for (Funcionario funcionario : userDAO.buscarFuncionarios(usuarioMaster.getFkEmpresa())) {
                        System.out.println("Nome do Funcionário:");
                        String nomeFuncionario = scannerStr.nextLine();
                        System.out.println("Email do Funcionário:");
                        String emailFuncionario = scannerStr.nextLine();
                        System.out.println("Cargo do Funcionário:");
                        String cargoFuncionario = scannerStr.nextLine();
                        System.out.println("Acesso à Dashboard?[S/N]");
                        String acessoFuncionario = scannerStr.nextLine();

                        String emailRegex = "^[^\\\\s@]+@[^\\\\s@]+\\\\.[^\\\\s@]+$";
                        Pattern pattern = Pattern.compile(emailRegex);
                        Matcher matcher = pattern.matcher(emailFuncionario);


                        if (matcher.matches() && !nomeFuncionario.equals("") && !cargoFuncionario.equals("")) {
                            emailValido = true;
                            nomeValido = true;
                            cargoValido = true;

                            if (acessoFuncionario.equalsIgnoreCase("s")) {
                                SecureRandom random = new SecureRandom();
                                StringBuilder senha = new StringBuilder(10);

                                for (int i = 0; i < 10; i++) {
                                    int indiceAleatorio = random.nextInt(CHARACTERS.length());
                                    senha.append(CHARACTERS.charAt(indiceAleatorio));
                                }

                                ConexaoSQLServer conexaoSQLServer = new ConexaoSQLServer();
                                JdbcTemplate consqlserver = conexaoSQLServer.getConexaoDoBanco();

                                String sql = "INSERT INTO Funcionario (nome, cargo, email, senha, fkEmpresa) VALUES (?, ?, ?, ?, ?)";

                                try {
                                    // SQL SERVER
                                    consqlserver.update(sql, nomeFuncionario, cargoFuncionario, emailFuncionario, senha.toString(), usuarioMaster.getFkEmpresa());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            } else if (acessoFuncionario.equalsIgnoreCase("n")){
                                ConexaoSQLServer conexaoSQLServer = new ConexaoSQLServer();
                                JdbcTemplate consqlserver = conexaoSQLServer.getConexaoDoBanco();

                                String sql = "INSERT INTO Funcionario (nome, cargo, email, fkEmpresa) VALUES (?, ?, ?, ?)";

                                try {
                                    // SQL SERVER
                                    consqlserver.update(sql, nomeFuncionario, cargoFuncionario, emailFuncionario, usuarioMaster.getFkEmpresa());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        } else {
                            System.out.println("""
                                    =======================================
                                    |  Campos inválidos, tente novamente  |
                                    =======================================
                                    """);
                        }
                    }
                } while (!emailValido && !nomeValido && !cargoValido);
                ascii.VinculoFuncionarioASCII();
            }

            Notebook notebook = new Notebook(scanner.nextInt(), usuarioMaster.getFkEmpresa());

            if (notebookDAO.adicionarNotebook(notebook)){
                Funcionario usuario = userDAO.pegaFuncionarioPeloNumeroSerial();
                try {
                    escolherMonitoramento(usuario, notebook);
                } catch (InterruptedException | IOException e) {
                    throw new RuntimeException(e);
                }
            } else System.out.println("ERRO!!! Funcionário inexistente vinculado a empresa");

        } else{
            Notebook notebookJaCadastrado = notebookDAO.consultaNotebook(userDAO.pegaFuncionarioPeloNumeroSerial().getId());
            try {
                escolherMonitoramento(userDAO.pegaFuncionarioPeloNumeroSerial(), notebookJaCadastrado);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
