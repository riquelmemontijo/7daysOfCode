package br.com.sevendaysofcode.service;

import br.com.sevendaysofcode.model.Content;
import br.com.sevendaysofcode.model.JsonParser;
import br.com.sevendaysofcode.model.MarvelSerie;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MarvelSerieJsonParser implements JsonParser {

    private List<String> json;

    public MarvelSerieJsonParser(List<String> json){
        this.json = json;
    }

    @Override
    public ArrayList<Content> parse(){

        Gson gson = new Gson();

        ArrayList<Content> marvelSeries = new ArrayList<>();

        json.forEach(jsonMovie -> {
            marvelSeries.add(gson.fromJson(jsonMovie, MarvelSerie.class));
        });

        return marvelSeries;

    }

}
