package com.cw.services;

import com.cw.dao.ProcessoDAO;
import com.cw.dao.RegistroDAO;
import com.cw.models.Processo;
import com.cw.models.Registro;
import com.cw.models.Sessao;
import com.github.britooo.looca.api.core.Looca;
import oshi.SystemInfo;
import oshi.software.os.OSProcess;

import java.util.Timer;
import java.util.TimerTask;

public class AtualizarRegistro extends TimerTask {
    private Sessao sessao;
    private InserirAlerta inserirAlerta;

    private Looca looca = new Looca();
    private SystemInfo oshi = new SystemInfo();

    private RegistroDAO registroDAO = new RegistroDAO();
    private ProcessoDAO processoDAO = new ProcessoDAO();

    private Boolean registrarProcessos = true;

    public AtualizarRegistro(Sessao sessao, InserirAlerta inserirAlerta) {
        this.sessao = sessao;
        this.inserirAlerta = inserirAlerta;
    }

    public void run() {
        try {
            Registro registro = new Registro(
                    looca.getProcessador().getUso()*10,
                    looca.getMemoria().getEmUso(),
                    looca.getMemoria().getDisponivel(),
                    sessao.getIdSessao());

            registroDAO.inserirRegistro(registro);

            Registro r = registroDAO.buscarUltimoRegistroPorSessao(sessao);

            if (inserirAlerta.verificarAlerta(r)) registrarProcessos(r);

            exibirUltimoRegistro(r);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void registrarProcessos(Registro r) {
        if (!inserirAlerta.getRegistrarProcessos()) return;

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

        inserirAlerta.setRegistrarProcessos(false);
        new Timer().schedule(new IntervaloRegistroProcessos(inserirAlerta), 15000);

        inserirAlerta.listarProcessosEmAlerta(processoDAO.buscarDezProcessosComMaisMemoria(r), r);
    }

    public void exibirUltimoRegistro(Registro r) {
        System.out.println("""
                ----------------------------
                Registro %s
                ----------------------------
                Uso de CPU: %.2f%%
                Uso de RAM: %.2f%%
                ----------------------------
                """.formatted(
                        r.getDtHora(),
                        r.getUsoCpu() > 100.0 ? 100.0 : r.getUsoCpu(),
                        Conversor.converterPorcentagem((r.getDisponivelRam()+r.getUsoRam()), r.getUsoRam())
        ));
    }
}

