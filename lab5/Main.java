import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        AgenteIA agente = new AgenteIA();


        PluginPesquisaWeb pesquisaWeb = new PluginPesquisaWeb();
        PluginGeradorCodigo geradorCodigo = new PluginGeradorCodigo();


        agente.usarHabilidade(pesquisaWeb, "Como aprender Java");
        agente.usarHabilidade(geradorCodigo, "como aprender Java");
        agente.usarHabilidade(pesquisaWeb, "hackear");
        
    }
}
