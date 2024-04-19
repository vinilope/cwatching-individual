package com.cw.models;

public class Registro {
    private Integer idRegistro;
    private String dtHora;
    private Double usoCpu;
    private Long usoRam;
    private Long disponivelRam;
    private Integer fkMaquina;

    public Registro(Double usoCpu, Long usoRam, Long disponivelRam) {
        this.usoCpu = usoCpu;
        this.usoRam = usoRam;
        this.disponivelRam = disponivelRam;
    }

    public Integer getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(Integer idRegistro) {
        this.idRegistro = idRegistro;
    }

    public String getDtHora() {
        return dtHora;
    }

    public void setDtHora(String dtHora) {
        this.dtHora = dtHora;
    }

    public Double getUsoCpu() {
        return usoCpu;
    }

    public void setUsoCpu(Double usoCpu) {
        this.usoCpu = usoCpu;
    }

    public Long getUsoRam() {
        return usoRam;
    }

    public void setUsoRam(Long usoRam) {
        this.usoRam = usoRam;
    }

    public Long getDisponivelRam() {
        return disponivelRam;
    }

    public void setDisponivelRam(Long disponivelRam) {
        this.disponivelRam = disponivelRam;
    }

    public Integer getFkMaquina() {
        return fkMaquina;
    }

    public void setFkMaquina(Integer fkMaquina) {
        this.fkMaquina = fkMaquina;
    }

    @Override
    public String toString() {
        return "Registro{" +
                "idRegistro=" + idRegistro +
                ", dtHora='" + dtHora + '\'' +
                ", usoCpu=" + usoCpu +
                ", usoRam=" + usoRam +
                ", disponivelRam=" + disponivelRam +
                ", fkMaquina=" + fkMaquina +
                '}';
    }
}
