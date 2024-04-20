package com.cw.services;

import com.cw.dao.RegistroDAO;
import com.cw.models.Registro;
import com.cw.models.Sessao;
import com.github.britooo.looca.api.core.Looca;

import java.util.TimerTask;

public class AtualizarRegistro extends TimerTask {
    private Sessao sessao;
    // (int)(((double)emUso/100000000)/((double)total/100000000)*100) <- porcentagem de uso de ram
    private Looca looca = new Looca();
    private RegistroDAO registroDAO = new RegistroDAO();

    public AtualizarRegistro(Sessao sessao) {
        this.sessao = sessao;
    }

    public void run() {
        try {
            registroDAO.inserirRegistro(new Registro(
                    looca.getProcessador().getUso()*10,
                    looca.getMemoria().getEmUso(),
                    looca.getMemoria().getDisponivel(),
                    sessao.getIdSessao()
            ));
        } catch (Exception e) {}
    }
}

