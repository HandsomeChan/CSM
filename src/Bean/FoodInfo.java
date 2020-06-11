package Bean;

public class FoodInfo {
    private int id;
    private String foodname;
    private double price;
    private int num;
    private String boss;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public int getNum() {
        return num;
    }

    public String getBoss() {
        return boss;
    }

    public String getFoodname() {
        return foodname;
    }

    public void setBoss(String boss) {
        this.boss = boss;
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
