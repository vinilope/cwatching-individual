package com.cw.models;

public class Usuario {
    private String username;
    private String senha;
    private String dtCriado;
    private Boolean ativo;

    public Usuario(String username, String senha, String dtCriado, Boolean ativo) {
        this.username = username;
        this.senha = senha;
        this.dtCriado = dtCriado;
        this.ativo = ativo;
    }

    public Usuario() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getDtCriado() {
        return dtCriado;
    }

    public void setDtCriado(String dtCriado) {
        this.dtCriado = dtCriado;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "username='" + username + '\'' +
                ", senha='" + senha + '\'' +
                ", dtCriado='" + dtCriado + '\'' +
                ", ativo=" + ativo +
                '}';
    }
}
