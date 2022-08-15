package br.com.sevendaysofcode.service;

import br.com.sevendaysofcode.model.Content;
import br.com.sevendaysofcode.model.IMDbMovie;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ImdbMovieJsonParser {

    private List<String> json;

    public ImdbMovieJsonParser(List<String> json){
        this.json = json;
    }

    public ArrayList<Content> parse(){

        Gson gson = new Gson().newBuilder().create();

        ArrayList<Content> movies = new ArrayList<>();

        json.forEach(jsonMovie -> {
            jsonMovie = "{" + jsonMovie + "}";
            movies.add(gson.fromJson(jsonMovie, IMDbMovie.class));
        });

        return movies;
    }

}
