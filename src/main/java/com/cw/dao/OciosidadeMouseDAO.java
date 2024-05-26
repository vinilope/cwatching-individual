package com.cw.dao;

import com.cw.conexao.Conexao;
import com.cw.models.RegistroOciosidadeMouse;
import com.cw.models.Usuario;
import com.cw.services.LogsService;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class OciosidadeMouseDAO extends Conexao {
    //TODO: remover formatted

    public OciosidadeMouseDAO() {

    }

    public void inserirOciosidadeMouse(RegistroOciosidadeMouse registro) {
        String sql = "INSERT INTO tempo_ociosidade (tempo_registro_ms, fk_usuario) VALUES (?, ?)";

        try {
            conLocal.update(sql, registro.getTempoRegistroMs(), registro.getFkUsuario());
        } catch (Exception e) {
            LogsService.gerarLog("Falha ao registrar ociosidade: " + e.getMessage());
        }

    }

    public RegistroOciosidadeMouse buscarUltimoRegistroOciosidadePorUsuario (Usuario u) {
        RegistroOciosidadeMouse r = new RegistroOciosidadeMouse();
        try {
            String sql = "SELECT * FROM tempo_ociosidade WHERE fk_usuario = %d ORDER BY dt_hora_registro DESC LIMIT 1".formatted(u.getIdUsuario());
            RegistroOciosidadeMouse registros = conLocal.queryForObject(sql, new BeanPropertyRowMapper<>(RegistroOciosidadeMouse.class));
            r = registros;
        } catch (Exception e){
            LogsService.gerarLog("Falha ao buscar ultima ociosidade: " + e.getMessage());
        }
        return r;
    }
}
