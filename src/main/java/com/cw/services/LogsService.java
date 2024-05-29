package com.cw.services;

import java.io.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Objects;

public class LogsService {
    private static final Integer QTD_MAX_LINHAS = 10;

//    public static void gerarLog(String mensagem) {
//
//        // TODO: verificar o tanto de linhas para a criação de novos arquivos
//
//        try {
//            mensagem += "\n";
//            String path = ".\\logs\\";
//            File diretorio = new File(path);
//
//            System.out.println(Arrays.toString(diretorio.list()));
//            System.out.println("length: " + Objects.requireNonNull(diretorio.list()).length);
//
//            Integer index = Objects.requireNonNull(diretorio.list()).length;
//            String nameFileAtual = "log_history_%d.txt".formatted(index == 0 ? index + 1 : index);
//            String nameFileNovo = "log_history_%d.txt".formatted(index == 0 ? index + 2 : index + 1);
//
//            File file = new File(path + nameFileAtual);
//
//            FileWriter log = new FileWriter(file, true);
//
//            LocalDateTime dtHora = LocalDateTime.now(ZoneId.of("UTC-3"));
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/y HH:m:s");
//
//            log.write(dtHora.format(formatter) + " " + mensagem);
//            log.close();
//
//            if (getQtdLinhas(file) >= QTD_MAX_LINHAS) {
//                file = new File(path + nameFileNovo);
//                file.createNewFile();
//            }
//
//        } catch(IOException e) {
//            System.out.println("Erro ao criar log");
//            e.printStackTrace();
//        }
//    }

    public static void gerarLog(String mensagem) {
        System.out.println(mensagem);
    }

    public static Integer getQtdLinhas(File file){
        Integer linhas = 0;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            while (reader.readLine() != null) linhas++;
            reader.close();
        } catch (IOException e) {
            System.out.println(e);
        }

        return linhas;
    }
}
