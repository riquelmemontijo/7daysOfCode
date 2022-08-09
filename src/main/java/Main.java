import com.google.gson.Gson;

import java.io.IOException;
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

        HttpClient client = HttpClient.newHttpClient();
        String apiKey = "<your API KEY>";

        HttpRequest request = HttpRequest.newBuilder(URI.create("https://imdb-api.com/en/API/Top250Movies/" + apiKey))
                .header("Accept", "application/json")
                .timeout(Duration.ofSeconds(5))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ArrayList<Movie> movies = convertJsonToMovie(response.body());

        movies.forEach(m -> System.out.println(m));

    }

    private static List<String> splitMovies(String jsonMovies){

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

    private static ArrayList<Movie> convertJsonToMovie(String json){

        Gson gson = new Gson().newBuilder().create();

        List<String> jsonMovies = splitMovies(json);

        ArrayList<Movie> movies = new ArrayList<>();

        jsonMovies.forEach(jsonMovie -> {
            jsonMovie = "{" + jsonMovie + "}";
            movies.add(gson.fromJson(jsonMovie, Movie.class));
        });

        return movies;
    }

}

