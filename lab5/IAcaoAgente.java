public interface IAcaoAgente {
    String executar(String comando) throws PromptInadequadoException, FalhaProcessamentoAgenteException;
    
}
