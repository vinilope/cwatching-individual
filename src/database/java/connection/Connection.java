package connection;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class Connection {

    //classe de conexão com o banco de dados
    //altere os dados inseridos do perfil do banco
    //com o seu respectivo perfil e senha nas linhas 18 e 19

    private JdbcTemplate dbConnection;
    BasicDataSource dataSource = new BasicDataSource();

    public Connection() {
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/cwdb");
        dataSource.setUsername("root");
        dataSource.setPassword("4#d20+15+1d6");

        dbConnection = new  JdbcTemplate(dataSource);
    }

    public JdbcTemplate getDbConnection() {
        return dbConnection;
    }
}
