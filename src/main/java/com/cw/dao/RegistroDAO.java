package com.cw.dao;

import com.cw.conexao.Conexao;
import com.cw.models.Registro;
import com.cw.models.Usuario;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class RegistroDAO {
    private final Conexao conexao = new Conexao();
    private final JdbcTemplate con = conexao.getConexaoDoBanco();

    public RegistroDAO() {
    }

    public Registro buscarRegistroPorId(Integer id) {
        String sql = "SELECT * FROM registro WHERE id_registro = %d";
        return con.queryForObject(sql.formatted(id), new BeanPropertyRowMapper<>(Registro.class));
    }

    public void inserirRegistro(Registro r) {
        String sql = "INSERT INTO registro (uso_cpu, uso_ram, disponivel_ram) VALUES (?, ?, ?)";
        con.update(sql, r.getUsoCpu(), r.getUsoRam(), r.getDisponivelRam());
    }
}
