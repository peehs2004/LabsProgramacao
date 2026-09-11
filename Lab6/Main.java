import java.nio.file.Path;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        Path arquivo = Path.of("artigos.txt");
        ArtigoDAOArquivoTexto dao = new ArtigoDAOArquivoTexto(arquivo);

        dao.salvar(new ArtigoCientifico(1, "Attention Is All You Need", "Vaswani et al.",
                "Apresenta a arquitetura Transformer", "deep learning, nlp", "Nenhuma",
                "https://arxiv.org/pdf/1706.03762"));

        dao.salvar(new ArtigoCientifico(2, "BERT", "Devlin et al.",
                "Pre-treino bidirecional com Transformers", "bert, nlp, transformers", "1",
                "https://arxiv.org/pdf/1810.04805"));

        dao.salvar(new ArtigoCientifico(3, "LoRA", "Hu et al.",
                "Fine-tuning eficiente com matrizes de baixo posto", "fine-tuning, llm", "1, 2",
                "https://arxiv.org/pdf/2106.09685"));

        List<ArtigoCientifico> todos = dao.listarTodos();
        todos.forEach(System.out::println);

        ArtigoCientifico artigo = dao.buscarPorId(2);
        System.out.println("Busca por id 2: " + artigo);

        ArtigoCientifico inexistente = dao.buscarPorId(99);
        System.out.println("Busca por id 99: " + inexistente);

        Path destino = dao.baixarAnexo(todos.get(0), Path.of("anexos"));
        System.out.println("Arquivo baixado em: " + destino.toAbsolutePath());
    }
}