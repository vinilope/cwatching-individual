package com.cw.services;

import com.cw.dao.RegistroVolumeDAO;
import com.cw.models.RegistroVolume;
import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.discos.Volume;

import java.util.List;
import java.util.TimerTask;

public class AtualizarRegistroVolume extends TimerTask {
    private Looca looca = new Looca();

    private RegistroVolumeDAO registroVolumeDAO = new RegistroVolumeDAO();

    public AtualizarRegistroVolume() {
    }

    public void run() {
        List<Volume> volumeAtual = looca.getGrupoDeDiscos().getVolumes();

        try {
            for (Volume v : volumeAtual) {
                registroVolumeDAO.inserirRegistroVolume(new RegistroVolume(v.getDisponivel(), v.getTotal(), v.getUUID()));
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
