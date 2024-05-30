package com.cw.dao;

import com.cw.conexao.Conexao;
import com.cw.models.RegistroOciosidadeMouse;
import com.cw.models.Usuario;
import com.cw.services.LogsService;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

public class OciosidadeMouseDAO extends Conexao {
    //TODO: remover formatted

    public OciosidadeMouseDAO() {

    }

    public Integer inserirOciosidadeMouse(RegistroOciosidadeMouse r) {
        String sql = "INSERT INTO tempo_ociosidade (fk_usuario) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            conLocal.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, r.getFkUsuario());
                return ps;
            }, keyHolder);

        } catch (Exception e) {
            LogsService.gerarLog("Falha ao registrar ociosidade: " + e.getMessage());
        }

        return keyHolder.getKey().intValue();
    }

    public void updateOciosidadeMouse(RegistroOciosidadeMouse r) {
        String sql = "UPDATE tempo_ociosidade SET tempo_registro_ms = ? WHERE id_tempo_ociosidade = ?";
        try {
            conLocal.update(sql, r.getTempoRegistroMs(), r.getIdTempoOciosidade());
            System.out.println("atualizado");
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
