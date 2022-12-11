package ua.com.prologistic;

public class Movie {
    private String name;
    private Integer year;
    private String genre;
    private Float rating;
    private String overview;
    private String director;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {this.year = year;}

    public String getGenre() {return genre;}

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {this.rating = rating;}

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

    public boolean equals(Movie a){
        return this.getName().equals(a.getName()) &&
                this.getYear().equals(a.getYear()) &&
                this.getGenre().equals(a.getGenre()) &&
                this.getOverview().equals(a.getOverview()) &&
                this.getDirector().equals(a.getDirector());
    }
}
