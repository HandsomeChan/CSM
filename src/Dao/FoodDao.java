package Dao;

import Bean.FoodInfo;
import Bean.Foods;
import com.mysql.cj.xdevapi.SqlDataResult;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FoodDao {
    //推荐系统top4
    public static List<Foods> recommend(String uname){
        Connection con=C3p0Util.getconn();
        List<Foods> foods=new ArrayList<>();
        String sql="select * from foods INNER JOIN lookLogs on foods.type=lookLogs.type where uname=? order by length desc limit 1,4";
        try {
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setObject(1,uname);
            ResultSet rs=ps.executeQuery();
            while (rs.next()){
                Foods food=new Foods();
                food.setId(rs.getInt("id"));
                food.setFoodname(rs.getString("foodname"));
                food.setBoss(rs.getString("boss"));
                food.setNum(rs.getInt("num"));
                food.setPrice(rs.getDouble("price"));
                food.setIntroduction(rs.getString("introduction"));
                food.setPicture(rs.getString("picture"));
                food.setSold(rs.getInt("sold"));
                food.setType(rs.getString("type"));
                foods.add(food);
            }
            rs.close();
            ps.close();
            con.close();
            return foods;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            C3p0Util.close(con);
        }
        return foods;
    }
    //根据id找到店家联系方式
    public static String findboss(int id){
        Connection con=C3p0Util.getconn();
        String email="";
        String sql="select email from foods,users where foods.boss=users.uname and foods.id=?";
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps=con.prepareStatement(sql);
            ps.setObject(1,id);
            rs=ps.executeQuery();
            while (rs.next()){
                email=rs.getString("email");
            }
            rs.close();
            ps.close();
            con.close();
            return email;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            C3p0Util.close(con);
        }
        return email;
    }
    //管理员的商品列表
    public static ArrayList<Foods> ManGetfoods() {
        ArrayList<Foods> foods = new ArrayList<>();
        Connection con = C3p0Util.getconn();
        String sql1 = "select * from foods order by sold desc";
        PreparedStatement ps;
        try {
            ps = con.prepareStatement(sql1);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Foods food = new Foods();
                food.setId(rs.getInt("id"));
                food.setFoodname(rs.getString("foodname"));
                food.setBoss(rs.getString("boss"));
                food.setNum(rs.getInt("num"));
                food.setPrice(rs.getDouble("price"));
                food.setIntroduction(rs.getString("introduction"));
                food.setPicture(rs.getString("picture"));
                food.setSold(rs.getInt("sold"));
                food.setType(rs.getString("type"));
                foods.add(food);
            }
            rs.close();
            ps.close();
            con.close();
            return foods;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            C3p0Util.close(con);
        }
        return foods;
    }
    //商家的所有商品
    public static int getBossNum(String boss,String type) {
        int count=0;
        Connection con = C3p0Util.getconn();
        String sql = "select count(0) from foods where type=? and boss=?";
        String sql1 = "select count(0) from foods where boss=?";
        PreparedStatement ps;
        try {
            if (type.equals("全部")) {
                ps = con.prepareStatement(sql1);
                ps.setObject(1,boss);
            } else {
                ps = con.prepareStatement(sql);
                ps.setObject(1, type);
                ps.setObject(2,boss);
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                count=rs.getInt("count(0)");
            }
            rs.close();
            ps.close();
            con.close();
            return count;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            C3p0Util.close(con);
        }
        return 0;
    }
    //删除商品
    public static boolean deleFood(int id){
        Connection con=C3p0Util.getconn();
        String sql="delete from foods where id=?";
        try {
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setObject(1,id);
            ps.executeLargeUpdate();
            ps.close();
            return true;
        } catch (SQLException e) {
            try {
                con.rollback(); //回滚此次链接的所有操作
            } catch (SQLException e1) {
                e1.printStackTrace();
        }
            }finally {
            C3p0Util.close(con);
        }
        return false;
    }
    //插入商品
    public static boolean addFood(int id,String boss,String name,Double price,String type,String intro,String pic,int num){
        Connection con=C3p0Util.getconn();
        String sql="insert foods(id,foodname,boss,num,price,`type`,sold,picture,introduction) values (?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setObject(1,id);
            ps.setObject(2,name);
            ps.setObject(3,boss);
            ps.setObject(4,num);
            ps.setObject(5,price);
            ps.setObject(6,type);
            ps.setObject(7,0);
            ps.setObject(8,pic);
            ps.setObject(9,intro);
            ps.executeLargeUpdate();
            ps.close();
            return true;
        } catch (SQLException e) {
            try {
                con.rollback(); //回滚此次链接的所有操作
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        return false;
    }
    //更新商品信息
    public static boolean updateInfo(int id,String name,Double price,String type,String intro,String pic,int num){
        Connection con=C3p0Util.getconn();
        String sql="update foods set num=num+?,foodname=?,price=?,type=?,introduction=?,picture=? where id=?";
        try {
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setObject(1,num);
            ps.setObject(2,name);
            ps.setObject(3,price);
            ps.setObject(4,type);
            ps.setObject(5,intro);
            ps.setObject(6,pic);
            ps.setObject(7,id);
            ps.executeLargeUpdate();
            ps.close();
            return true;
        } catch (SQLException e) {
            try {
                con.rollback(); //回滚此次链接的所有操作
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        return false;
    }
    //商家的食品页面
    public static ArrayList<Foods> getMyFoods(String uname,String type,int begin,int size){
        ArrayList<Foods> foods=new ArrayList<>();
        Connection con=C3p0Util.getconn();
        String sql="select * from foods where type=? and boss=? limit ?,?";
        String sql1="select * from foods where boss=? limit ?,?";
        PreparedStatement ps;
        try{
            if (type.equals("全部")){
                ps=con.prepareStatement(sql1);
                ps.setObject(1,uname);
                ps.setObject(2, begin);
                ps.setObject(3, size);
            }
            else {
                ps = con.prepareStatement(sql);
                ps.setObject(1, type);
                ps.setObject(2,uname);
                ps.setObject(3, begin);
                ps.setObject(4, size);
            }
            ResultSet rs=ps.executeQuery();
            while (rs.next()){
                Foods food=new Foods();
                food.setId(rs.getInt("id"));
                food.setFoodname(rs.getString("foodname"));
                food.setBoss(rs.getString("boss"));
                food.setNum(rs.getInt("num"));
                food.setPrice(rs.getDouble("price"));
                food.setIntroduction(rs.getString("introduction"));
                food.setPicture(rs.getString("picture"));
                food.setSold(rs.getInt("sold"));
                food.setType(rs.getString("type"));
                foods.add(food);
//                System.out.println(food.getFoodname());
            }
//            System.out.println("我好了");
            rs.close();
            ps.close();
            con.close();
            return foods;
        }catch (SQLException e){
            e.printStackTrace();
        } finally {
            C3p0Util.close(con);
        }
        return foods;
    }
    //查找食品剩余数量
    public static int getRest(int id){
        Connection con=C3p0Util.getconn();
        int num=0;
        String sql="select num from foods where id=?";
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps=con.prepareStatement(sql);
            ps.setObject(1,id);
            rs=ps.executeQuery();
            while (rs.next()){
                num=rs.getInt("num");
            }
            rs.close();
            ps.close();
            con.close();
            return num;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            C3p0Util.close(con);
        }
        return 0;
    }
    //购买后食物数量变化
    public static boolean buyFood(Connection con,int id,int num){
        String sql="update foods set num=num-?,sold=sold+? where id=?";
        try {
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setObject(1,num);
            ps.setObject(2,num);
            ps.setObject(3,id);
            ps.executeLargeUpdate();
            ps.close();
            return true;
        } catch (SQLException e) {
            try {
                con.rollback(); //回滚此次链接的所有操作
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        return false;
    }
    //根据id找到商品信息
    public static Foods getFood(int id){
        Connection con=C3p0Util.getconn();
        Foods f=new Foods();
        String sql="select * from foods where id=?";
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps=con.prepareStatement(sql);
            ps.setObject(1,id);
            rs=ps.executeQuery();
            while (rs.next()){
                f.setId(rs.getInt("id"));
                f.setFoodname(rs.getString("foodname"));
                f.setBoss(rs.getString("boss"));
                f.setPrice(rs.getDouble("price"));
                f.setNum(rs.getInt("num"));
                f.setSold(rs.getInt("sold"));
                f.setType(rs.getString("type"));
                f.setPicture(rs.getString("picture"));
                f.setIntroduction(rs.getString("introduction"));
            }
            rs.close();
            ps.close();
            con.close();
            return f;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            C3p0Util.close(con);
        }
        return f;
    }
    //根据id找到购物车信息
    public static FoodInfo getInfo(int id){
        Connection con=C3p0Util.getconn();
        FoodInfo fi=new FoodInfo();
        String sql="select * from foods where id=?";
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps=con.prepareStatement(sql);
            ps.setObject(1,id);
            rs=ps.executeQuery();
            while (rs.next()){
                fi.setId(rs.getInt("id"));
                fi.setFoodname(rs.getString("foodname"));
                fi.setBoss(rs.getString("boss"));
                fi.setPrice(rs.getDouble("price"));
            }
            rs.close();
            ps.close();
            con.close();
            return fi;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            C3p0Util.close(con);
        }
        return fi;
    }
    //获得商品的个数
    public static int getNum(String type) {
        int count=0;
        Connection con = C3p0Util.getconn();
        String sql = "select count(0) from foods where type=?";
        String sql1 = "select count(0) from foods";
        PreparedStatement ps;
        try {
            if (type.equals("精品推荐")) {
                ps = con.prepareStatement(sql1);
            } else {
                ps = con.prepareStatement(sql);
                ps.setObject(1, type);
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                count=rs.getInt("count(0)");
            }
            rs.close();
            ps.close();
            con.close();
            return count;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            C3p0Util.close(con);
        }
        return 0;
    }
        //获得商品列表
    public static ArrayList<Foods> getfoods(String type,int begin,int size){
        ArrayList<Foods> foods=new ArrayList<>();
        Connection con=C3p0Util.getconn();
        String sql="select * from foods where type=? order by sold desc limit ?,?";
        String sql1="select * from foods order by sold desc limit ?,?";
        PreparedStatement ps;
        try{
            if (type.equals("精品推荐")){
                ps=con.prepareStatement(sql1);
                ps.setObject(1, begin);
                ps.setObject(2, size);
            }
            else {
                ps = con.prepareStatement(sql);
                ps.setObject(1, type);
                ps.setObject(2, begin);
                ps.setObject(3, size);
            }
            ResultSet rs=ps.executeQuery();
            while (rs.next()){
                Foods food=new Foods();
                food.setId(rs.getInt("id"));
                food.setFoodname(rs.getString("foodname"));
                food.setBoss(rs.getString("boss"));
                food.setNum(rs.getInt("num"));
                food.setPrice(rs.getDouble("price"));
                food.setIntroduction(rs.getString("introduction"));
                food.setPicture(rs.getString("picture"));
                food.setSold(rs.getInt("sold"));
                food.setType(rs.getString("type"));
                foods.add(food);
//                System.out.println(food.getFoodname());
            }
//            System.out.println("我好了");
            rs.close();
            ps.close();
            con.close();
            return foods;
        }catch (SQLException e){
            e.printStackTrace();
        } finally {
            C3p0Util.close(con);
        }
        return foods;
    }
}
