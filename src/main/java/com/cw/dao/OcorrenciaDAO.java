package com.cw.dao;

import com.cw.conexao.Conexao;
import com.cw.models.Ocorrencia;
import org.springframework.jdbc.core.JdbcTemplate;

public class OcorrenciaDAO {
    private Conexao conexao = new Conexao();
    private JdbcTemplate con = conexao.getConexaoDoBanco();

    public OcorrenciaDAO() {
    }

    public void inserirOcorrencia(Ocorrencia o) {
        String sql = "INSERT INTO ocorrencia (titulo, descricao, tipo, fk_sessao) VALUES (?, ?, ?, ?)";
        System.out.println(o);
        con.update(sql, o.getTitulo(), o.getDescricao(), o.getTipo(), o.getFkSessao());
    }
}
