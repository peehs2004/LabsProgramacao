public class AgenteTexto extends AgenteIA {

    public AgenteTexto(String nome) {
        super(nome);
    }

    @Override
    public void processarRequisicao(String input)
            throws FalhaProcessamentoAgenteException, PromptInadequadoException, ErroComunicacaoIAException {

        conectarServidor();
        this.status = "PROCESSING";

        if (input.length() > 500) {
            this.status = "ERROR";
            throw new FalhaProcessamentoAgenteException(
                "Estouro de Contexto: prompt possui " + input.length() + " caracteres."
            );
        }

        System.out.println("Agente de Texto [" + nome + "] gerando resposta para: " + input);
        this.status = "IDLE";
    }
}
