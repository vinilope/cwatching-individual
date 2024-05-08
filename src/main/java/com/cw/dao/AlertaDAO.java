package com.cw.dao;

import com.cw.conexao.Conexao;
import com.cw.models.Alerta;
import org.springframework.jdbc.core.JdbcTemplate;

public class AlertaDAO {
    private Conexao conexao = new Conexao();
    private JdbcTemplate con = conexao.getConexaoDoBanco();

    public AlertaDAO() {
    }

    public void inserirAlerta(Alerta l) {
        String sql = "INSERT INTO alerta(tipo, fk_sessao) values (?, ?)";
        con.update(sql, l.getTipo(), l.getFkSessao());
        System.out.println(l);
    }
}
