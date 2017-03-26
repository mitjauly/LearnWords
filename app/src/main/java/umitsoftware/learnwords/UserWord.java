package umitsoftware.learnwords;

/**
 * Created by umitsoftware on 1/31/2017.
 */
public class UserWord {
    public int Id;
    public String RuWord;
    public String EnWord;
    public int RuCount;
    public int EnCount;
    public UserWord(int id,String enword,String ruword, int encount, int rucount){
        Id=id;
        RuWord =ruword;
        EnWord=enword;
        RuCount=rucount;
        EnCount=encount;
    }
}
