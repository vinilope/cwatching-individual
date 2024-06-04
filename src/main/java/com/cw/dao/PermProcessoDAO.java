package com.cw.dao;

import com.cw.conexao.Conexao;
import com.cw.models.Config;
import com.cw.models.PermProcesso;
import com.cw.services.LogsService;
import com.mysql.cj.log.Log;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class PermProcessoDAO extends Conexao {
    //TODO: remover formatted

    public PermProcessoDAO() {

    }

    public List<PermProcesso> buscarProcessos(Config c) {
        List<PermProcesso> p = new ArrayList<>();
        try {
            String sql = "SELECT * FROM perm_processo WHERE fk_config = %d".formatted(c.getIdConfig());

            p = conNuvem.query(sql, new BeanPropertyRowMapper<>(PermProcesso.class));
        } catch (Exception e){
            LogsService.gerarLog("Falha ao buscar processos" + e.getMessage());
        }

        return p;
    }

    public void inserirPermProcesso(String p, Config c) {
        String sql = "INSERT INTO perm_processo (nome, fk_config) values (?, ?)";
        try{
            System.out.println("Adicionado: " + p);
            insert(sql, p, c.getIdConfig());

        }catch (Exception e){
            LogsService.gerarLog("Falha ao inserir processo: " + e.getMessage());
        }
    }

    public Boolean verificarPermProcesso(String nome, Config c) {
        Boolean b =false;
        try {
            String sql = "SELECT permitido FROM perm_processo WHERE nome = '%s' AND fk_config = %d AND permitido IS NOT null".formatted(nome, c.getIdConfig());
            List<PermProcesso> permProcesso = conNuvem.query(sql, new BeanPropertyRowMapper<>(PermProcesso.class));
            if (permProcesso.isEmpty()) return null;
            b = permProcesso.get(0).getPermitido();

        }catch (Exception e){
            LogsService.gerarLog("Falha ao verificar processo: "+ e.getMessage());
        }
        return b;
    }
}
