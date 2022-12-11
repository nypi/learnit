package ua.com.prologistic;

public class Movie {
    private String name;
    private String year;
    private String genre;
    private String rating;
    private String overview;
    private String director;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {this.year = year;}

    public String getGenre() {return genre;}

    public void setGenre(String role) {
        this.genre = genre;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String year) {this.rating = rating;}

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    @Override
    public String toString() {
        return "\nTitle=" + getName() + "\nOverview:" + getOverview() + "\n";
    }
}
