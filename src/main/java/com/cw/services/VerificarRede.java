package com.cw.services;

import com.cw.models.ParametroAlerta;
import com.cw.models.Sessao;

import java.net.InetAddress;
import java.util.TimerTask;

public class VerificarRede {

    private Integer timeout;

    public VerificarRede(Integer timeout) {
        this.timeout = timeout;
    }

    public Boolean redeConectada() {
        Boolean conectada = false;

        try {
            InetAddress address = InetAddress.getByName("google.com");
            conectada = address.isReachable(timeout);

        } catch (Exception e) {}

        return conectada;
    }
}
