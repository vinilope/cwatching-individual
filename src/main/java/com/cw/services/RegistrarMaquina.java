package com.cw.services;

import com.cw.dao.MaquinaDAO;
import com.cw.dao.VolumeDAO;
import com.cw.models.Empresa;
import com.cw.models.Maquina;
import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.discos.Volume;

import java.util.List;

public class RegistrarMaquina {
    private Looca looca = new Looca();
    private MaquinaDAO maquinaDAO = new MaquinaDAO();
    private VolumeDAO volumeDAO = new VolumeDAO();

    private Maquina maquinaAtual;

    public RegistrarMaquina() {
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
        Boolean dadosMaquinaAlterado = maquinaDAO.verificarComponentesMaquinaExistente(maquinaAtual);

        if (!maquinaExiste) {
                this.maquinaAtual.setFkEmpresa(e.getIdEmpresa());
                maquinaDAO.inserirMaquina(maquinaAtual,e);
                registrarGrupoVolumePorMaquina(e);
            }
        }



    private void atualizarGrupoVolumeExistente(Empresa e) {

        Boolean maquinaExiste = maquinaDAO.verificarMaquinaExistePorHostnameEEmpresa(maquinaAtual.getHostname(), e);
        List<Volume> volumes = looca.getGrupoDeDiscos().getVolumes();

    }

    public void registrarGrupoVolumePorMaquina(Empresa e) {
        Boolean maquinaExiste = maquinaDAO.verificarMaquinaExistePorHostnameEEmpresa(maquinaAtual.getHostname(), e);
        Maquina maquina = maquinaDAO.buscarMaquinaPorHostnameEEmpresa(looca.getRede().getParametros().getHostName(), e);
        List<Volume> volumes = looca.getGrupoDeDiscos().getVolumes();

        if (!maquinaExiste) {
            for (Volume volume : volumes) {
                volumeDAO.inserirVolume(new com.cw.models.Volume(
                        volume.getUUID(),
                        volume.getNome(),
                        volume.getPontoDeMontagem(),
                        volume.getTotal(),
                        maquina.getIdMaquina()
                ));
            }
        }
        /*else {
            atualizarMaquinaExistente(e);
        }*/
    }

    ;

}
