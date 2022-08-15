package br.com.sevendaysofcode.model;

public class MarvelSerie implements Content{

    private String title;
    private String path;
    private String rating;
    private String startYear;

    public MarvelSerie(String title, String path, String rating, String startYear) {
        this.title = title;
        this.path = path;
        this.rating = rating;
        this.startYear = startYear;
    }

    @Override
    public String toString() {
        return "MarvelSerie{" +
                "title='" + title + '\'' +
                ", path='" + path + '\'' +
                ", rating='" + rating + '\'' +
                ", startYear='" + startYear + '\'' +
                '}';
    }

    @Override
    public String title() {
        return this.title;
    }

    @Override
    public String urlImage() {
        return this.path;
    }

    @Override
    public String rating() {
        return this.rating;
    }

    @Override
    public String year() {
        return this.startYear;
    }
}
