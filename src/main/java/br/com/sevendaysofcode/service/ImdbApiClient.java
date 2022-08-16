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
        return splitMovies(callImdbApi());
    }

    private String callImdbApi(){

        HttpClient client = HttpClient.newHttpClient();
        String apiKey = "<your API KEY from IMDb>";

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

        return response.body();

    }

    private List<String> splitMovies(String jsonMovies){

        Matcher matcher = Pattern.compile(".*\\[(.*)\\].*").matcher(jsonMovies);

        if(!matcher.matches()){
            throw new IllegalArgumentException();
        }

        String[] arrayImdbMovies = matcher.group(1).split("},\\{");
        arrayImdbMovies[0] = arrayImdbMovies[0].replace("{", "");
        int lastPosition = arrayImdbMovies.length - 1;
        int lastChar = arrayImdbMovies[lastPosition].length() - 1;
        arrayImdbMovies[lastPosition] = arrayImdbMovies[lastPosition].substring(0, lastChar);

        List<String> listImdbMovies = Arrays.stream(arrayImdbMovies).collect(Collectors.toList());

        return formatterToJsonList(listImdbMovies);
    }

    private List<String> formatterToJsonList(List<String> listImdbMovies){

        listImdbMovies.forEach(movie ->{
            movie = "{" + movie + "}";
        });

        return listImdbMovies;
    }

}
