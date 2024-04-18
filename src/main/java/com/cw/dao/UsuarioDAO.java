package com.cw.dao;

import com.cw.conexao.Conexao;
import com.cw.models.Usuario;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class UsuarioDAO {
    private Conexao conexao;
    private JdbcTemplate con;

    public UsuarioDAO() {
        this.conexao = new Conexao();
        this.con = conexao.getConexaoDoBanco();
    }

    public Usuario buscarUsuarioPorUsername(String username) {

        Usuario usuario = con.queryForObject("SELECT * FROM usuario WHERE username = '%s'".formatted(username), new BeanPropertyRowMapper<>(Usuario.class));

        return usuario;
    }

    public Boolean autenticarLogin(String username, String senha) {

        List<Usuario> usuario = con.query("SELECT * FROM usuario WHERE username = '%s' AND senha = '%s'".formatted(username, senha), new BeanPropertyRowMapper<>(Usuario.class));

        return usuario.size() == 1;
    }
}
