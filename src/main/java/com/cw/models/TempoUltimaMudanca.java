package com.cw.models;

public class TempoUltimaMudanca {
    private String tempo;
    private Boolean con;

    public TempoUltimaMudanca(String tempo, Boolean con) {
        this.tempo = tempo;
        this.con = con;
    }

    public TempoUltimaMudanca() {
    }

    public String getTempo() {
        return tempo;
    }

    public void setTempo(String tempo) {
        this.tempo = tempo;
    }

    public Boolean getCon() {
        return con;
    }

    public void setCon(Boolean con) {
        this.con = con;
    }

    @Override
    public String toString() {
        return """
                Instabilidade de Conexão Detecada
                -----------------------------------
                Estado: %s
                Duração: %s
                """.formatted(this.con ? "Online" : "Offline", this.tempo);
    }
}
