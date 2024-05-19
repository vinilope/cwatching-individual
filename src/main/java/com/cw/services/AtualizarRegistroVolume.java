package com.cw.services;

import com.cw.dao.RegistroVolumeDAO;
import com.cw.models.RegistroVolume;
import com.cw.models.Sessao;
import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.discos.Volume;

import java.util.List;
import java.util.TimerTask;

public class AtualizarRegistroVolume extends TimerTask {
    private InserirAlerta inserirAlerta;
    private Sessao sessao;
    private Looca looca;
    private RegistroVolumeDAO registroVolumeDAO;

    public AtualizarRegistroVolume(Sessao sessao, InserirAlerta inserirAlerta) {
        this.inserirAlerta = inserirAlerta;
        this.sessao = sessao;
        this.looca = new Looca();
        this.registroVolumeDAO = new RegistroVolumeDAO();
    }

    public void run() {
        List<Volume> volumeAtual = looca.getGrupoDeDiscos().getVolumes();

        try {
            for (Volume v : volumeAtual) {
                RegistroVolume registroVolume = new RegistroVolume(v.getDisponivel(), sessao.getIdSessao(), v.getUUID());
                registroVolumeDAO.inserirRegistroVolume(registroVolume);

                inserirAlerta.verificarAlerta(registroVolumeDAO.buscarUltimoRegVolumePorUUID(v.getUUID()), v.getTotal());
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
