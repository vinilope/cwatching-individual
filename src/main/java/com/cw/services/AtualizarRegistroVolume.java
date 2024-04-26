package com.cw.services;

import com.cw.dao.RegistroVolumeDAO;
import com.cw.models.Registro;
import com.cw.models.RegistroVolume;
import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.discos.Volume;

import java.util.List;
import java.util.TimerTask;

public class AtualizarRegistroVolume extends TimerTask {
    private Alerta alerta;

    private Looca looca = new Looca();
    private RegistroVolumeDAO registroVolumeDAO = new RegistroVolumeDAO();

    public AtualizarRegistroVolume(Alerta alerta) {
        this.alerta = alerta;
    }

    public void run() {
        List<Volume> volumeAtual = looca.getGrupoDeDiscos().getVolumes();

        try {
            for (Volume v : volumeAtual) {
                RegistroVolume registroVolume = new RegistroVolume(v.getDisponivel(), v.getTotal(), v.getUUID());

                registroVolumeDAO.inserirRegistroVolume(registroVolume);

                System.out.println("""
                -----------------------------------------
                Registro %s %s
                -----------------------------------------
                Ponto de montagem: %s
                Disponível (GB): %.2f GB
                Disponível (%%): %.2f%%
                -----------------------------------------
                """.formatted(
                        v.getNome(),
                        alerta.verificarAlerta(registroVolume) ? "⚠ ALERTA ⚠" : "",
                        v.getPontoDeMontagem(),
                        Conversor.converterBytesParaGb(v.getDisponivel()),
                        Conversor.converterPorcentagem(v.getTotal(), v.getDisponivel())
                ));
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
