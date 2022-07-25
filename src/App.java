import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

public class App {

    public static void main(String[] args) throws IOException, InterruptedException {
        var url = "https://api.mocki.io/v2/549a5d8b";
        var address = URI.create(url);
        HttpClient client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(address).GET().build();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        var body = response.body();

        var parser = new JsonParser();
        List<Map<String,String>> movies = parser.parse(body);

        movies.forEach(movie -> {
            System.out.println(movie.get("title"));
            System.out.println(movie.get("image"));
            System.out.println(movie.get("imDbRating"));
            System.out.println();
        });
    }
}
