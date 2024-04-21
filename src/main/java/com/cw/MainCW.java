package com.cw;

import com.cw.dao.*;
import com.cw.database.CriarTabelas;
import com.cw.database.PopularTabelas;
import com.cw.models.*;
import com.cw.services.AtualizarRegistroVolume;
import com.cw.services.AtualizarRegistro;
import com.cw.services.OciosidadeMouse;
import com.cw.services.RegistrarMaquina;
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
        ParametroAlertaDAO parametroAlertaDAO = new ParametroAlertaDAO();

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
                // Usuário está logado

                Empresa empresa = userDao.buscarEmpresaPorUsername(username);

                ParametroAlerta parametroAlertaAtual = parametroAlertaDAO.buscarParametroAlertaPorEmpresa(empresa);
                System.out.println(parametroAlertaAtual);

                // Cadastra a máquina atual caso ela não esteja no banco
                RegistrarMaquina registrarMaquina = new RegistrarMaquina();
                registrarMaquina.registrarMaquinaSeNaoExiste(empresa);

                // Busca objetos usuário e máquina para ser registrada a sessão criada
                Usuario usuario = userDao.buscarUsuarioPorUsername(username);
                Maquina maquina = maquinaDAO.buscarMaquinaPorHostnameEEmpresa(hostname, empresa);

                // Registra a sessão criada ao logar
                sessaoDAO.registrarSessao(maquina.getIdMaquina(), usuario.getIdUsuario());
                Sessao sessaoAtual = sessaoDAO.buscarUltimaSessaoPorMaquina(maquina.getIdMaquina());

                System.out.println("Login com sucesso. Iniciando captura de dados...");

                // Inicializa timer para coleta de dados de CPU e RAM
                Timer atualizarRegistro = new Timer();
                atualizarRegistro.schedule(new AtualizarRegistro(sessaoAtual), 0, parametroAlertaAtual.getIntervaloRegistroMs());

                // Inicializa timer para coleta de dados de volumes
                Timer atualizarVolume = new Timer();
                atualizarVolume.schedule(new AtualizarRegistroVolume(), 0, parametroAlertaAtual.getIntervaloVolumeMs());

                // Inicializa o monitoramento de ociosidade de mouse do usuário
                OciosidadeMouse ociosidadeMouse = new OciosidadeMouse(usuario);
                ociosidadeMouse.setTempoDecrescenteMs(parametroAlertaAtual.getTimerMouseMs());
                ociosidadeMouse.setSensibilidadeThreshold(parametroAlertaAtual.getSensibilidadeMouse());
                ociosidadeMouse.iniciar();

                continuar = false;
            } else {
                System.out.println("Login inválido. Tentar novamente? Y/N");

                continuar = leitor.next().equalsIgnoreCase("Y");
            }

        } while (continuar);
    }
}
