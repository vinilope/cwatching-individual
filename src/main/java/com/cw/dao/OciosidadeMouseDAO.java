package com.cw.dao;

import com.cw.conexao.Conexao;
import com.cw.models.RegistroOciosidadeMouse;
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
        String sql = "INSERT INTO tempo_ociosidade (tempo_registro_seg, fk_funcionario) VALUES (?, ?)";

        con.update(sql, registro.getTempoRegistroSeg(), registro.getFkFuncionario());
    }

    public List<RegistroOciosidadeMouse> buscarRegistroOciosidadeFuncionario (Integer fkFuncionario) {
        String sql = "SELECT * FROM tempo_ociosidade WHERE fk_funcionario = " + fkFuncionario;
        List<RegistroOciosidadeMouse> registros = con.query(sql, new BeanPropertyRowMapper<>(RegistroOciosidadeMouse.class));

        return registros;
    }

}
