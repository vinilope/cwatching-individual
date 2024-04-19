package com.cw;

import com.cw.dao.OciosidadeMouseDAO;
import com.cw.dao.UsuarioDAO;
import com.cw.models.RegistroOciosidadeMouse;
import com.cw.services.AtualizarRegistro;
import com.cw.services.OciosidadeMouse;
import com.cw.services.RegistrarMaquina;
import com.cw.services.VerificarRede;
import com.github.britooo.looca.api.core.Looca;
import org.checkerframework.checker.units.qual.A;

import java.util.List;
import java.util.Timer;

public class TestesMain {
    public static void main(String[] args) {
//        RegistroOciosidadeMouse registro = new RegistroOciosidadeMouse(10, 1);
//        OciosidadeMouseDAO dao = new OciosidadeMouseDAO();
//
//        dao.inserirOciosidadeMouse(registro);
//
//        List<RegistroOciosidadeMouse> registros = dao.buscarRegistroOciosidadeFuncionario(1);
//
//        for (RegistroOciosidadeMouse registroOciosidadeMouse : registros) {
//            System.out.println(registroOciosidadeMouse);
//        }

//        OciosidadeMouse mouse = new OciosidadeMouse();
//        mouse.setTempoRestanteSegundos(30);
//        mouse.setSensibilidadeThreshold(10);
//        mouse.iniciar();

//        UsuarioDAO usuarioDAO = new UsuarioDAO();

//        System.out.println(usuarioDAO.buscarUsuarioPorUsername("usuario"));
//        System.out.println(usuarioDAO.autenticarLogin("usuario", "123123123"));

//        int INTERVALO_MS = 1000;
//        Timer time = new Timer();
//        time.schedule(new AtualizarRegistro(), 0, INTERVALO_MS);

//        Looca looca = new Looca();
////        System.out.println(looca.getRede().getParametros().getHostName());
//
//        System.out.println(looca.getProcessador().getUso()*10);

//        System.out.println("Está conectado: " + (new VerificarRede().redeConectada() ? "Sim" : "Não"));

//        new RegistrarMaquina().registrarMaquinaSeNaoExiste();

    }
}
