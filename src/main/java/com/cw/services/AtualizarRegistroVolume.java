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
                        inserirAlerta.verificarAlerta(registroVolume, v.getTotal()) ? "# ALERTA #" : "",
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
