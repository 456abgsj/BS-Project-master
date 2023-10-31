package basic;

public class test2 {
    public static void main(String[] args) {
        String s1="n. 家，住宅；（可买卖的）房子，寓所；家庭；养育院，收容所；适于存放……的地方；发源地，发祥地；栖息地，生息地；家乡，祖国；（组织、公司等的）本部，基地；（比赛的）主场";
        String[] s2=s1.split(" ");
        System.out.println(s2.length);
        System.out.println(s2[0]);
        String s3=s2[0].replace("."," ");
        System.out.println(s3);
    }
}
