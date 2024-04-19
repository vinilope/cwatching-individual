package com.cw;

import com.cw.dao.*;
import com.cw.models.*;
import com.cw.services.AtualizarRegistro;
import com.cw.services.OciosidadeMouse;
import com.github.britooo.looca.api.core.Looca;

import java.util.Scanner;
import java.util.Timer;

public class MainCW {

    public static void main(String[] args) {
        // Buscar hostname da máquina atual
        String hostname = new Looca().getRede().getParametros().getHostName();

        UsuarioDAO userDao = new UsuarioDAO();
        MaquinaDAO maquinaDAO = new MaquinaDAO();
        SessaoDAO sessaoDAO = new SessaoDAO();


        // Configurar parâmetros para alertas e tempos de intervalo
        // Instância para definir parâmetros:
        ParametroAlerta parametroAlertaAtual = new ParametroAlerta();

        // Porcentagem:
        parametroAlertaAtual.setMaxCpu(70.0);
        parametroAlertaAtual.setMaxRam(95.0);
        parametroAlertaAtual.setMaxVolume(85.0);

        // Tempo para ociosidade do mouse e sua sensibilidade:
        parametroAlertaAtual.setSensisbilidadeMouse(25);
        parametroAlertaAtual.setRegistroMouseSeg(15);

        // Intervalo de captura dos registros:
        parametroAlertaAtual.setRegistroMaquinaSeg(2);
        // Fim configuração

        System.out.println("""                                                                      
                   ______           __           _       __      __       __ \s
                  / ____/__  ____  / /____  ____| |     / /___ _/ /______/ /_\s
                 / /   / _ \\/ __ \\/ __/ _ \\/ ___/ | /| / / __ `/ __/ ___/ __ \\
                / /___/  __/ / / / /_/  __/ /   | |/ |/ / /_/ / /_/ /__/ / / /
                \\____/\\___/_/ /_/\\__/\\___/_/    |__/|__/\\__,_/\\__/\\___/_/ /_/\s
                                                                             \s                                                                         
                """);

        // Loop para interação com usuário (login)
        Boolean continuar;
        do {
            Scanner leitor = new Scanner(System.in);

            System.out.print("Usuário: ");
            String username = leitor.next();

            System.out.print("Senha: ");
            String senha = leitor.next();

            // Autentica o login
            if (userDao.autenticarLogin(username, senha)) {

                // Busca objetos usuário e máquina para ser registrada a sessão criada
                Usuario usuario = userDao.buscarUsuarioPorUsername(username);
                Maquina maquina = maquinaDAO.buscarMaquinaPorHostname(hostname);

                // Registra a sessão criada ao logar
                Sessao sessaoAtual = new Sessao(usuario, maquina);
                sessaoDAO.registrarSessao(sessaoAtual.getMaquina(), sessaoAtual.getUsuario());

                // Inicializa timer para coleta de dados
                Timer time = new Timer();
                time.schedule(new AtualizarRegistro(), 0, parametroAlertaAtual.getRegistroMaquinaSeg());

                // Inicializa o monitoramento de ociosidade de mouse do usuário
                OciosidadeMouse ociosidadeMouse = new OciosidadeMouse(usuario);
                ociosidadeMouse.setTempoRestanteSegundos(parametroAlertaAtual.getRegistroMouseSeg());
                ociosidadeMouse.setSensibilidadeThreshold(parametroAlertaAtual.getSensisbilidadeMouse());

                System.out.println("Login com sucesso. Iniciando captura de dados...");
                ociosidadeMouse.iniciar();
                continuar = false;
            } else {
                System.out.println("Login inválido. Tentar novamente? Y/N");

                continuar = leitor.next().equalsIgnoreCase("Y");
            }

        } while (continuar);
    }
}
