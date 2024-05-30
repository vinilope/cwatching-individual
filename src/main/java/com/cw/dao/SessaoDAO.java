package com.cw.dao;

import com.cw.conexao.Conexao;
import com.cw.models.Empresa;
import com.cw.models.Maquina;
import com.cw.models.Sessao;
import com.cw.models.Usuario;
import com.cw.services.LogsService;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class SessaoDAO extends Conexao {
    //TODO: remover formatted

    public SessaoDAO() {
    }

    public void registrarSessao(Integer fkMaquina, Integer fkUsuario) {
        String sql = "INSERT INTO sessao (fk_maquina, fk_usuario) VALUES (?, ?)";

        try {
            insert(sql, fkMaquina, fkUsuario);
        } catch(Exception e) {
            LogsService.gerarLog("Falha ao registrar sessão: " + e.getMessage());
        }
    }

    public Sessao buscarUltimaSessaoPorMaquina(Integer fkMaquina) {
        String sql = "SELECT * FROM sessao WHERE fk_maquina = %d ORDER BY dt_hora_sessao DESC LIMIT 1".formatted(fkMaquina);
        Sessao s;

        try {
            s = conLocal.queryForObject(sql, new BeanPropertyRowMapper<>(Sessao.class));
        } catch (Exception e) {
            LogsService.gerarLog("Falha ao buscar última sessão por máquina: " + e.getMessage());
            return null;
        }

        return s;
    }
}
