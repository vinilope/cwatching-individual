package com.cw.models;

public class ParametroAlerta {
    private Integer idParametro;
    private Double maxCpu;
    private Double maxRam;
    private Double maxVolume;
    private Integer sensisbilidadeMouse;
    private Integer registroMouseSeg;
    private Integer registroMaquinaSeg;

    public ParametroAlerta(Integer idParametro, Double maxCpu, Double maxRam, Double maxVolume, Integer sensisbilidadeMouse, Integer registroMouseSeg, Integer registroMaquinaSeg) {
        this.idParametro = idParametro;
        this.maxCpu = maxCpu;
        this.maxRam = maxRam;
        this.maxVolume = maxVolume;
        this.sensisbilidadeMouse = sensisbilidadeMouse;
        this.registroMouseSeg = registroMouseSeg;
        this.registroMaquinaSeg = registroMaquinaSeg;
    }

    public ParametroAlerta() {
    }

    public Integer getIdParametro() {
        return idParametro;
    }

    public void setIdParametro(Integer idParametro) {
        this.idParametro = idParametro;
    }

    public Double getMaxCpu() {
        return maxCpu;
    }

    public void setMaxCpu(Double maxCpu) {
        this.maxCpu = maxCpu;
    }

    public Double getMaxRam() {
        return maxRam;
    }

    public void setMaxRam(Double maxRam) {
        this.maxRam = maxRam;
    }

    public Double getMaxVolume() {
        return maxVolume;
    }

    public void setMaxVolume(Double maxVolume) {
        this.maxVolume = maxVolume;
    }

    public Integer getSensisbilidadeMouse() {
        return sensisbilidadeMouse;
    }

    public void setSensisbilidadeMouse(Integer sensisbilidadeMouse) {
        this.sensisbilidadeMouse = sensisbilidadeMouse;
    }

    public Integer getRegistroMouseSeg() {
        return registroMouseSeg;
    }

    public void setRegistroMouseSeg(Integer registroMouseSeg) {
        this.registroMouseSeg = registroMouseSeg;
    }

    public Integer getRegistroMaquinaSeg() {
        return registroMaquinaSeg;
    }

    public void setRegistroMaquinaSeg(Integer registroMaquinaSeg) {
        this.registroMaquinaSeg = registroMaquinaSeg;
    }
}
