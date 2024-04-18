package com.cw.models;

import org.h2.util.DateTimeUtils;

public class RegistroOciosidadeMouse {
    private int idTempoOciosidade;
    private String dtHoraRegistro;
    private int tempoRegistroSeg;
    private int fkFuncionario;

    public RegistroOciosidadeMouse(int tempoRegistroSeg, int fkFuncionario) {
        this.tempoRegistroSeg = tempoRegistroSeg;
        this.fkFuncionario = fkFuncionario;
    }

    public RegistroOciosidadeMouse() {
    }

    public int getTempoRegistroSeg() {
        return tempoRegistroSeg;
    }

    public int getIdTempoOciosidade() {
        return idTempoOciosidade;
    }

    public void setIdTempoOciosidade(int idTempoOciosidade) {
        this.idTempoOciosidade = idTempoOciosidade;
    }

    public String getDtHoraRegistro() {
        return dtHoraRegistro;
    }

    public void setDtHoraRegistro(String dtHoraRegistro) {
        this.dtHoraRegistro = dtHoraRegistro;
    }

    public void setTempoRegistroSeg(int tempoRegistroSeg) {
        this.tempoRegistroSeg = tempoRegistroSeg;
    }

    public int getFkFuncionario() {
        return fkFuncionario;
    }

    public void setFkFuncionario(int fkFuncionario) {
        this.fkFuncionario = fkFuncionario;
    }

    @Override
    public String toString() {
        return "RegistroOciosidadeMouse{" +
                "idTempoOciosidade=" + idTempoOciosidade +
                ", dtHoraRegistro='" + dtHoraRegistro + '\'' +
                ", tempoRegistroSeg=" + tempoRegistroSeg +
                ", fkFuncionario=" + fkFuncionario +
                '}';
    }
}
