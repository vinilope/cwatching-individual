package com.cw.services;

import com.cw.dao.RegistroVolumeDAO;
import com.cw.models.RegistroVolume;
import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.discos.Volume;

import java.util.List;
import java.util.TimerTask;

public class AtualizarRegistroVolume extends TimerTask {
    private InserirAlerta inserirAlerta;

    private Looca looca = new Looca();
    private RegistroVolumeDAO registroVolumeDAO = new RegistroVolumeDAO();

    public AtualizarRegistroVolume(InserirAlerta inserirAlerta) {
        this.inserirAlerta = inserirAlerta;
    }

    public void run() {
        List<Volume> volumeAtual = looca.getGrupoDeDiscos().getVolumes();

        try {
            for (Volume v : volumeAtual) {
                RegistroVolume registroVolume = new RegistroVolume(v.getDisponivel(), v.getUUID());
                registroVolumeDAO.inserirRegistroVolume(registroVolume);

                inserirAlerta.verificarAlerta(registroVolume, v.getTotal());
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
