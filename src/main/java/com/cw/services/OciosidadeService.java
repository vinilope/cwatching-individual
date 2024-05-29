package com.cw.services;

import com.cw.dao.OciosidadeMouseDAO;
import com.cw.models.RegistroOciosidadeMouse;
import com.cw.models.Usuario;
import com.mysql.cj.log.Log;

import java.awt.*;

public class OciosidadeService {
    private static final Boolean DEBUG = false;

    private static Integer tempoDecrescenteMs;
    private static Integer tempoCrescenteMs;

    private static Integer tempoDecrescente;

    private static Integer sensibilidadeThreshold;

    private static Boolean timerDecrescenteRodando;
    private static Boolean mouseMoveu;

    public static Boolean run;

    static OciosidadeMouseDAO ociosidadeMouseDAO = new OciosidadeMouseDAO();

    private static Usuario usuario;

    public static void iniciar(Usuario u, Integer t, Integer s) {
        timerDecrescenteRodando = false;
        tempoCrescenteMs = 0;
        usuario = u;
        tempoDecrescenteMs = t;
        sensibilidadeThreshold = s;
        run = true;
        new Thread(threadMouse).start();
    }

    private static Runnable threadMouse = new Runnable() {
        public void run() {
            try {
                if (!run) Thread.currentThread().interrupt();

                // Busca coordenadas do mouse em dois intervalos de tempo
                Point coordAnterior = getCoordenadaMouse();
                Thread.sleep(300);
                Point coordAtual = getCoordenadaMouse();

                // Cálculo para checar se mouse moveu dentro de uma threshold estabelecida com base em suas coordenadas
                int intervaloXMenor = coordAtual.x - sensibilidadeThreshold;
                int intervaloXMaior = coordAtual.x + sensibilidadeThreshold;
                int intervaloYMenor = coordAtual.y - sensibilidadeThreshold;
                int intervaloYMaior = coordAtual.y + sensibilidadeThreshold;

                mouseMoveu = (
                        (coordAnterior.x < intervaloXMenor) ||
                                (coordAnterior.x > intervaloXMaior) ||
                                (coordAnterior.y < intervaloYMenor) ||
                                (coordAnterior.y > intervaloYMaior)
                );


                if (mouseMoveu)
                    tempoDecrescente = tempoDecrescenteMs; // Reseta a contagem regressiva caso o mouse mova

                if (mouseMoveu && !timerDecrescenteRodando) {
                    timerDecrescenteRodando = true;
                    new Thread(threadTimerDecrescente).start(); // Inicia a contagem regressiva
                }

                run();
            } catch (Exception e) {
            }
        }
    };

    private static Runnable threadTimerDecrescente = new Runnable() {
        public void run() {
            try {
                if (!run) Thread.currentThread().interrupt();

                tempoDecrescente -= 100;
                Thread.sleep(100);
                if (DEBUG && tempoDecrescente % 1000 == 0) System.out.println("Falta para ocioso: " + tempoDecrescente);

                if (tempoDecrescente <= 0)
                    throw new RuntimeException();

                run();
            } catch (Exception e) {
                // Contagem regressiva chegou à zero
                timerDecrescenteRodando = false;
                new Thread(threadTimerCrescente).start(); // Inicia a contagem do tempo de ociosidade
            }
        }
    };

    private static Runnable threadTimerCrescente = new Runnable() {
        public void run() {
            try {
                if (!run) Thread.currentThread().interrupt();

                tempoCrescenteMs += 100;
                Thread.sleep(100);

                if (DEBUG && tempoCrescenteMs % 1000 == 0) System.out.println("Ocioso: " + tempoCrescenteMs);

                if (mouseMoveu)
                    throw new RuntimeException();

                run();
            } catch (Exception e) {
                // Insere o tempo da ociosidade da vez e reseta o timer
                ociosidadeMouseDAO.inserirOciosidadeMouse(new RegistroOciosidadeMouse(tempoCrescenteMs, usuario.getIdUsuario()));
                System.out.println(ociosidadeMouseDAO.buscarUltimoRegistroOciosidadePorUsuario(usuario));

                if (DEBUG) System.out.println("Inserido %d segundos".formatted(tempoCrescenteMs));

                tempoCrescenteMs = 0;
            }
        }
    };

    public static Point getCoordenadaMouse() {
        return MouseInfo.getPointerInfo().getLocation();
    }

    public void setTempoDecrescenteMs(Integer tempoDecrescenteMs) {
        this.tempoDecrescenteMs = tempoDecrescenteMs;
        this.tempoDecrescente = tempoDecrescenteMs;
    }

    public void setSensibilidadeThreshold(Integer sensibilidadeThreshold) {
        if (sensibilidadeThreshold < 0 || sensibilidadeThreshold > 100) return;
        this.sensibilidadeThreshold = sensibilidadeThreshold;
    }
}
