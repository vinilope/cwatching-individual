package looca;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.discos.Disco;
import com.github.britooo.looca.api.group.discos.DiscoGrupo;
import com.github.britooo.looca.api.group.discos.Volume;
import com.github.britooo.looca.api.group.memoria.Memoria;
import com.github.britooo.looca.api.group.processador.Processador;
import com.github.britooo.looca.api.group.rede.Rede;

import java.util.List;

public class DataGetter {

    //classe utilizada para pegar os dados do looca
    private Looca looca;
    private Memoria memoria;
    private Processador cpu;
    private Rede rede;
    private List<Volume> volumes;

    public DataGetter() {
        this.looca = new Looca();
        this.memoria = looca.getMemoria();
        this.cpu = looca.getProcessador();
        this.rede = looca.getRede();
        this.volumes = looca.getGrupoDeDiscos().getVolumes();
    }

    public Long getRamEmUso() {
        return memoria.getEmUso();
    }

    public Long getRamTotal() {
        return memoria.getTotal();
    }

    public Double getCpuEmUso() {
        return cpu.getUso();
    }

    public String getIp() {
        return rede.getGrupoDeInterfaces()
                .getInterfaces()
                .stream()
                .filter(p -> !p.getEnderecoIpv4().isEmpty())
                .toList()
                .get(0)
                .getEnderecoIpv4()
                .get(0);
    }

    public Long getPacotesRecebidos() {
        return rede.getGrupoDeInterfaces()
                .getInterfaces()
                .stream()
                .filter(p -> !p.getEnderecoIpv4().isEmpty())
                .toList()
                .get(0)
                .getPacotesRecebidos();
    }

    public Long getPacotesEnviados() {
        return rede.getGrupoDeInterfaces()
                .getInterfaces()
                .stream()
                .filter(p -> !p.getEnderecoIpv4().isEmpty())
                .toList()
                .get(0)
                .getPacotesEnviados();
    }

    public String getNomeRede() {
        return rede.getParametros().getNomeDeDominio();
    }

    public Long getBytesRecebidos() {
        return rede.getGrupoDeInterfaces()
                .getInterfaces()
                .stream()
                .filter(p -> !p.getEnderecoIpv4().isEmpty())
                .toList()
                .get(0)
                .getBytesRecebidos();
    }

    public Long getBytesEnviados() {
        return rede.getGrupoDeInterfaces()
                .getInterfaces()
                .stream()
                .filter(p -> !p.getEnderecoIpv4().isEmpty())
                .toList()
                .get(0)
                .getBytesRecebidos();
    }

    public Rede getRede() {
        return rede;
    }

    public List<Volume> getVolumes() {
        return volumes;
    }



}
