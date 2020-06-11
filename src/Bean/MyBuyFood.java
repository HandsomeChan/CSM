package Bean;

public class MyBuyFood {
    private String email;
    private String time;
    private String boss;
    private int foodid;
    private String food;
    private int num;
    private double allpay;

    public void setNum(int num) {
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    public void setBoss(String boss) {
        this.boss = boss;
    }

    public String getBoss() {
        return boss;
    }

    public String getEmail() {
        return email;
    }

    public double getAllpay() {
        return allpay;
    }

    public int getFoodid() {
        return foodid;
    }

    public String getFood() {
        return food;
    }

    public String getTime() {
        return time;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public void setAllpay(double allpay) {
        this.allpay = allpay;
    }

    public void setFoodid(int foodid) {
        this.foodid = foodid;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
