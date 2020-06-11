package Bean;

public class MySoldFood {
    private String food;
    private int foodid;
    private String time;
    private String uname;
    private String email;
    private int num;
    private double earn;
    private String boss;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setFoodid(int foodid) {
        this.foodid = foodid;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTime() {
        return time;
    }

    public String getFood() {
        return food;
    }

    public int getFoodid() {
        return foodid;
    }

    public String getEmail() {
        return email;
    }

    public String getBoss() {
        return boss;
    }

    public void setBoss(String boss) {
        this.boss = boss;
    }

    public double getEarn() {
        return earn;
    }

    public String getUname() {
        return uname;
    }

    public void setEarn(double earn) {
        this.earn = earn;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }
}
