package com.cw.services;

import java.util.TimerTask;

public class IntervaloRegistroProcessos extends TimerTask {
    private InserirAlerta inserirAlerta;

    public IntervaloRegistroProcessos(InserirAlerta inserirAlerta) {
        this.inserirAlerta = inserirAlerta;
    }

    public IntervaloRegistroProcessos() {
    }

    @Override
    public void run() {
        inserirAlerta.setRegistrarProcessos(true);
    }
}
