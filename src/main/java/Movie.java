public class Movie {

    private String title;
    private String image;
    private String imDbRating;
    private String year;

    public Movie(String title, String image, String imDbRating, String year) {
        this.title = title;
        this.image = image;
        this.imDbRating = imDbRating;
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImDbRating() {
        return imDbRating;
    }

    public void setImDbRating(String imDbRating) {
        this.imDbRating = imDbRating;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", urlImage='" + image + '\'' +
                ", imDbRating='" + imDbRating + '\'' +
                ", year='" + year + '\'' +
                '}';
    }
}
