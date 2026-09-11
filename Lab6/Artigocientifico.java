import java.util.Arrays;

public record ArtigoCientifico(
        int id,
        String titulo,
        String autores,
        String resumo,
        String palavrasChave,
        String referencias,
        String link
) {

    private static final String SEPARADOR = " \\| ";
    private static final String JUNCAO = " | ";

    public String toLinha() {
        return String.join(JUNCAO,
                String.valueOf(id),
                titulo,
                autores,
                resumo,
                palavrasChave,
                referencias,
                link == null ? "" : link);
    }

    public static ArtigoCientifico fromLinha(String linha) {
        String[] campos = linha.split(SEPARADOR, -1);
        if (campos.length < 6) {
            throw new IllegalArgumentException("Linha em formato inválido: " + linha);
        }

        int id = Integer.parseInt(campos[0].trim());
        String titulo = campos[1].trim();
        String autores = campos[2].trim();
        String resumo = campos[3].trim();
        String palavrasChave = campos[4].trim();
        String referencias = campos[5].trim();
        String link = campos.length >= 7 ? campos[6].trim() : "";

        return new ArtigoCientifico(id, titulo, autores, resumo, palavrasChave, referencias, link);
    }

    @Override
    public String toString() {
        return "ArtigoCientifico" + Arrays.toString(new Object[]{id, titulo, autores, resumo, palavrasChave, referencias, link});
    }
}