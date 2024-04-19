package com.cw.models;

public class Sessao {
    private Usuario usuario;
    private Maquina maquina;
    private String dtHoraSessao;

    public Sessao(Usuario usuario, Maquina maquina) {
        this.usuario = usuario;
        this.maquina = maquina;
    }

    public Sessao() {
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Maquina getMaquina() {
        return maquina;
    }

    public void setMaquina(Maquina maquina) {
        this.maquina = maquina;
    }

    public String getDtHoraSessao() {
        return dtHoraSessao;
    }

    public void setDtHoraSessao(String dtHoraSessao) {
        this.dtHoraSessao = dtHoraSessao;
    }

    @Override
    public String toString() {
        return "Sessao{" +
                "usuario=" + usuario +
                ", maquina=" + maquina +
                ", dtHoraSessao='" + dtHoraSessao + '\'' +
                '}';
    }
}
