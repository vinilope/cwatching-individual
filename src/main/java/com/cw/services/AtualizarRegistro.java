package com.cw.services;

import com.cw.dao.RegistroDAO;
import com.cw.models.Registro;
import com.github.britooo.looca.api.core.Looca;

import java.util.TimerTask;

public class AtualizarRegistro extends TimerTask {
    // (int)(((double)emUso/100000000)/((double)total/100000000)*100) <- porcentagem de uso de ram
    private Looca looca = new Looca();

    public AtualizarRegistro() {
    }

    public void run() {
        try {
            new RegistroDAO().inserirRegistro(new Registro(looca.getProcessador().getUso()*10, looca.getMemoria().getEmUso(), looca.getMemoria().getDisponivel()));
        } catch (Exception e) {}
    }
}

