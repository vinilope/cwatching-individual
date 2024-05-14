package com.cw.dao;

import com.cw.conexao.Conexao;
import com.cw.models.Empresa;
import com.cw.models.Maquina;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class MaquinaDAO {
    private Conexao conexao = new Conexao();
    private JdbcTemplate con = conexao.getConexaoDoBanco();

    public MaquinaDAO() {
    }

    public void inserirMaquina(Maquina m, Empresa e) {
        if (!verificarMaquinaExistePorHostnameEEmpresa(m.getHostname(), e)) {
            String sql = "INSERT INTO maquina (so, cpu_modelo, ram_total, hostname, fk_empresa) VALUES (?, ?, ?, ?, ?)";
            con.update(sql, m.getSo(), m.getCpu(), m.getRam(), m.getHostname(), m.getFkEmpresa());
        } else if (!verificarComponentesMaquinaExistente(m)) {
            atualizarMaquina(m);
        }
    }


    public Boolean verificarComponentesMaquinaExistente(Maquina m) {
        String sql = ("SELECT * FROM maquina WHERE " +
                "hostname = '%s' AND " +
                "cpu_modelo = '%s' OR " +
                "ram_total = %d OR" +
                " so = '%s'").formatted(m.getHostname(), m.getCpu(), m.getRam(), m.getSo());
        con.query(sql, new BeanPropertyRowMapper<>(Maquina.class)).isEmpty();
        return !con.query(sql, new BeanPropertyRowMapper<>(Maquina.class)).isEmpty();
    }

    private void atualizarMaquina(Maquina m) {
        String sql = ("UPDATE maquina SET so = '%s', cpu_modelo = '%s', ram_total = %d " +
                "WHERE hostname = '%s'").formatted(m.getSo(), m.getCpu(), m.getCpu(), m.getRam(), m.getHostname());
        con.update(sql, m.getSo(), m.getCpu(), m.getRam(), m.getHostname());

    }

    public Boolean verificarMaquinaExistePorHostnameEEmpresa(String hostname, Empresa e) {
        String sql = "SELECT hostname FROM maquina WHERE hostname = '%s' AND fk_empresa = %d".formatted(hostname, e.getIdEmpresa());
        return !con.query(sql, new BeanPropertyRowMapper<>(Maquina.class)).isEmpty();


    }

    public Maquina buscarMaquinaPorHostnameEEmpresa(String hostname, Empresa e) {
        String sql = "SELECT * FROM maquina WHERE hostname = '%s' AND fk_empresa = %d".formatted(hostname, e.getIdEmpresa());
        return con.queryForObject(sql, new BeanPropertyRowMapper<>(Maquina.class));
    }
}
