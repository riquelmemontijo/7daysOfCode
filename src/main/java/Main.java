import br.com.sevendaysofcode.model.Content;
import br.com.sevendaysofcode.service.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {

        MarvelApiClient marvelApiClient = new MarvelApiClient();
        marvelApiClient.getBody();

        List<String> jsonMovies = new ImdbApiClient().getBody();
        List<Content> imdbMovies = new ImdbMovieJsonParser(jsonMovies).parse();

        List<String> marvelSeries = new MarvelApiClient().getBody();
        List<Content> series = new MarvelSerieJsonParser(marvelSeries).parse();

        List<String> s = new ArrayList<>();

        Collections.sort(imdbMovies);
        Collections.sort(series);

        PrintWriter writerImdb = new PrintWriter("imdb.html");
        PrintWriter writerMarvel = new PrintWriter("marvel.html");

        new HtmlGenerator(writerImdb).generate(imdbMovies);
        new HtmlGenerator(writerMarvel).generate(series);

        writerImdb.close();
        writerMarvel.close();
    }

}



