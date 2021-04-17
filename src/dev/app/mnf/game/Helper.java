package dev.app.mnf.game;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class Helper {

    public static TitleCastBean getTitleCastIndex(Element table) {
        TitleCastBean titleCastBean = new TitleCastBean();
        titleCastBean.setCastIndex(-1);
        titleCastBean.setTitleIndex(-1);
        Element tbodyElement = table.getElementsByTag("tbody").get(0);
        Elements allHeaderElements = tbodyElement.getElementsByTag("th");
        int count = 0;
        for (Element element : allHeaderElements) {
            if (element.text().equalsIgnoreCase("title") && titleCastBean.getTitleIndex() == -1)
                titleCastBean.setTitleIndex(count);
            if (element.text().equalsIgnoreCase("cast") && titleCastBean.getCastIndex() == -1)
                titleCastBean.setCastIndex(count);
            int colSpanValue = element.attr("colspan").equals("") ? 0 : Integer.parseInt(element.attr("colspan"));
            if (colSpanValue != 0)
                count += colSpanValue;
            else
                count += 1;
        }
        return titleCastBean;
    }

    public static List<Movie> fetchDetailsFromTable(Element table, TitleCastBean titleCastBean, String input) {
        Elements rowElements = table.getElementsByTag("tr");
        List<Movie> movies = new ArrayList<>();
        for (Element rowElement : rowElements) {
            Elements movieElements = rowElement.getElementsByTag("i");
            Movie movie = new Movie();
            for (Element element : movieElements) {
                List<Node> nodes = element.parent().siblingNodes();
                List<Element> siblings = new ArrayList<>();
                for (Node node : nodes) {
                    if (node instanceof Element)
                        siblings.add((Element) node);
                }
                if (element.parent().elementSiblingIndex() + Math.abs(titleCastBean.getCastIndex() - titleCastBean.getTitleIndex()) - 1 < siblings.size()) {
                    Element castElement = siblings.get(element.parent().elementSiblingIndex() + Math.abs(titleCastBean.getCastIndex() - titleCastBean.getTitleIndex()) - 1);
                    movie.setTitle(element.text());
                    movie.setCast(getCastList(castElement));
                    if (isValidMovie(movie, input)) {
                        movies.add(movie);
                    }
                }
            }
        }
        return movies;
    }

    private static boolean isValidMovie(Movie movie, String input) {
        char heroLetter = input.charAt(0);
        char heroineLetter = input.charAt(1);
        char movieLetter = input.charAt(2);
        if (movie.getTitle().length() > 0 && Character.toLowerCase(movie.getTitle().charAt(0)) == Character.toLowerCase(movieLetter)) {
            System.out.println("TRAIL:        " + movie.getTitle());
            List<String> cast = movie.getCast();
            for (String person : cast) {
                if (person.length() > 0 && (Character.toLowerCase(person.charAt(0)) == Character.toLowerCase(heroLetter) || Character.toLowerCase(person.charAt(0)) == Character.toLowerCase(heroineLetter))) {
                    System.out.println("REAL: " + movie.getTitle() + " Cast: " + movie.getCast());
                    return true;
                }
            }
        }
        return false;
    }

    private static List<String> getCastList(Element castElement) {
        String[] cast = castElement.text().split(",");
        List<String> castList = new ArrayList<>();
        for (String str : cast) {
            castList.add(str);
        }
        return castList;
    }
}
