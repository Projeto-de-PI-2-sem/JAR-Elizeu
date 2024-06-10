package org.notelog;

import com.github.britooo.looca.api.core.Looca;
import org.notelog.dao.*;
import org.notelog.model.*;
import org.notelog.service.ASCIIService;

import java.io.Console;
import java.io.IOException;
import java.util.Scanner;

import static org.notelog.service.LoginService.vincularFuncionario;

public class Main {
    public static void main(String[] args)  {
        try{
            SimpleLogger logger = new SimpleLogger("application.log");
            ASCIIService ascii = new ASCIIService();
            ascii.NotelogASCII();

            Thread.sleep(3000);
            Scanner scanner = new Scanner(System.in);

            // Loop de login
            boolean loginValido = false;
            FuncionarioDAO userDAO = new FuncionarioDAO();
            Funcionario usuario = null;

            while (!loginValido) {
                Console console = System.console();
                String senha = "";
                System.out.println("Por favor, faça login para continuar...");
                System.out.print("Email: ");
                String email = scanner.nextLine();
                if (console == null){
                    System.out.print("Senha: ");
                    senha = scanner.nextLine();
                }else{
                    char[] senhaArray = console.readPassword("Senha: ");

                    senha = new String(senhaArray);

                    java.util.Arrays.fill(senhaArray,' ');
                }


                // Verifica se o usuário existe no banco de dados
                usuario = userDAO.verificaUsuario(email, senha);
                if (usuario != null) {
                    loginValido = true;
                    System.out.println();
                    System.out.println("Login bem-sucedido! Bem-vindo, " + usuario.getNome() + "!");
                    logger.info("Login bem-sucedido para o usuário:"+ usuario.getNome());
                    Thread.sleep(3000);
                    vincularFuncionario(usuario);
                } else {
                    System.out.println();
                    System.out.println("Email ou senha incorretos. Tente novamente.");
                    logger.warning("Login mal-sucedido!");

                }
            }
            logger.fechar();
        } catch (IOException | InterruptedException e){
            e.printStackTrace();
        }
    }

}