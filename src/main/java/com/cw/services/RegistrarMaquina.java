package com.cw.services;

import com.cw.dao.MaquinaDAO;
import com.cw.models.Maquina;
import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.rede.RedeInterface;

public class RegistrarMaquina {
    private Looca looca = new Looca();
    private MaquinaDAO dao = new MaquinaDAO();

    private Maquina maquina;

    public RegistrarMaquina() {
        this.maquina = new Maquina(
                looca.getSistema().getSistemaOperacional(),
                looca.getProcessador().getNome(),
                looca.getMemoria().getTotal(),
                looca.getRede().getParametros().getHostName(),
                1
        );
    }

    public void registrarMaquinaSeNaoExiste() {
        if (!dao.verificarMaquinaExistePorHostname(maquina.getHostname())) {
            dao.inserirMaquina(maquina);
        }
    }

}
