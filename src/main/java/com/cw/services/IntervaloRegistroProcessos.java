package com.cw.services;

import java.util.TimerTask;

public class IntervaloRegistroProcessos extends TimerTask {
    private Alerta alerta;

    public IntervaloRegistroProcessos(Alerta alerta) {
        this.alerta = alerta;
    }

    public IntervaloRegistroProcessos() {
    }

    @Override
    public void run() {
        alerta.setRegistrarProcessos(true);
    }
}
