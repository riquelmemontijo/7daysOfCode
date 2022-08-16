package br.com.sevendaysofcode.service;

import br.com.sevendaysofcode.model.Content;
import br.com.sevendaysofcode.model.IMDbMovie;
import br.com.sevendaysofcode.model.JsonParser;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ImdbMovieJsonParser implements JsonParser {

    private List<String> json;

    public ImdbMovieJsonParser(List<String> json){
        this.json = json;
    }

    @Override
    public ArrayList<Content> parse(){

        Gson gson = new Gson().newBuilder().create();

        ArrayList<Content> imdbMovies = new ArrayList<>();

        json.forEach(jsonMovie -> {
            imdbMovies.add(gson.fromJson(jsonMovie, IMDbMovie.class));
        });

        return imdbMovies;
    }

}
