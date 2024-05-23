package com.cw.services;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.processos.Processo;
import com.github.britooo.looca.api.group.processos.ProcessoGrupo;
import oshi.SystemInfo;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;

import java.io.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProcessoUtil {
    Looca looca = new Looca();
    ProcessoGrupo processos = looca.getGrupoDeProcessos();
    List<Processo> listaProcessos = processos.getProcessos();

    public void limparProcesso(String NomeProcesso){
            try {
                Runtime.getRuntime().exec("taskkill /F /IM " + NomeProcesso + ".exe" );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
}





