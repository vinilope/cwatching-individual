package com.cw.services;

import com.cw.dao.PermProcessoDAO;
import com.cw.models.Config;
import com.cw.models.PermProcesso;
import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.processos.Processo;

import java.util.*;

public class MonitorarProcesso extends TimerTask {
    private Config config;
    private List<PermProcesso> permProcessos;
    private PermProcessoDAO permProcessoDAO;
    private Looca looca;

    public MonitorarProcesso(Config config) {
        this.config = config;
        this.permProcessoDAO = new PermProcessoDAO();
        looca = new Looca();
    }

    @Override
    public void run() {
        permProcessos = permProcessoDAO.buscarProcessos(this.config);
        Set<String> processos = new HashSet<>(filtrarProcessoNome(looca.getGrupoDeProcessos().getProcessos()));

        for (String p : processos) {
            if (!verificarProcessoNaLista(p)) permProcessoDAO.inserirPermProcesso(p, this.config);

            Boolean permitido = permProcessoDAO.verificarPermProcesso(p, config);

            if (permitido != null && !permitido) ProcessoUtil.finalizarProcesso(p);
        }
    }

    public Boolean verificarProcessoNaLista(String p) {
        for (PermProcesso pp : permProcessos) {
            if (p.equals(pp.getNome())) return true;
        }
        
        return false;
    }

    public List<String> filtrarProcessoNome(List<Processo> processos) {
        List<String> p = new ArrayList<>();

        for (Processo processo : processos) {
            p.add(processo.getNome());
        }

        return p;
    }
}
