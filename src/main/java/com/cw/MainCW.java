package com.cw;

import com.cw.dao.MaquinaDAO;
import com.cw.dao.OciosidadeMouseDAO;
import com.cw.dao.SessaoDAO;
import com.cw.dao.UsuarioDAO;
import com.cw.models.Maquina;
import com.cw.models.Sessao;
import com.cw.models.Usuario;
import com.cw.services.AtualizarRegistro;
import com.cw.services.LoginUsuario;
import com.cw.services.OciosidadeMouse;

import java.util.Scanner;
import java.util.Timer;

public class MainCW {

    public static void main(String[] args) {
        Sessao sessao = new Sessao();

        UsuarioDAO userDao = new UsuarioDAO();
        MaquinaDAO maquinaDAO = new MaquinaDAO();
        SessaoDAO sessaoDAO = new SessaoDAO();

        System.out.println("""                                                                      
                   ______           __           _       __      __       __ \s
                  / ____/__  ____  / /____  ____| |     / /___ _/ /______/ /_\s
                 / /   / _ \\/ __ \\/ __/ _ \\/ ___/ | /| / / __ `/ __/ ___/ __ \\
                / /___/  __/ / / / /_/  __/ /   | |/ |/ / /_/ / /_/ /__/ / / /
                \\____/\\___/_/ /_/\\__/\\___/_/    |__/|__/\\__,_/\\__/\\___/_/ /_/\s
                                                                             \s                                                                         
                """);

        Boolean continuar = true;

        do {
            Scanner leitor = new Scanner(System.in);

            System.out.println("Usuário: ");
            String username = leitor.next();

            System.out.println("Senha: ");
            String senha = leitor.next();

            if (userDao.autenticarLogin(username, senha)) {
                Usuario usuario = userDao.buscarUsuarioPorUsername(username);
                Maquina maquina = maquinaDAO.buscarMaquinaPorHostname("VINILOPE");

                Sessao sessaoAtual = new Sessao(usuario, maquina);
                sessaoDAO.registrarSessao(sessaoAtual.getMaquina(), sessaoAtual.getUsuario());

                int INTERVALO_MS = 1000;
                Timer time = new Timer();
                time.schedule(new AtualizarRegistro(), 0, INTERVALO_MS);

                Integer TEMPO = 5;
                Integer SENSIBILIDADE = 25;
                System.out.println(usuario);
                OciosidadeMouse ociosidadeMouse = new OciosidadeMouse(usuario);
                ociosidadeMouse.setTempoRestanteSegundos(TEMPO);
                ociosidadeMouse.setSensibilidadeThreshold(SENSIBILIDADE);
                ociosidadeMouse.iniciar();

                System.out.println("logou");
                continuar = false;
            } else {
                System.out.println("Login inválido. Tentar novamente? Y/N");

                continuar = leitor.next().equalsIgnoreCase("Y");
            }

        } while (continuar);
    }
}
