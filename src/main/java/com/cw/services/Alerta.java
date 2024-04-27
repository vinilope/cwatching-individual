package com.cw.services;

import com.cw.models.ParametroAlerta;
import com.cw.models.Processo;
import com.cw.models.Registro;
import com.cw.models.RegistroVolume;
import com.github.britooo.looca.api.core.Looca;

import java.util.List;

public class Alerta {
    private ParametroAlerta parametro;
    private Boolean registrarProcessos;

    String[] alerta = {"", ""};

    Looca looca = new Looca();

    public Alerta(ParametroAlerta parametro) {
        this.parametro = parametro;
        this.registrarProcessos = true;
    }

    public String[] verificarAlerta(Registro r) {

        Double ram = Conversor.converterPorcentagem(looca.getMemoria().getTotal(), r.getUsoRam());
        alerta[0] = r.getUsoCpu() > parametro.getMaxCpu() ? "cpu" : "";

        alerta[1] = ram > parametro.getMaxRam() ? "ram" : "";

        return alerta;
    }

    public Boolean verificarAlerta(RegistroVolume v) {
        Double disponivel = Conversor.converterPorcentagem(v.getVolumeTotal(), v.getVolumeDisponivel());
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
