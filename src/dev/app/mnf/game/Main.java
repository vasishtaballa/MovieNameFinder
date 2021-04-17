package dev.app.mnf.game;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Give me input: ");
            String input = scanner.next();
            File file = new File("G:\\GIT_REPO\\MovieNameFinder\\MovieNameFinder\\urls.txt");
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println("--------------------------------" + line + "------------------------------------------------------");
                Document document = Jsoup.connect(line).get();
                Elements elementsByClass = document.getElementsByClass("wikitable");
                final Iterator<Element> tableIterator = elementsByClass.iterator();
                while (tableIterator.hasNext()) {
                    Element table = tableIterator.next();
                    TitleCastBean titleCastBean = Helper.getTitleCastIndex(table);
                    List<Movie> movies = Helper.fetchDetailsFromTable(table, titleCastBean, input);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
