public class PluginPesquisaWeb implements IAcaoAgente {

    @Override
    public String executar(String comando) throws PromptInadequadoException, FalhaProcessamentoAgenteException {
        String comandoLower = comando.toLowerCase();
        
        
        if (comandoLower.contains("Hackear")|| comandoLower.contains("roubar")) {
            throw new PromptInadequadoException("Comando inadequado detectado: " + comando);
        }

        return "Resultado da pesquisa no Google sobre: " + comando;
    }
    
}
