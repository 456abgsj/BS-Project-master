package basic;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.IOException;
import java.net.URL;

public class test {
    public static void main(String[] args) throws IOException {
        String url = "http://dict.youdao.com/w/eng/apple";
        Document document = (Document) Jsoup.connect(url).get();
        //System.out.println(document);
        String title = document.select("title").text();
        Element link=document.getElementsByClass("trans-container").first().getElementsByTag("ul").first();
        System.out.println(link);
    }
}
