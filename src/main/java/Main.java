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

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder(URI.create("https://imdb-api.com/en/API/Top250Movies/k_2pik6goe")) //substitua "<API KEY>" pela sua key do IMDB
                .header("Accept", "application/json")
                .timeout(Duration.ofSeconds(5))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        List<String> arrayMovies = splitMovies(response.body());

        List<String> arrayTitles = getTitles(arrayMovies);
        List<String> arrayURLImages = getURLImages(arrayMovies);

        System.out.println("\n------ MOVIES --------\n");
        arrayTitles.forEach(System.out::println);
        System.out.println("\n------ URL IMAGE --------\n");
        arrayURLImages.forEach(System.out::println);

    }

    private static List<String> splitMovies(String jsonMovies){

        Matcher matcher = Pattern.compile(".*\\[(.*)\\].*").matcher(jsonMovies);
        String formatedMovies;

        if(!matcher.matches()){
            throw new IllegalArgumentException();
        }
        else {
            formatedMovies = matcher.group(1);
        }

        String[] arrayMovies = formatedMovies.split("},\\{");
        arrayMovies[0] = arrayMovies[0].replace("{", "");
        int lastPosition = arrayMovies.length - 1;
        int lastChar = arrayMovies[lastPosition].length() - 1;
        arrayMovies[lastPosition] = arrayMovies[lastPosition].substring(0, lastChar);

        return Arrays.stream(arrayMovies).collect(Collectors.toList());
    }

    private static List<String> getTitles(List<String> arrayMovies){
        List<String> arrayTitle = arrayMovies.stream()
                .map(m -> m.split("\",\"")[3])
                .map(m -> m.split("\":\"")[1]).collect(Collectors.toList());

        return arrayTitle;
    }

    private static List<String> getURLImages(List<String> arrayMovies){
        List<String> arrayURLImage = arrayMovies.stream()
                .map(m -> m.split("\",\"")[5])
                .map(m -> m.split("\":\"")[1]).collect(Collectors.toList());

        return arrayURLImage;
    }

}