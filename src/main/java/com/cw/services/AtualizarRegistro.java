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
                    looca.getProcessador().getUso()*10 > 100 ? 100.0 : looca.getProcessador().getUso()*10,
                    looca.getMemoria().getEmUso(),
                    looca.getMemoria().getDisponivel(),
                    looca.getSistema().getTempoDeAtividade(),
                    sessao.getIdSessao());

            registroDAO.inserirRegistro(registro);

            Registro r = registroDAO.buscarUltimoRegistroPorSessao(sessao);

            if (inserirAlerta.verificarAlerta(r)) registrarProcessos(r);
        } catch (Exception e) {
            System.out.println("Não foi possivel inserir o registro: " + e);
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

        // Reinicializando timeout para inserir novamente os processos
        // TODO: parametrizar o timer
        new Timer().schedule(new IntervaloRegistroProcessos(inserirAlerta), 15000);

        System.out.println("Registrando processos...");
    }
}

