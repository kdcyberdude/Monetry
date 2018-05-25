package monetry.com.monetry;

/**
 * Created by KD on 2/15/2018.
 */

public class Blog {

    private String title;
    private String article;

    public Blog(){

    }

    public Blog(String title , String article){
        this.title = title;
        this.article = article;
    }

    public String getTitle(){
        return title;
    }

    public String getArticle(){
        return article;
    }

    public void setArticle(String article){
        this.article = article;
    }
    public void setTitle(String title){
        this.title = title;
    }


}
