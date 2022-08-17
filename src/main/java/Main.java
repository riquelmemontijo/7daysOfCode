import br.com.sevendaysofcode.model.Content;
import br.com.sevendaysofcode.service.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {

        List<Content> imdbMovies = new ImdbMovieJsonParser(new ImdbApiClient().getBody()).parse();

        List<Content> marvelSeries = new MarvelSerieJsonParser(new MarvelApiClient().getBody()).parse();

        imdbMovies.forEach(System.out::println);

        Collections.sort(imdbMovies);
        Collections.sort(marvelSeries);

        PrintWriter writerImdb = new PrintWriter("imdb.html");
        PrintWriter writerMarvel = new PrintWriter("marvel.html");

        new HtmlGenerator(writerImdb).generate(imdbMovies);
        new HtmlGenerator(writerMarvel).generate(marvelSeries);

        writerImdb.close();
        writerMarvel.close();
    }

}



