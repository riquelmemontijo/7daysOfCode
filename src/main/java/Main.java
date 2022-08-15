import br.com.sevendaysofcode.model.Content;
import br.com.sevendaysofcode.service.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {

        MarvelApiClient marvelApiClient = new MarvelApiClient();
        marvelApiClient.getBody();

        List<String> jsonMovies = new ImdbApiClient().getBody();
        ArrayList<Content> imdbMovies = new ImdbMovieJsonParser(jsonMovies).parse();
        imdbMovies.forEach(System.out::println);

        List<String> marvelSeries = new MarvelApiClient().getBody();
        marvelSeries.forEach(System.out::println);
        ArrayList<Content> series = new MarvelSerieJsonParser(marvelSeries).parse();
        series.forEach(System.out::println);
//        PrintWriter writer = new PrintWriter("content.html");
//        new HtmlGenerator(writer).generate(movies);
//        writer.close();
    }

}



