package com.cw.services;

import com.cw.dao.OciosidadeMouseDAO;
import com.cw.models.RegistroOciosidadeMouse;
import com.cw.models.Usuario;

import java.awt.*;

public class OciosidadeMouse {
    private final Boolean DEBUG = true;

    private Integer tempoDecrescenteSegundos;
    private Integer tempoDecrescente;
    private Integer tempoCrescente;

    private Integer sensibilidadeThreshold;

    private Boolean timerDecrescenteIsRunning;
    private Boolean mouseIsMoving;

    private Usuario usuario;

    public OciosidadeMouse(Usuario usuario) {
        this.tempoDecrescenteSegundos = 45;
        this.timerDecrescenteIsRunning = false;
        this.mouseIsMoving = false;
        this.tempoDecrescente = tempoDecrescenteSegundos * 1000;
        this.tempoCrescente = 0;
        this.sensibilidadeThreshold = 0;
        this.usuario = usuario;
    }

    public void iniciar() {
        new Thread(threadMouse).start();
    }

    public void setTempoRestanteSegundos(Integer tempoDecrescenteSegundos) {
        this.tempoDecrescenteSegundos = tempoDecrescenteSegundos;
    }

    public void setSensibilidadeThreshold(Integer sensibilidadeThreshold) {
        if (sensibilidadeThreshold < 0 || sensibilidadeThreshold > 100) return;
        this.sensibilidadeThreshold = sensibilidadeThreshold;
    }

    public static Point getCoord() {
        return MouseInfo.getPointerInfo().getLocation();
    }

    public void handleMovimentoMouse() {
        if (mouseIsMoving)
            tempoDecrescente = tempoDecrescenteSegundos * 1000;

        if (mouseIsMoving && !timerDecrescenteIsRunning) {
            timerDecrescenteIsRunning = true;
            new Thread(threadTimerDecrescente).start();
        }
    }

    private Runnable threadMouse = new Runnable() {
        public void run() {
            Point coordAnterior = getCoord();
            try {
                Thread.sleep(300);
                Point coordAtual = getCoord();

                int intervaloXMenor = coordAtual.x - sensibilidadeThreshold;
                int intervaloXMaior = coordAtual.x + sensibilidadeThreshold;
                int intervaloYMenor = coordAtual.y - sensibilidadeThreshold;
                int intervaloYMaior = coordAtual.y + sensibilidadeThreshold;

                mouseIsMoving = (
                        (coordAnterior.x < intervaloXMenor) ||
                                (coordAnterior.x > intervaloXMaior) ||
                                (coordAnterior.y < intervaloYMenor) ||
                                (coordAnterior.y > intervaloYMaior)
                );

                handleMovimentoMouse();
                run();
            } catch (Exception e) {}
        }
    };

    private Runnable threadTimerDecrescente = new Runnable() {
        public void run() {
            try {
                tempoDecrescente -= 100;
                Thread.sleep(100);
                if (DEBUG && tempoDecrescente % 1000 == 0) System.out.println("Falta para ocioso: " + tempoDecrescente/1000);

                if (tempoDecrescente <= 0)
                    throw new RuntimeException();

                run();
            } catch (Exception e) {
                timerDecrescenteIsRunning = false;
                new Thread(threadTimerCrescente).start();
            }
        }
    };

    private Runnable threadTimerCrescente = new Runnable() {
        public void run() {
            try {
                tempoCrescente += 100;
                Thread.sleep(100);

                if (DEBUG && tempoCrescente % 1000 == 0) System.out.println("Ocioso: " + tempoCrescente/1000);

                if (mouseIsMoving)
                    throw new RuntimeException();

                run();
            } catch (Exception e) {
                System.out.println(usuario);
                new OciosidadeMouseDAO().inserirOciosidadeMouse(new RegistroOciosidadeMouse(tempoCrescente/1000, usuario.getIdUsuario()));
                System.out.println("Inserido %d segundos".formatted(tempoCrescente/1000));
                tempoCrescente = 0;
            }
        }
    };

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
