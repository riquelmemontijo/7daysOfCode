package br.com.sevendaysofcode.model;

public class MarvelSerie implements Content{

    private String title;
    private Thumbnail thumbnail;
    private String rating;
    private String startYear;

    public MarvelSerie(String title, Thumbnail thumbnail, String rating, String startYear) {
        this.title = title;
        this.thumbnail.setPath(thumbnail.getPath());
        this.rating = rating;
        this.startYear = startYear;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "MarvelSerie{" +
                "title='" + title + '\'' +
                ", path='" + thumbnail.getPath() + '\'' +
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
        return this.thumbnail.getPath();
    }

    @Override
    public String rating() {
        if(this.rating.equals("")){
            this.rating = "No rating";
        }
        return this.rating;
    }

    @Override
    public String year() {
        return this.startYear;
    }

    @Override
    public int compareTo(Content otherContent) {
        return this.rating.compareTo(otherContent.rating());
    }
}
