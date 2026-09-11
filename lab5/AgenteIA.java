public class AgenteIA {

    public void usarHabilidade(IAcaoAgente ferramenta, String comando){
        try{
            String resultado = ferramenta.executar(comando);
            System.out.println(resultado);
        } catch (PromptInadequadoException | FalhaProcessamentoAgenteException e) {
            System.out.println("Erro ao executar habilidade: " + e.getMessage());

        }

    }
}