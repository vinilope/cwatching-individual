package com.cw.dao;

import com.cw.conexao.Conexao;
import com.cw.models.Registro;
import com.cw.models.TempoUltimaMudanca;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class LogConexaoDAO {
    private final Conexao conexao = new Conexao();
    private final JdbcTemplate con = conexao.getConexaoDoBanco();

    public LogConexaoDAO() {
    }

    public void inserirLogConexaoPorRegistro(Registro r) {
        String sql =  "INSERT INTO log_conexao (estado_con, fk_registro) VALUES (?, ?)";
        con.update(sql, r.getConexaoInternet(), r.getIdRegistro());
    }

    public TempoUltimaMudanca buscarDuracaoUltimaMudanca() {
        String sql = """
                select
                
                TIMEDIFF(
                    (select dt_hora from log_conexao order by dt_hora desc limit 1),
                    (select dt_hora from log_conexao
                        where estado_con <> (select estado_con from log_conexao order by dt_hora desc limit 1) 
                    order by dt_hora desc limit 1)
                ) as tempo,
                        
                (select estado_con from log_conexao 
                    where estado_con <> (select estado_con from log_conexao order by dt_hora desc limit 1) 
                    order by dt_hora desc limit 1) as con
                """;

        return con.queryForObject(sql, new BeanPropertyRowMapper<>(TempoUltimaMudanca.class));
    }
}

