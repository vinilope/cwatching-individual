package com.cw.services;

import com.github.britooo.looca.api.core.Looca;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Objects;
import java.util.List;

public class LogsService {
    private static final Integer QTD_MAX_LINHAS = 3;

    public static void gerarLog(String mensagem) {
        Path path = Paths.get("/cwatching/log");
        File dir = new File(String.valueOf(path));

        mensagem += "\n";

        try {
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            } else {
                System.out.println("length: " + Objects.requireNonNull(dir.list()).length);
                LocalDateTime dtHora = LocalDateTime.now(ZoneId.of("UTC-3"));

                DateTimeFormatter f = DateTimeFormatter.ofPattern("yMdHHmsn");

                Integer index = Objects.requireNonNull(dir.list()).length;
                String nameFileAtual = dir.list().length == 0 ? "/%s_%s.txt".formatted(new Looca().getRede().getParametros().getHostName(), dtHora.format(f)) : getUltimoArquivo(dir);
                String nameFileNovo = "/%s_%s.txt".formatted(new Looca().getRede().getParametros().getHostName(), dtHora.format(f));

                File file = new File(path + nameFileAtual);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/y HH:m:s:n");

                FileWriter log = new FileWriter(file, true);
                log.write(dtHora.format(formatter) + " " + mensagem);
                log.close();

                System.out.println(getQtdLinhas(file));

                if (getQtdLinhas(file) >= QTD_MAX_LINHAS) {
                    file = new File(path + nameFileNovo);
                    file.createNewFile();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public static String getUltimoArquivo(File dir) {
        List<String> fileNames = List.of(dir.list());

        fileNames.stream().sorted();

        return fileNames.get(fileNames.size()-1);
    }
}