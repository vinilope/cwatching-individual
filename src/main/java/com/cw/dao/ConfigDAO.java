package com.cw.dao;

import com.cw.conexao.Conexao;
import com.cw.models.Config;
import com.cw.models.Empresa;
import com.cw.models.PermProcesso;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class ConfigDAO {
    private final Conexao conexao = new Conexao();
    private final JdbcTemplate con = conexao.getConexaoDoBanco();

    public Config buscarConfigPorEmpresa(Empresa e) {
        String sql = "SELECT * FROM config WHERE id_config = %d".formatted(e.getIdEmpresa());

        return con.queryForObject(sql, new BeanPropertyRowMapper<>(Config.class));
    }

    public List<PermProcesso> buscarPermProcessosPorConfig(Config c) {
        String sql = "SELECT * FROM perm_processo WHERE fk_config = %d".formatted(c.getIdConfig());

        return con.query(sql, new BeanPropertyRowMapper<>(PermProcesso.class));
    }
}
