package monetry.com.monetry;

import java.io.Serializable;

/**
 * Created by KD on 2/19/2018.
 */

public class UsersData implements Serializable{
    private String firebaseUID;
    private String name;
    private String email;
    private int gold;
    private int silver;

    public UsersData(){
    }

    public UsersData(String name,String email,int gold,int silver,String firebaseUID){
        this.name = name;
        this.email = email;
        this.gold = gold;
        this.silver = silver;
        this.firebaseUID = firebaseUID;
    }


    public String getName(){
        return name;
    }
    public String getEmail(){
        return email;
    }
    public int getgold(){
        return gold;
    }
    public int getSilver(){
        return silver;
    }
    public String getFirebaseUID(){
        return firebaseUID;
    }

   public void setGold(int gold){
        this.gold = gold;
   }
   public void setSilver(int silver){
       this.silver = silver;
   }


}
