package br.com.sevendaysofcode.service;

import br.com.sevendaysofcode.model.APIClient;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MarvelApiClient implements APIClient {

    private static final String apiPublicKey = "168b184b1e4271416639094ef8e1fe34";
    private static final String apiPrivateKey = "c096f7395746c4ebfbb32df312de82a5f116d621";

    @Override
    public List<String> getBody() {
        return splitMovies();
    }

    private String callApiMarvelSeries(){
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest
                .newBuilder(
                        URI.create(String.format("http://gateway.marvel.com/v1/public/series?ts=%s&apikey=%s&hash=%s", getTimestamp(), apiPublicKey, generateHashMD5())))
                .header("Accept", "application/json")
                .timeout(Duration.ofSeconds(10))
                .build();

        HttpResponse<String> response = null;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            System.out.println("FALHA NA CHAMADA DA API DA MARVEL: " + e.getMessage());
        }

        return response.body();
    }

    private List<String> splitMovies() {

        String body = callApiMarvelSeries();

        Matcher matcher = Pattern.compile(".*\"results\":\\[\\{(.*)\\}\\]\\}\\}").matcher(body);

        if(!matcher.matches()){
            throw new IllegalArgumentException("JSON MARVEL RECEBIDO É IMCOMPATÍVEL");
        }

        String results = matcher.group(1);
        String[] arrayMarvelSeries = results.split("\\},\\{\"id\"");
        arrayMarvelSeries[0] = arrayMarvelSeries[0].replace("\"id\"", "");

        List<String> listMarvelSeries = Arrays.stream(arrayMarvelSeries).collect(Collectors.toList());

        return formatterToJsonList(listMarvelSeries);
    }

    private ArrayList<String> formatterToJsonList(List<String> listMarvelSeries){

        ArrayList<String> jsonSeries = new ArrayList<>();
        listMarvelSeries.forEach(s -> {
            s = "{\"id\"" + s + "}";
            jsonSeries.add(s);
        });

        return jsonSeries;
    }

    private String generateHashMD5() {

        String hash = getTimestamp() + apiPrivateKey + apiPublicKey;

        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            System.out.println("ERRO NA GERAÇÃO DO HASH: " + e.getMessage());
        }
        md.update(hash.getBytes(),0,hash.length());
        return new BigInteger(1,md.digest()).toString(16);

    }

    private static String getTimestamp(){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return String.valueOf(timestamp.getTime());
    }

}
