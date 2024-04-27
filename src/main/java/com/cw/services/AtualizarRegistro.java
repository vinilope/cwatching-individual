package com.cw.services;

import com.cw.dao.ProcessoDAO;
import com.cw.dao.RegistroDAO;
import com.cw.models.ParametroAlerta;
import com.cw.models.Processo;
import com.cw.models.Registro;
import com.cw.models.Sessao;
import com.github.britooo.looca.api.core.Looca;
import oshi.SystemInfo;
import oshi.software.os.OSProcess;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class AtualizarRegistro extends TimerTask {
    private Sessao sessao;
    private Alerta alerta;

    private Looca looca = new Looca();
    private SystemInfo oshi = new SystemInfo();

    VerificarRede verificarRede;

    private RegistroDAO registroDAO = new RegistroDAO();
    private ProcessoDAO processoDAO = new ProcessoDAO();

    private Boolean registrarProcessos = true;

    public AtualizarRegistro(Sessao sessao, Alerta alerta) {
        this.sessao = sessao;
        this.alerta = alerta;
        this.verificarRede = new VerificarRede(alerta.getParametro().getIntervaloRegistroMs());
    }

    public void run() {
        try {
            Registro registro = new Registro(
                    looca.getProcessador().getUso()*10,
                    looca.getMemoria().getEmUso(),
                    looca.getMemoria().getDisponivel(),
                    verificarRede.redeConectada(),
                    sessao.getIdSessao());

            registroDAO.inserirRegistro(registro);

            Registro r = registroDAO.buscarUltimoRegistroPorSessao(sessao);

            String[] alertas = alerta.verificarAlerta(r);

            if (!(alertas[0].isEmpty() && alertas[1].isEmpty())) registrarProcessos(r);

            exibirUltimoRegistro(r, alertas);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void registrarProcessos(Registro r) {
        if (!alerta.getRegistrarProcessos()) return;

        for (OSProcess processo : oshi.getOperatingSystem().getProcesses()) {
            if ((!processo.getPath().contains("C:\\Windows\\System32\\") && !processo.getPath().isEmpty())) {
                Processo p = new Processo(
                        processo.getName(),
                        processo.getPath(),
                        processo.getResidentSetSize(),
                        r.getIdRegistro());

                processoDAO.inserirProcesso(p);
            }
        }

        alerta.setRegistrarProcessos(false);
        new Timer().schedule(new IntervaloRegistroProcessos(alerta), 15000);

        alerta.listarProcessosEmAlerta(processoDAO.buscarDezProcessosComMaisMemoria(r), r);
    }

    public void exibirUltimoRegistro(Registro r, String[] alerta) {
        System.out.println("""
                ----------------------------
                Registro %s
                ----------------------------
                Uso de CPU: %.2f%% %s
                Uso de RAM: %.2f%% %s
                Conexão Internet: %s
                ----------------------------
                """.formatted(
                        r.getDtHora(),
                        r.getUsoCpu() > 100.0 ? 100.0 : r.getUsoCpu(),
                        alerta[0].equals("cpu") ? "⚠ ALERTA ⚠" : "",
                        Conversor.converterPorcentagem((r.getDisponivelRam()+r.getUsoRam()), r.getUsoRam()),
                        alerta[1].equals("ram") ? "⚠ ALERTA ⚠" : "",
                        r.getConexaoInternet() ? "Sim" : "Não"
        ));
    }

    public Integer getIntervaloRegistro() {
        return this.alerta.getParametro().getIntervaloRegistroMs();
    }
}

