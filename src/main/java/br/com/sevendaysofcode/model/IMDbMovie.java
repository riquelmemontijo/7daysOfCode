package br.com.sevendaysofcode.model;

public class IMDbMovie implements Content{

    private String title;
    private String image;
    private String imDbRating;
    private String year;

    public IMDbMovie(String title, String image, String imDbRating, String year) {
        this.title = title;
        this.image = image;
        this.imDbRating = imDbRating;
        this.year = year;
    }

    @Override
    public String toString() {
        return "IMDbMovie{" +
                "title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", imDbRating='" + imDbRating + '\'' +
                ", year='" + year + '\'' +
                '}';
    }

    @Override
    public String title() {
        return this.title;
    }

    @Override
    public String urlImage() {
        return this.image;
    }

    @Override
    public String rating() {
        return this.imDbRating;
    }

    @Override
    public String year() {
        return this.year;
    }
}
