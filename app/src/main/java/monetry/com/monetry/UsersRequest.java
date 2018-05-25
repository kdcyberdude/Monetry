package monetry.com.monetry;

/**
 * Created by KD on 2/19/2018.
 */

public class UsersRequest {
    private String name;
    private String email;
    private String number;
    private Integer gold;
    private Integer silver;
    private boolean request;
    private double rupees;

    public UsersRequest(){
        name = "";
        email = "";
        number = "";
        gold = 0;
        silver = 0;
        request = false;
        rupees = 0.0;
    }

    public UsersRequest(String name,String email,String number,Integer gold,Integer silver,boolean request,double rupees){
        this.name = name;
        this.email = email;
        this.number = number;
        this.gold = gold;
        this.silver = silver;
        this.request = request;
        this.rupees = rupees;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getGold() {
        return gold;
    }

    public void setGold(Integer gold) {
        this.gold = gold;
    }

    public Integer getSilver() {
        return silver;
    }

    public void setSilver(Integer silver) {
        this.silver = silver;
    }

    public boolean isRequest() {
        return request;
    }

    public void setRequest(boolean request) {
        this.request = request;
    }

    public Double getRupees() {
        return rupees;
    }

    public void setRupees(Double rupees) {
        this.rupees = rupees;
    }
}
