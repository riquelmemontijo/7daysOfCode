package br.com.sevendaysofcode.service;

import br.com.sevendaysofcode.model.APIClient;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ImdbApiClient implements APIClient {

    @Override
    public List<String> getBody() {
        HttpClient client = HttpClient.newHttpClient();
        String apiKey = "k_2pik6goe";

        HttpRequest request = HttpRequest.newBuilder(URI.create("https://imdb-api.com/en/API/Top250Movies/" + apiKey))
                .header("Accept", "application/json")
                .timeout(Duration.ofSeconds(10))
                .build();

        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            System.out.println("FALHA NA CHAMADA DA API DA IMDB. MESSAGE: " + e.getMessage());
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
