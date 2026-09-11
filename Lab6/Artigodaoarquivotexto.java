import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class ArtigoDAOArquivoTexto implements ArtigoDAO {

    private final Path arquivo;

    public ArtigoDAOArquivoTexto(Path arquivo) {
        this.arquivo = arquivo;
    }

    @Override
    public void salvar(ArtigoCientifico artigo) {
        try {
            if (arquivo.getParent() != null) {
                Files.createDirectories(arquivo.getParent());
            }
            Files.writeString(
                    arquivo,
                    artigo.toLinha() + System.lineSeparator(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar artigo no arquivo " + arquivo, e);
        }
    }

    @Override
    public List<ArtigoCientifico> listarTodos() {
        List<ArtigoCientifico> artigos = new ArrayList<>();

        if (!Files.exists(arquivo)) {
            return artigos;
        }

        try (BufferedReader reader = Files.newBufferedReader(arquivo)) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (!linha.isBlank()) {
                    artigos.add(ArtigoCientifico.fromLinha(linha));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler o arquivo " + arquivo, e);
        }

        return artigos;
    }

    @Override
    public ArtigoCientifico buscarPorId(int id) {
        if (!Files.exists(arquivo)) {
            return null;
        }

        try (BufferedReader reader = Files.newBufferedReader(arquivo)) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (linha.isBlank()) {
                    continue;
                }
                ArtigoCientifico artigo = ArtigoCientifico.fromLinha(linha);
                if (artigo.id() == id) {
                    return artigo;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler o arquivo " + arquivo, e);
        }

        return null;
    }

    public boolean verificarLink(String link) {
        if (link == null || link.isBlank()) {
            return false;
        }

        try {
            HttpClient client = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(5))
                    .build();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(link))
                    .method("HEAD", HttpRequest.BodyPublishers.noBody())
                    .timeout(Duration.ofSeconds(5))
                    .build();

            HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
            return response.statusCode() >= 200 && response.statusCode() < 400;
        } catch (Exception e) {
            return false;
        }
    }

    public Path baixarAnexo(ArtigoCientifico artigo, Path diretorioDestino) throws IOException, InterruptedException {
        String link = artigo.link();
        if (link == null || link.isBlank()) {
            throw new IllegalArgumentException("Artigo " + artigo.id() + " não possui link definido");
        }
        if (!verificarLink(link)) {
            throw new IOException("Link inacessível para o artigo " + artigo.id() + ": " + link);
        }

        Files.createDirectories(diretorioDestino);

        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(link))
                .GET()
                .timeout(Duration.ofSeconds(30))
                .build();

        HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());

        if (response.statusCode() < 200 || response.statusCode() >= 300) {
            throw new IOException("Falha ao baixar anexo do artigo " + artigo.id() + ": HTTP " + response.statusCode());
        }

        String nomeArquivo = "artigo_" + artigo.id() + extrairExtensao(link);
        Path destino = diretorioDestino.resolve(nomeArquivo);

        try (InputStream in = response.body()) {
            Files.copy(in, destino, StandardCopyOption.REPLACE_EXISTING);
        }

        return destino;
    }

    private String extrairExtensao(String link) {
        String semQuery = link.split("[?#]", 2)[0];
        int ultimoPonto = semQuery.lastIndexOf('.');
        int ultimaBarra = semQuery.lastIndexOf('/');
        if (ultimoPonto > ultimaBarra && ultimoPonto != -1) {
            return semQuery.substring(ultimoPonto);
        }
        return ".pdf";
    }
}