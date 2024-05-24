package com.cw.services;

import com.cw.dao.MaquinaDAO;
import com.cw.dao.VolumeDAO;
import com.cw.models.Empresa;
import com.cw.models.Maquina;
import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.discos.Volume;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MaquinaService {
    private Looca looca = new Looca();
    private MaquinaDAO maquinaDAO = new MaquinaDAO();
    private VolumeDAO volumeDAO = new VolumeDAO();

    private Maquina maquinaAtual;

    public MaquinaService() {
        this.maquinaAtual = new Maquina(
                looca.getSistema().getSistemaOperacional(),
                looca.getProcessador().getNome(),
                looca.getMemoria().getTotal(),
                looca.getRede().getParametros().getHostName(),
                0
        );
    }

    public void registrarMaquinaSeNaoExiste(Empresa e) {
        Boolean maquinaExiste = maquinaDAO.verificarMaquinaExistePorHostnameEEmpresa(maquinaAtual.getHostname(), e);
        Boolean componentesAlterados = maquinaDAO.componentesAlterou(maquinaAtual);

        System.out.println("\nVerificado cadastro da máquina...");

        if (!maquinaExiste) {
            System.out.println("\nMáquina não encontrada. Cadastrando máquina...");

            this.maquinaAtual.setFkEmpresa(e.getIdEmpresa());
            maquinaDAO.inserirMaquina(maquinaAtual, e);
            registrarGrupoVolumePorMaquina(e);
        } else {
            System.out.println("\nMáquina encontrada.");
        }

        if (componentesAlterados && maquinaExiste) {
            System.out.println("\nDetectada alteração nos componentes. Atualizando dados...");
            maquinaDAO.atualizarMaquina(maquinaAtual, e);
        }

        atualizarGrupoVolumeExistente(e);
    }

    private void atualizarGrupoVolumeExistente(Empresa e) {
        Maquina maquina = maquinaDAO.buscarMaquinaPorHostnameEEmpresa(looca.getRede().getParametros().getHostName(), e);
        List<Volume> volumes = looca.getGrupoDeDiscos().getVolumes();

        for (Volume v : volumes) {
            Map<String, Object> mapVolume;

            mapVolume = volumeDAO.volumeAlterou(new com.cw.models.Volume(v.getUUID(), v.getNome(), v.getPontoDeMontagem(), v.getTotal()));

            com.cw.models.Volume volumeAtual = new com.cw.models.Volume(
                    v.getUUID(),
                    v.getNome(),
                    v.getPontoDeMontagem(),
                    v.getTotal(),
                    maquina.getIdMaquina()
            );

            if ((Long) mapVolume.get("existe") == 0) {
                System.out.println("\nNovo volume detectado. Inserindo volume...");
                volumeDAO.inserirVolume(volumeAtual);

            } else if ((Long) mapVolume.get("alterou") == 1) {
                System.out.println("\n alteração no volume. Atualizando dados...");
                volumeDAO.atualizarVolume(volumeAtual);

            }
        }
    }

    public void registrarGrupoVolumePorMaquina(Empresa empresa) {
        Maquina maquina = maquinaDAO.buscarMaquinaPorHostnameEEmpresa(looca.getRede().getParametros().getHostName(), empresa);
        List<Volume> volumes = looca.getGrupoDeDiscos().getVolumes();

        for (Volume volume : volumes) {
            try {
                volumeDAO.inserirVolume(new com.cw.models.Volume(
                        volume.getUUID(),
                        volume.getNome(),
                        volume.getPontoDeMontagem(),
                        volume.getTotal(),
                        maquina.getIdMaquina()
                ));
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    ;

}
