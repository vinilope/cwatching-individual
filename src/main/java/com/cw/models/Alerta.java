package com.cw.models;

public class Alerta {
    private Integer idAlerta;
    private String dtHora;
    private String tipo;
    private Integer fkSessao;

    public Alerta(String tipo, Integer fkSessao) {
        this.tipo = tipo;
        this.fkSessao = fkSessao;
    }

    public Alerta() {
    }

    public Integer getIdAlerta() {
        return idAlerta;
    }

    public void setIdAlerta(Integer idAlerta) {
        this.idAlerta = idAlerta;
    }

    public String getDtHora() {
        return dtHora;
    }

    public void setDtHora(String dtHora) {
        this.dtHora = dtHora;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getFkSessao() {
        return fkSessao;
    }

    public void setFkSessao(Integer fkSessao) {
        this.fkSessao = fkSessao;
    }

    @Override
    public String toString() {
        return "InserirAlerta{" +
                "idAlerta=" + idAlerta +
                ", dtHora='" + dtHora + '\'' +
                ", tipo='" + tipo + '\'' +
                ", fkSessao=" + fkSessao +
                '}';
    }
}
