public class AgenteImagem extends AgenteIA {

    private static final String[] TERMOS_SENSIVEIS = {
        "hackear", "roubar", "biometrico", "biométrico"
    };

    public AgenteImagem(String nome) {
        super(nome);
    }

    @Override
    public void processarRequisicao(String input)
            throws FalhaProcessamentoAgenteException, PromptInadequadoException, ErroComunicacaoIAException {

        conectarServidor();
        this.status = "PROCESSING";

        String inputNormalizado = input.toLowerCase();
        for (String termo : TERMOS_SENSIVEIS) {
            if (inputNormalizado.contains(termo)) {
                this.status = "BLOCKED";
                throw new PromptInadequadoException("Prompt bloqueado por conter termo sensivel: \"" + termo + "\".");
            }
        }

        System.out.println("Agente de Imagem [" + nome + "] sintetizando pixels para: " + input);
        this.status = "IDLE";
    }
}
