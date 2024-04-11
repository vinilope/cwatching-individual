
import com.github.britooo.looca.api.group.discos.Disco;
import com.github.britooo.looca.api.group.discos.DiscoGrupo;
import com.github.britooo.looca.api.group.discos.Volume;
import connection.Connection;
import looca.DTO;
import looca.DataGetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class DbMain {


    public static void main(String[] args) throws InterruptedException {

        //aviso: execute o script do banco no workbench ou no terminal
        //antes de executar o código, a única parte realmente necessária
        //é a tabela "maquina".

        //criando as conexões
        Connection connection = new Connection();
        JdbcTemplate con = connection.getDbConnection();
        DataGetter data = new DataGetter();


        //permanência dos dados dentro do banco
        while(true) {
            con.update("""
                            insert into registro
                            (uso_cpu,
                            uso_ram,
                            disponivel_ram,
                            ipv4,
                            pacotes_recebidos,
                            fk_maquina)
                            values (?, ?, ?, ?, ?, null)
                            """, data.getCpuEmUso(),
                    data.getRamEmUso(),
                    data.getRamTotal(),
                    data.getIp(),
                    data.getPacotesRecebidos()
            );


            DTO idRegistro = con.queryForObject("select id_registro from registro order by id_registro desc limit 1",
                    new BeanPropertyRowMapper<>(DTO.class));

            System.out.println(idRegistro);

            con.update("""
                            insert into rede
                            (nome,
                            fk_registro,
                            ipv4,
                            bytes_recebidos,
                            bytes_enviados,
                            pacotes_recebidos,
                            pacotes_enviados)
                            values (?,?,?,?,?,?,?)
                            """, data.getNomeRede(),
                    idRegistro.toString(),
                    data.getIp(),
                    data.getBytesRecebidos(),
                    data.getBytesEnviados(),
                    data.getPacotesRecebidos(),
                    data.getPacotesEnviados()
            );

            List<Volume> volumes = data.getVolumes();

            for(Volume volume : volumes) {
                con.update("""
                        insert into volume
                        (fk_registro,
                        nome,
                        ponto_montagem,
                        volume_total,
                        volume_disponivel)
                        values(?,?,?,?,?)
                        """,
                        idRegistro.toString(),
                        volume.getNome(),
                        volume.getPontoDeMontagem(),
                        volume.getTotal(),
                        volume.getDisponivel());
            }


            Thread.sleep(1000);

            //a coluna fk_maquina recebe um valor nulo
            //por enquanto, ela seria em teoria hard-codada
            //em cada máquina de acordo com a regra de negócio
            //baseada até então.

        }
    }
}
