public class ModuloConexao {

    public static void validarLink() throws ErroComunicacaoIAException {
        double valor = Math.random();

        if (valor > 0.8) {
            throw new ErroComunicacaoIAException(
                "Link de comunicacao com o servidor perdido. Valor sorteado=" + String.format("%.2f", valor)
            );
        }
    }
}
