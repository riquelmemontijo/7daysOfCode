package br.com.sevendaysofcode.service;

import br.com.sevendaysofcode.model.Content;
import br.com.sevendaysofcode.model.MarvelSerie;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MarvelSerieJsonParser {

    private List<String> json;

    public MarvelSerieJsonParser(List<String> json){
        this.json = json;
    }

    public ArrayList<Content> parse(){

        Gson gson = new Gson();

        ArrayList<Content> series = new ArrayList<>();

        json.forEach(jsonMovie -> {
            series.add(gson.fromJson(jsonMovie, MarvelSerie.class));
        });

        return series;

    }

}
