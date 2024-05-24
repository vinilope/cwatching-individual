package com.cw.dao;

import com.cw.conexao.Conexao;
import com.cw.models.Empresa;
import com.cw.models.Maquina;
import com.cw.models.Volume;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

public class VolumeDAO extends Conexao {
    MaquinaDAO maquinaDAO = new MaquinaDAO();

    public VolumeDAO() {
    }

    public void inserirVolume(Volume v) {

        String sql = "INSERT INTO volume (uuid, nome, ponto_montagem, volume_total, fk_maquina) VALUES (?, ?, ?, ?, ?)";
        conLocal.update(sql, v.getUUID(), v.getNome(), v.getPontoMontagem(), v.getVolumeTotal(), v.getFkMaquina());
    }
    public void atualizarVolume(Volume v){
        String sql= ("UPDATE volume SET nome = ?, ponto_montagem = ?, volume_total = ? WHERE uuid = ? ");
        conLocal.update(sql, v.getNome(), v.getPontoMontagem(), v.getVolumeTotal(), v.getUUID());
    }

    public List<Volume> buscarVolumesPorMaquina(Maquina m, Empresa e) {
        String sql = "SELECT v.* FROM volume v JOIN maquina ON fk_maquina = id_maquina WHERE hostname = '%s' AND fk_empresa = %d".formatted(m.getHostname(), e.getIdEmpresa());

        return conLocal.query(sql, new BeanPropertyRowMapper<>(Volume.class));
    }

    public Map<String, Object> volumeAlterou(Volume v) {
        String sql = """
                SELECT
                    (SELECT COUNT(*) FROM volume WHERE uuid=?) > 0 AS existe,
                    (SELECT COUNT(*) FROM volume WHERE uuid=? AND nome=? AND ponto_montagem=? AND volume_total=?) = 0 AS alterou
                """;

        return conLocal.queryForMap(sql, v.getUUID(), v.getUUID(), v.getNome(), v.getPontoMontagem(), v.getVolumeTotal());
    }
}
