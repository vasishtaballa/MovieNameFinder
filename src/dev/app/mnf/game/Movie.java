package dev.app.mnf.game;

import java.util.List;

public class Movie {
    private String title;
    private List<String> cast;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getCast() {
        return cast;
    }

    public void setCast(List<String> cast) {
        this.cast = cast;
    }

    @Override
    public String toString() {
        return "Movie {" +
                "title='" + title + '\'' +
                ", cast=" + cast +
                '}';
    }
}
