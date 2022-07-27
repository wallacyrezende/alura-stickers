import java.util.List;

public class GetMoviesAndSeries {
    private static final String BOLD = "\u001b[1m";
    private static final String ITALIC = "\u001b[3m";
    private static final String TEXT_GREEN = "\u001b[32m";
    private static final String TEXT_BLUE = "\u001b[34m";
    private static final String TEXT_MAGENTA = "\u001b[35m";
    private static final String TEXT_CIANO = "\u001b[36m";
    private static final String TEXT_WHITE = "\u001b[37m";
    private static final String TOP_250_MOVIES_IMDB = "Top 250 Filmes - IMDb";
    private static final String MOST_POPULAR_MOVIES_IMDB = "Filmes mais populares - IMDb";
    private static final String MOST_POPULAR_SERIES_IMDB = "Séries mais populares - IMDb";
    private static final String MOST_TOP_SERIES_IMDB = "Top séries mais populares - IMDb";

    private static void showTopMovies() {
        App.print(ITALIC + TEXT_MAGENTA + TOP_250_MOVIES_IMDB);
        var getTopMovies = getMovies();
        getExtractedContent(getTopMovies);
    }

    private static void showPopularMovies() {
        App.print(ITALIC + TEXT_GREEN + MOST_POPULAR_MOVIES_IMDB);
        getExtractedContent(getPopularMovies());
    }

    private static void showPopularSeries() {
        App.print(ITALIC + TEXT_GREEN + MOST_POPULAR_SERIES_IMDB);
        getExtractedContent(getPopularSeries());
    }

    private static void showTopSeries() {
        App.print(ITALIC + TEXT_MAGENTA + MOST_TOP_SERIES_IMDB);
        getExtractedContent(getSeries());
    }

    private static void getExtractedContent(String getContent) {
        ContentExtractor extractor = new ImdbContentExtractor();
        List<Content> contents = extractor.extractsContent(getContent);

        contents.forEach( content -> {
            App.print(BOLD + TEXT_WHITE + "Title: " + TEXT_CIANO + content.title());
            App.print(BOLD + TEXT_WHITE + "Poster: " + TEXT_BLUE + content.imageUrl());
        });
    }
    private static String getMovies() {
        HttpApiClient http = new HttpApiClient();
        return http.getData(ApiUrlsEnum.IMDB_MOVIES.getUrl());
    }

    private static String getPopularMovies()  {
        HttpApiClient http = new HttpApiClient();
        return http.getData(ApiUrlsEnum.IMDB_TOP_MOVIES.getUrl());
    }


    private static String getSeries() {
        HttpApiClient http = new HttpApiClient();
        return http.getData(ApiUrlsEnum.IMDB_TOP_SERIES.getUrl());
    }

    private static String getPopularSeries()  {
        HttpApiClient http = new HttpApiClient();
        return http.getData(ApiUrlsEnum.IMDB_SERIES.getUrl());
    }

    public static void main(String[] args) {
        showTopMovies();
        showPopularMovies();
        showTopSeries();
        showPopularSeries();
    }
}
