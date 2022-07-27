import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

public class App {

    public static final String BOLD = "\u001b[1m";
    public static final String TEXT_BLUE = "\u001b[34m";
    public static final String TEXT_WHITE = "\u001b[37m";
    private static final String ENTER_WORD_TO_STICKER = "Informe a palavra que deseja escrever na figurinha:";

    public static void main(String[] args) {
        boolean menu = true;
        do {
            Scanner scanner = new Scanner(System.in);
            print(BOLD + TEXT_BLUE +  "Seja Bem-vindo(a) ao criador de figurinhas da Alura");
            print(TEXT_WHITE + "Escolha uma opção: ");
            print("1 - Criar figurinha de TOP Filmes");
            print("2 - Criar figurinha de TOP Séries");
            print("3 - Criar figurinha de Filmes mais populares");
            print("4 - Criar figurinha de Séries mais populares");
            print("5 - Criar figurinha de Linguagens de programação");
            print("6 - Sair");

            var selected = scanner.nextInt();

            if(selected == 1) {
                print(ENTER_WORD_TO_STICKER);
                var word = scanner.next();
                generateIMDBApiStickers(ApiUrlsEnum.IMDB_TOP_MOVIES, word);
            }
            else if(selected == 2) {
                print(ENTER_WORD_TO_STICKER);
                var word = scanner.next();
                generateIMDBApiStickers(ApiUrlsEnum.IMDB_TOP_SERIES, word);
            }
            else if(selected == 3) {
                print(ENTER_WORD_TO_STICKER);
                var word = scanner.next();
                generateIMDBApiStickers(ApiUrlsEnum.IMDB_MOVIES, word);
            }
            else if(selected == 4) {
                print(ENTER_WORD_TO_STICKER);
                var word = scanner.next();
                generateIMDBApiStickers(ApiUrlsEnum.IMDB_SERIES, word);
            }
            else if(selected == 5) {
                print(ENTER_WORD_TO_STICKER);
                var word = scanner.next();

                var url = String.valueOf(ApiUrlsEnum.PROGRAMMING_LANGUAGES.getUrl());
                ContentExtractor extractor = new LanguageContentExtractor();
                HttpApiClient http = new HttpApiClient();
                var json = http.getData(url);
                List<Content> contents = extractor.extractsContent(json);

                var color = selectWordColor();

                callStickerGenerator(contents, word, color);
            }
            else if(selected == 6) {
                print("Saindo..." + "🤟🏻🤟🏻");
                scanner.close();
                menu = false;
            }
            else {
                print("Por favor, escolha uma opção entre 1 e 6.");
            }

        } while (menu);
    }

    private static void generateIMDBApiStickers(ApiUrlsEnum api, String word) {
        var url = String.valueOf(api.getUrl());
        ContentExtractor extractor = new ImdbContentExtractor();
        HttpApiClient http = new HttpApiClient();
        var json = http.getData(url);
        List<Content> contents = extractor.extractsContent(json);
        var color = selectWordColor();
        callStickerGenerator(contents, word, color);
    }

    private static void callStickerGenerator(List<Content> contents, String word, int color) {
        StickerGenerator generator = new StickerGenerator();

        contents.forEach( content -> {
            var title = content.title();
            var fileName = title.replace(":", "-") + ".png";
            try {
                InputStream inputStream = new URL(content.imageUrl()).openStream();
                generator.create(inputStream, fileName, word, color);
                System.out.printf("Figurinha de %s gerada com sucesso!%n", content.title());
            } catch (FileNotFoundException e) {
                print("Imagem não encontrada.");
            } catch (MalformedURLException e) {
                print("Erro inesperado.");
            } catch (IOException e) {
                print("Erro inesperado.");
            }
        });
    }

    private static int selectWordColor() {
        Scanner scanner = new Scanner(System.in);
        print("Escolha uma cor para palavra: ");
        print("1 - Azul");
        print("2 - Laranja");
        print("3 - Rosa");
        print("4 - Vermelho");
        print("5 - Verde");
        return scanner.nextInt();
    }

    public static void print(String s) {
        System.out.println(s);
    }
}
