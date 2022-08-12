import com.google.gson.Gson;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {

        List<String> jsonMovies = new ImdbApiClient().callTop250Movies();
        ArrayList<Movie> movies = new ImdbMovieJsonParser(jsonMovies).parse();
        PrintWriter writer = new PrintWriter("content.html");
        new HtmlGenerator(writer).generate(movies);
        writer.close();

    }

}

class Movie {

    private String title;
    private String image;
    private String imDbRating;
    private String year;

    public Movie(String title, String image, String imDbRating, String year) {
        this.title = title;
        this.image = image;
        this.imDbRating = imDbRating;
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImDbRating() {
        return imDbRating;
    }

    public void setImDbRating(String imDbRating) {
        this.imDbRating = imDbRating;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}

class ImdbApiClient {

    public List<String> callTop250Movies() {
        HttpClient client = HttpClient.newHttpClient();
        String apiKey = "<your API KEY>";

        HttpRequest request = HttpRequest.newBuilder(URI.create("https://imdb-api.com/en/API/Top250Movies/" + apiKey))
                .header("Accept", "application/json")
                .timeout(Duration.ofSeconds(10))
                .build();

        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            System.out.println("FALHA NA CHAMADA DA API. MESSAGE: "+ e.getMessage());
        }

        return splitMovies(response.body());
    }

    private List<String> splitMovies(String jsonMovies){

        Matcher matcher = Pattern.compile(".*\\[(.*)\\].*").matcher(jsonMovies);

        if(!matcher.matches()){
            throw new IllegalArgumentException();
        }

        String[] arrayMovies = matcher.group(1).split("},\\{");
        arrayMovies[0] = arrayMovies[0].replace("{", "");
        int lastPosition = arrayMovies.length - 1;
        int lastChar = arrayMovies[lastPosition].length() - 1;
        arrayMovies[lastPosition] = arrayMovies[lastPosition].substring(0, lastChar);

        return Arrays.stream(arrayMovies).collect(Collectors.toList());
    }

}

class ImdbMovieJsonParser {

    private List<String> json;

    public ImdbMovieJsonParser(List<String> json){
        this.json = json;
    }

    public ArrayList<Movie> parse(){

        Gson gson = new Gson().newBuilder().create();

        ArrayList<Movie> movies = new ArrayList<>();

        json.forEach(jsonMovie -> {
            jsonMovie = "{" + jsonMovie + "}";
            movies.add(gson.fromJson(jsonMovie, Movie.class));
        });

        return movies;
    }

}

class HtmlGenerator {

    private final PrintWriter writer;

    public HtmlGenerator(PrintWriter writer) {
        this.writer = writer;
    }

    public void generate(List<Movie> movies) {
        writer.println(
                """
                <html>
                    <head>
                        <meta charset=\"utf-8\">
                        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">
                        <link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css\" 
                                    + "integrity=\"sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm\" crossorigin=\"anonymous\">
                                    
                    </head>
                    <body>
                """);

        for (Movie movie : movies) {
            String div =
                    """
                    <div class=\"card text-white bg-dark mb-3\" style=\"max-width: 18rem;\">
                        <h4 class=\"card-header\">%s</h4>
                        <div class=\"card-body\">
                            <img class=\"card-img\" src=\"%s\" alt=\"%s\">
                            <p class=\"card-text mt-2\">Nota: %s - Ano: %s</p>
                        </div>
                    </div>
                    """;

            writer.println(String.format(div, movie.getTitle(), movie.getImage(), movie.getTitle(), movie.getImDbRating(), movie.getYear()));
        }


        writer.println(
                """
                    </body>
                </html>
                """);
    }

}



