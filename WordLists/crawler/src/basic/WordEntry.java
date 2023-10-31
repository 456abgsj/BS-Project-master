package basic;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.*;
import java.io.*;
import java.util.Vector;

public class WordEntry {
    public final String word;
    private final String main_url;
    public String pronunciation; // 发音源
    public String symbols; // 音标
    public Vector<String> poses = new Vector<>(); // 词性
    public Vector<Vector<String>> explanations = new Vector<>(); // 词性与释义对应
    public Vector<String> sentences = new Vector<>(); // 例句 中英文对应
    public String antonyms=" "; // 反义词
    public String synonyms=" "; // 近义词

    public WordEntry(final String the_word, final String root_url) {
        word = the_word;
        main_url = root_url + word ;
        pronunciation = "http://dict.youdao.com/dictvoice?audio=" + word;
    }

    public void FetchWordInfo() {
        try {
           /* URL url = new URL(main_url);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String buf;
            while ((buf = in.readLine()) != null) {
                if (buf.contains("\"pronounce\">美")) { // 美式发音
                    buf = in.readLine();
                    if (buf.indexOf('[') == -1)
                        symbols = "[]";
                    else
                        symbols = buf.substring(buf.indexOf("["), buf.indexOf("]") + 1); // 保存音标
                }
                if (buf.contains("pos wordGroup"))  { // 词性
                    // 得到词性 用{}标记
                    String pos = "{" + buf.substring(buf.indexOf(">") + 1, buf.indexOf("</")) + "}";
                    poses.add(pos);
                    Vector<String> exp_of_pos = new Vector<>();
                    while (!(buf = in.readLine()).contains("</ul>")) { // 没有读到下一个词性
                        if (buf.contains("class=\"def\"")) { // 这个词性的某个释义 用++ **标记
                            exp_of_pos.add("++" + buf.substring(buf.indexOf("class=\"def\"") + 12, buf.indexOf("</span>")) + "**");
                        }
                    }
                    explanations.add(exp_of_pos); // 加入这个词性的所有释义
                }
                if (buf.contains("近义词：")) {
                    in.readLine();
                    buf = in.readLine();
                    synonyms = buf.substring(buf.indexOf("hy") + 4, buf.indexOf("</a>"));
                    // flag1 = false;
                }
                if (buf.contains("反义词：")) {
                    in.readLine();
                    buf = in.readLine();
                    antonyms = buf.substring(buf.indexOf("hy") + 4, buf.indexOf("</a>"));
                    // flag2 = false;
                }
                if (buf.contains("<p><span")) { // 一个例句 用++ **标记

                      tring text = buf.replaceAll("</?[^>]+>", ""); // 提取纯文本
                    sentences.add("++" + text.trim() + "**");
                }
                if (buf.contains("更多双语例句")) break;
            }
            in.close();*/
            Document document = (Document) Jsoup.connect(main_url).get();
            //System.out.println(document.text());


            //保存这个词的音标
            Elements element=document.getElementsByClass("phonetic");
            //System.out.println(element.size());
            symbols=element.get(0).text();

            //保存这个词的词性
            String ss=" ";
            Elements link=document.getElementsByClass("trans-container").first().getElementsByTag("li");
            for(Element tran:link){
              String s=tran.text();
              String[] s2=s.split(" ");
              String s3=s2[0].replace("."," ");
              ss="{"+s3+"}";
              poses.add(ss);
              Vector<String> vector=new Vector<>();
               if(s2.length!=1) {
                   vector.add("++" + s2[1] + "**");
               }else{
                   vector.add("++" +" "+"**");
               }
              explanations.add(vector);
            }

            //保存这个词的例句
            String s="";
            Elements elements=document.getElementById("examplesToggle").getElementsByClass("ol").get(0).getElementsByTag("li");
            for(int i=0;i<elements.size();i++){
                Elements elements1=elements.get(i).getElementsByTag("p");
               // System.out.println(elements1.get(0).text());
               // System.out.println(elements1.get(1).text());
                s+="++"+elements1.get(0).text()+"**++"+elements1.get(1).text()+"**";
            }
            sentences.add(s);
            //System.out.println(sentences);
        } catch (Exception e) {
           //System.out.println("error: inside FetchWordInfo(), " + main_url);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        WordEntry w=new WordEntry("orange","http://dict.youdao.com/w/eng/");
        w.FetchWordInfo();
        System.out.println(w.symbols);
        System.out.println(w.poses);
        System.out.println(w.sentences);
        System.out.println(w.explanations);


      //  ++I plucked an orange from the tree.**++ 我从树上摘了一个橙子。**++ Orange is the big colour this year.**++ 橘黄色是今年的流行色。**++ Would you care for some orange juice?**++ 你想要些橙汁吗？**
      //{n}++橙，柑橘；橙汁，橘汁饮料；橙树，橘树；橙色，橘黄色；橙黄色蝴蝶**{adj}++橙红色的，橘黄色的；奥兰治党或社团的**{n}++（Orange）（美、法、俄、英）奥林奇（人名）**
    }
}
