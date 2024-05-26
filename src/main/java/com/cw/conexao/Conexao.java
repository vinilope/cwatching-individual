package com.cw.conexao;

import com.cw.services.LogsService;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class Conexao {
    public final JdbcTemplate conLocal;
    public final JdbcTemplate conNuvem;

    public Conexao() {
        this.conLocal = setConexaoLocal();
        this.conNuvem = setConexaoNuvem();

        testarConexao(conLocal);
//        testarConexao(conNuvem);
    }

    private JdbcTemplate setConexaoLocal() {


            BasicDataSource dataSource = new BasicDataSource();
            dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
            dataSource.setUrl("jdbc:mysql://localhost:3306/cwdb");
            dataSource.setUsername("root");
            dataSource.setPassword("root");

            return new JdbcTemplate(dataSource);
    }

    private JdbcTemplate setConexaoNuvem() {

         BasicDataSource dataSource = new BasicDataSource();
         dataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
         dataSource.setUrl("jdbc:sqlserver://54.198.160.133:1433;database=cwdb;trustServerCertificate=true");
         dataSource.setUsername("sa");
         dataSource.setPassword("cwc@2024");

         return new JdbcTemplate(dataSource);
    }

    private static void testarConexao(JdbcTemplate con) {
        try {
            con.queryForObject("SELECT 1", Integer.class);
        } catch (Exception e) {
            LogsService.gerarLog("Falha ao estabelecer conexão com o MYsql: " + e);
        }
    }

}

