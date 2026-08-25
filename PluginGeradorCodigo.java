public class PluginGeradorCodigo implements IAcaoAgente{

    @Override

    public String executar (String comando) throws PromptInadequadoException, FalhaProcessamentoAgenteException {
        if (comando.length()> 50){

            throw new FalhaProcessamentoAgenteException("limite de tokens de código execido" + comando.length() + "caracteres");

        }

        return "Snippet de código Java gerado para: " + comando;
    }
    
}
