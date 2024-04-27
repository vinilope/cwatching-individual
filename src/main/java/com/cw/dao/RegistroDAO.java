package com.cw.dao;

import com.cw.conexao.Conexao;
import com.cw.models.Registro;
import com.cw.models.Sessao;
import com.cw.models.Usuario;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class RegistroDAO {
    private final Conexao conexao = new Conexao();
    private final JdbcTemplate con = conexao.getConexaoDoBanco();

    public RegistroDAO() {
    }

    public Registro buscarUltimoRegistroPorSessao(Sessao s) {
        Registro r = new Registro();
        String sql = "SELECT * FROM registro WHERE fk_sessao = %d ORDER BY dt_hora DESC LIMIT 1".formatted(s.getIdSessao());

        try {
            r = con.queryForObject(sql, new BeanPropertyRowMapper<>(Registro.class));
        } catch (Exception e) {}

        return r;
    }

    public void inserirRegistro(Registro r) {
        String sql = "INSERT INTO registro (uso_cpu, uso_ram, disponivel_ram, conexao_internet, fk_sessao) VALUES (?, ?, ?, ?, ?)";
        con.update(sql, r.getUsoCpu(), r.getUsoRam(), r.getDisponivelRam(), r.getConexaoInternet(), r.getFkSessao());
    }

    public List<Registro> buscarDoisUltimosRegistrosPorSessao(Sessao s) {
        List<Registro> registros = new ArrayList<>();

        try {
            String sql = "SELECT * FROM registro WHERE fk_sessao = %d ORDER BY dt_hora DESC LIMIT 2".formatted(s.getIdSessao());
            registros = con.query(sql, new BeanPropertyRowMapper<>(Registro.class));
        } catch (Exception e) {}

        return registros;
    }
}
