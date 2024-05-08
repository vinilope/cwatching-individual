package com.cw.services;

import com.cw.dao.AlertaDAO;
import com.cw.models.*;
import com.github.britooo.looca.api.core.Looca;

import java.util.List;

public class InserirAlerta {
    private ParametroAlerta parametro;
    private Boolean registrarProcessos;
    private AlertaDAO alertaDAO;

    String[] alerta = {"", ""};

    Looca looca = new Looca();

    public InserirAlerta(ParametroAlerta parametro) {
        this.parametro = parametro;
        this.registrarProcessos = true;
        alertaDAO = new AlertaDAO();
    }

//    public String[] verificarAlerta(Registro r) {
//
//        Double ram = Conversor.converterPorcentagem(looca.getMemoria().getTotal(), r.getUsoRam());
//        alerta[0] = r.getUsoCpu() > parametro.getMaxCpu() ? "cpu" : "";
//
//        alerta[1] = ram > parametro.getMaxRam() ? "ram" : "";
//
//        return alerta;
//    }

    public Boolean verificarAlerta(Registro r) {
        Double ramPer = Conversor.converterPorcentagem(looca.getMemoria().getTotal(), r.getUsoRam());
        Double cpuPer = r.getUsoCpu();

        if (ramPer > parametro.getMaxRam()) alertaDAO.inserirAlerta(new com.cw.models.Alerta("ram", r.getFkSessao()));
        if (cpuPer > parametro.getMaxCpu()) alertaDAO.inserirAlerta(new com.cw.models.Alerta("cpu", r.getFkSessao()));

        return ((ramPer > parametro.getMaxRam()) || (cpuPer > parametro.getMaxCpu()));
    }

    public Boolean verificarAlerta(RegistroVolume r, Long total) {
        Double disponivel = Conversor.converterPorcentagem(total, r.getVolumeDisponivel());
        return disponivel < (100.0 - parametro.getMaxVolume());
    }

    public void listarProcessosEmAlerta(List<Processo> processos, Registro r) {
        System.out.println("----------------------------");
        System.out.println("Processos %s".formatted(r.getDtHora()));
        System.out.println("----------------------------");

        for (Processo processo : processos) {
            System.out.println(processo);
        }
    }

    public Boolean getRegistrarProcessos() {
        return registrarProcessos;
    }

    public void setRegistrarProcessos(Boolean registrarProcessos) {
        this.registrarProcessos = registrarProcessos;
    }
}
