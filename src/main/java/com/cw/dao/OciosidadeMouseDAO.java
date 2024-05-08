package com.cw.dao;

import com.cw.conexao.Conexao;
import com.cw.models.RegistroOciosidadeMouse;
import com.cw.models.Usuario;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class OciosidadeMouseDAO {
    private Conexao conexao;
    private JdbcTemplate con;

    public OciosidadeMouseDAO() {
        this.conexao = new Conexao();
        this.con = conexao.getConexaoDoBanco();
    }

    public void inserirOciosidadeMouse(RegistroOciosidadeMouse registro) {
        String sql = "INSERT INTO tempo_ociosidade (tempo_registro_ms, fk_usuario) VALUES (?, ?)";

        con.update(sql, registro.getTempoRegistroMs(), registro.getFkUsuario());
    }

    public RegistroOciosidadeMouse buscarUltimoRegistroOciosidadePorUsuario (Usuario u) {
        String sql = "SELECT * FROM tempo_ociosidade WHERE fk_usuario = %d ORDER BY dt_hora_registro DESC LIMIT 1".formatted(u.getIdUsuario());
        RegistroOciosidadeMouse registros = con.queryForObject(sql, new BeanPropertyRowMapper<>(RegistroOciosidadeMouse.class));

        return registros;
    }

}
