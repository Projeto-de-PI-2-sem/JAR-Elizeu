package org.notelog;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SimpleLogger {
    private String logFilePath;
    private PrintWriter writer;
    private DateTimeFormatter dateTimeFormatter;

    public SimpleLogger(String logFilePath) throws IOException {
        this.logFilePath = logFilePath;
        this.writer = new PrintWriter(new FileWriter(logFilePath, true), true);
        this.dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    }

    public void log(String level,  String mensagem) {
        String timestamp = LocalDateTime.now().format(dateTimeFormatter);
        writer.println(timestamp + "[" + level + "]" + mensagem);
    }

    public void info(String mensagem) {
        log("INFO", mensagem);
    }

    public void warning(String mensagem) {
        log("WARNING", mensagem);
    }

    public void error(String mensagem) {
        log("ERROR", mensagem);
    }

    public void fechar() {
        writer.close();
    }
}