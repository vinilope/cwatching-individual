package com.cw.dao;

import com.cw.conexao.Conexao;
import com.cw.models.Config;
import com.cw.models.PermProcesso;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class PermProcessoDAO {
    Conexao conexao = new Conexao();
    JdbcTemplate con = conexao.getConexaoDoBanco();

    public PermProcessoDAO() {

    }

    public List<PermProcesso> buscarProcessos(Config c) {
        String sql = "SELECT * FROM perm_processo WHERE fk_config = %d".formatted(c.getIdConfig());

        return con.query(sql, new BeanPropertyRowMapper<>(PermProcesso.class));
    }

    public void inserirPermProcesso(String p, Config c) {
        String sql = "INSERT INTO perm_processo (nome, fk_config) values (?, ?)";
        System.out.println("Adicionado: " + p);
        con.update(sql, p, c.getIdConfig());
    }

    public Boolean verificarPermProcesso(String nome, Config c) {
        String sql = "SELECT permitido FROM perm_processo WHERE nome = '%s' AND fk_config = %d AND permitido IS NOT null".formatted(nome, c.getIdConfig());

        List<PermProcesso> permProcesso = con.query(sql, new BeanPropertyRowMapper<>(PermProcesso.class));

        if (permProcesso.isEmpty()) return null;

        return permProcesso.get(0).getPermitido();
    }
}
