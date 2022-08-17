package br.com.sevendaysofcode.model;

import java.util.List;

public interface JsonParser {
    public List<? extends Content> parse();
}
