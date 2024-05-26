package com.cw;

import com.cw.conexao.Conexao;
import com.cw.dao.*;
import com.cw.database.CriarPopularTabelas;
import com.cw.models.*;
import com.cw.services.*;
import com.github.britooo.looca.api.core.Looca;

import java.util.Scanner;
import java.util.Timer;

public class MainCW {

    public static void main(String[] args) throws Exception {
        // Buscar hostname da máquina atual
        String hostname = new Looca().getRede().getParametros().getHostName();

        UsuarioDAO userDao = new UsuarioDAO();
        MaquinaDAO maquinaDAO = new MaquinaDAO();
        SessaoDAO sessaoDAO = new SessaoDAO();
        ConfigDAO configDAO = new ConfigDAO();
        PermProcessoDAO permProcessoDAO = new PermProcessoDAO();

        System.out.println("""                                                                      
                   ______           __           _       __      __       __ \s
                  / ____/__  ____  / /____  ____| |     / /___ _/ /______/ /_\s
                 / /   / _ \\/ __ \\/ __/ _ \\/ ___/ | /| / / __ `/ __/ ___/ __ \\
                / /___/  __/ / / / /_/  __/ /   | |/ |/ / /_/ / /_/ /__/ / / /
                \\____/\\___/_/ /_/\\__/\\___/_/    |__/|__/\\__,_/\\__/\\___/_/ /_/\s
                                                                             \s                                                                         
                """);

//           new CriarPopularTabelas().criarPopularTabelas();

        // Loop para interação com usuário (login)
        Boolean continuar;
        do {
            Scanner leitor = new Scanner(System.in);

            String username = "lucas@techsolutions.com";
            String senha = "lucas789";

            System.out.print("Usuário: ");
//            String username = leitor.next();

            System.out.print("Senha: ");
//            String senha = leitor.next();

            // Autentica o login
            if (userDao.autenticarLogin(username, senha)) {
                // Usuário está logado

                System.out.println("\nLogin com sucesso. Registrando sessão...");

                // Busca a empresa pelo usuário logado
                Empresa empresa = userDao.buscarEmpresaPorUsername(username);

                // Busca os parâmetros definidos pela empresa
                Config configAtual = configDAO.buscarConfigPorEmpresa(empresa);
                configAtual.setPermProcessos(configDAO.buscarPermProcessosPorConfig(configAtual));

                // Cadastra a máquina atual caso ela não esteja no banco
                MaquinaService maquinaService = new MaquinaService();
                maquinaService.registrarMaquinaSeNaoExiste(empresa);

                // Busca objetos usuário e máquina para ser registrada a sessão criada
                Usuario usuario = userDao.buscarUsuarioPorUsername(username);
                Maquina maquina = maquinaDAO.buscarMaquinaPorHostnameEEmpresa(hostname, empresa);

                // Registra a sessão criada ao logar
                sessaoDAO.registrarSessao(maquina.getIdMaquina(), usuario.getIdUsuario());
                Sessao sessaoAtual = sessaoDAO.buscarUltimaSessaoPorMaquina(maquina.getIdMaquina());

                AlertaService alerta = new AlertaService(configAtual);

                // Inicializa timer para coleta de dados de CPU e RAM
                System.out.println("\nIniciando coleta de dados...\n");
                Timer atualizarRegistro = new Timer();
                atualizarRegistro.schedule(new RegistroService(sessaoAtual, alerta), 0, configAtual.getIntervaloRegistroMs());

                // Inicializa timer para coleta de dados de volumes
                Timer atualizarVolume = new Timer();
                atualizarVolume.schedule(new RegistroVolumeService(sessaoAtual, alerta), 0, configAtual.getIntervaloVolumeMs());

                // Inicializa timer para monitoramento de processos
                Timer monitorarProcesso = new Timer();
                monitorarProcesso.schedule(new ProcessoService(configAtual), 0, 500);

                // Inicializa o monitoramento de ociosidade de mouse do usuário
                OciosidadeService ociosidadeService = new OciosidadeService(usuario);
                ociosidadeService.setTempoDecrescenteMs(configAtual.getTimerMouseMs());
                ociosidadeService.setSensibilidadeThreshold(configAtual.getSensibilidadeMouse());
                ociosidadeService.iniciar();

                continuar = false;
            } else {
                System.out.println("Login inválido. Tentar novamente? Y/N");

                continuar = leitor.next().equalsIgnoreCase("Y");
            }

        } while (continuar);
    }
}
