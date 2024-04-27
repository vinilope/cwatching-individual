package com.cw.models;

public class LogConexao {
    private Integer idLogCon;
    private String dtHora;
    private Integer fkRegistro;

    public LogConexao(Integer idLogCon, String dtHora, Integer fkRegistro) {
        this.idLogCon = idLogCon;
        this.dtHora = dtHora;
        this.fkRegistro = fkRegistro;
    }

    public LogConexao() {
    }

    public Integer getIdLogCon() {
        return idLogCon;
    }

    public void setIdLogCon(Integer idLogCon) {
        this.idLogCon = idLogCon;
    }

    public String getDtHora() {
        return dtHora;
    }

    public void setDtHora(String dtHora) {
        this.dtHora = dtHora;
    }

    public Integer getFkRegistro() {
        return fkRegistro;
    }

    public void setFkRegistro(Integer fkRegistro) {
        this.fkRegistro = fkRegistro;
    }

    @Override
    public String toString() {
        return "LogConexao{" +
                "idLogCon=" + idLogCon +
                ", dtHora='" + dtHora + '\'' +
                ", fkRegistro=" + fkRegistro +
                '}';
    }
}
