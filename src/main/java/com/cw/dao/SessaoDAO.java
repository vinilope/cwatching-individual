package com.cw.dao;

import com.cw.conexao.Conexao;
import com.cw.models.Maquina;
import com.cw.models.Usuario;
import org.springframework.jdbc.core.JdbcTemplate;

public class SessaoDAO {
    private final Conexao conexao = new Conexao();
    private final JdbcTemplate con = conexao.getConexaoDoBanco();

    public SessaoDAO() {
    }

    public void registrarSessao(Maquina m, Usuario u) {
        String sql = "INSERT INTO sessao (fk_maquina, fk_usuario) VALUES (?, ?)";
        con.update(sql, m.getIdMaquina(), u.getIdUsuario());
    }
}
