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
    public Connection con=null;
    //推荐系统top4
    public List<Foods> recommend(String uname){
        PreparedStatement ps=null;
        ResultSet rs=null;
        List<Foods> foods=new ArrayList<>();
        String sql="select * from foods INNER JOIN lookLogs on foods.type=lookLogs.type where uname=? order by length desc limit 1,4";
        try {
            con=C3p0Util.getconn();
            ps=con.prepareStatement(sql);
            ps.setObject(1,uname);
            rs=ps.executeQuery();
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
            return foods;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            C3p0Util.close(rs,ps,con);
        }
        return foods;
    }
    //根据id找到店家联系方式
    public String findboss(int id){
        String email="";
        String sql="select email from foods,users where foods.boss=users.uname and foods.id=?";
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
            con=C3p0Util.getconn();
            ps=con.prepareStatement(sql);
            ps.setObject(1,id);
            rs=ps.executeQuery();
            while (rs.next()){
                email=rs.getString("email");
            }
            return email;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            C3p0Util.close(rs,ps,con);
        }
        return email;
    }
    //管理员的商品列表
    public ArrayList<Foods> ManGetfoods() {
        ArrayList<Foods> foods = new ArrayList<>();
        Connection con = null;
        String sql1 = "select * from foods order by sold desc";
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
            con=C3p0Util.getconn();
            ps = con.prepareStatement(sql1);
            rs = ps.executeQuery();
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
            return foods;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            C3p0Util.close(rs,ps,con);
        }
        return foods;
    }
    //商家的所有商品
    public int getBossNum(String boss,String type) {
        int count=0;
        Connection con = null;
        String sql = "select count(0) from foods where type=? and boss=?";
        String sql1 = "select count(0) from foods where boss=?";
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
            con=C3p0Util.getconn();
            if (type.equals("全部")) {
                ps = con.prepareStatement(sql1);
                ps.setObject(1,boss);
            } else {
                ps = con.prepareStatement(sql);
                ps.setObject(1, type);
                ps.setObject(2,boss);
            }
            rs = ps.executeQuery();
            while (rs.next()){
                count=rs.getInt("count(0)");
            }
            return count;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            C3p0Util.close(rs,ps,con);
        }
        return 0;
    }
    //删除商品
    public boolean deleFood(int id){
        PreparedStatement ps=null;
        ResultSet rs=null;
        String sql="delete from foods where id=?";
        try {
            con=C3p0Util.getconn();
            ps=con.prepareStatement(sql);
            ps.setObject(1,id);
            ps.executeLargeUpdate();
            ps.close();
            return true;
        } catch (Exception e) {
            try {
                con.rollback(); //回滚此次链接的所有操作
            } catch (Exception e1) {
                e1.printStackTrace();
        }
            }finally {
            C3p0Util.close(rs,ps,con);
        }
        return false;
    }
    //插入商品
    public boolean addFood(int id,String boss,String name,Double price,String type,String intro,String pic,int num){
        String sql="insert foods(id,foodname,boss,num,price,`type`,sold,picture,introduction) values (?,?,?,?,?,?,?,?,?)";
        try {
            con=C3p0Util.getconn();
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
        } catch (Exception e) {
            try {
                con.rollback(); //回滚此次链接的所有操作
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return false;
    }
    //更新商品信息
    public boolean updateInfo(int id,String name,Double price,String type,String intro,String pic,int num){
        String sql="update foods set num=num+?,foodname=?,price=?,type=?,introduction=?,picture=? where id=?";
        try {
            con=C3p0Util.getconn();
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
        } catch (Exception e) {
            try {
                con.rollback(); //回滚此次链接的所有操作
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return false;
    }
    //商家的食品页面
    public ArrayList<Foods> getMyFoods(String uname,String type,int begin,int size){
        ArrayList<Foods> foods=new ArrayList<>();
        String sql="select * from foods where type=? and boss=? limit ?,?";
        String sql1="select * from foods where boss=? limit ?,?";
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{
            con=C3p0Util.getconn();
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
            rs=ps.executeQuery();
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
            return foods;
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            C3p0Util.close(rs,ps,con);
        }
        return foods;
    }
    //查找食品剩余数量
    public int getRest(int id){
        int num=0;
        String sql="select num from foods where id=?";
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
            con=C3p0Util.getconn();
            ps=con.prepareStatement(sql);
            ps.setObject(1,id);
            rs=ps.executeQuery();
            while (rs.next()){
                num=rs.getInt("num");
            }
            return num;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            C3p0Util.close(rs,ps,con);
        }
        return 0;
    }
    //购买后食物数量变化
    public boolean buyFood(Connection con,int id,int num){
        String sql="update foods set num=num-?,sold=sold+? where id=?";
        try {
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setObject(1,num);
            ps.setObject(2,num);
            ps.setObject(3,id);
            ps.executeLargeUpdate();
            ps.close();
            return true;
        } catch (Exception e) {
            try {
                con.rollback(); //回滚此次链接的所有操作
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return false;
    }
    //根据id找到商品信息
    public Foods getFood(int id){
        Foods f=new Foods();
        String sql="select * from foods where id=?";
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
            con=C3p0Util.getconn();
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
            return f;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            C3p0Util.close(rs,ps,con);
        }
        return f;
    }
    //根据id找到购物车信息
    public FoodInfo getInfo(int id){
        FoodInfo fi=new FoodInfo();
        String sql="select * from foods where id=?";
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
            con=C3p0Util.getconn();
            ps=con.prepareStatement(sql);
            ps.setObject(1,id);
            rs=ps.executeQuery();
            while (rs.next()){
                fi.setId(rs.getInt("id"));
                fi.setFoodname(rs.getString("foodname"));
                fi.setBoss(rs.getString("boss"));
                fi.setPrice(rs.getDouble("price"));
            }
            return fi;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            C3p0Util.close(rs,ps,con);
        }
        return fi;
    }
    //获得商品的个数
    public int getNum(String type) {
        int count=0;
        Connection con = null;
        String sql = "select count(0) from foods where type=?";
        String sql1 = "select count(0) from foods";
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
            con=C3p0Util.getconn();
            if (type.equals("精品推荐")) {
                ps = con.prepareStatement(sql1);
            } else {
                ps = con.prepareStatement(sql);
                ps.setObject(1, type);
            }
            rs = ps.executeQuery();
            while (rs.next()){
                count=rs.getInt("count(0)");
            }
            return count;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            C3p0Util.close(rs,ps,con);
        }
        return 0;
    }
        //获得商品列表
    public ArrayList<Foods> getfoods(String type,int begin,int size) throws SQLException {
        ArrayList<Foods> foods=new ArrayList<>();
        String sql="select * from foods where type=? order by sold desc limit ?,?";
        String sql1="select * from foods order by sold desc limit ?,?";
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{
            con=C3p0Util.getconn();
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
            rs=ps.executeQuery();
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
            return foods;
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            C3p0Util.close(rs,ps,con);
        }
        return foods;
    }
}
