package Dao;
import Bean.*;
import SendEmail.NotifyEmail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    //浏览时间
    public static void lookLogs(String email,String uname,String type,int length){
        Connection con=C3p0Util.getconn();
        boolean tap=false;
        String sql0="select count(0) from lookLogs where uname=? and type=?";
        String sql1="insert into lookLogs values(?,?,?,?)";
        String sql2="update lookLogs set length=length+? where uname=? and type=?";
        try {
            PreparedStatement ps=con.prepareStatement(sql0);
            ps.setObject(1,uname);
            ps.setObject(2,type);
            ResultSet rs=ps.executeQuery();
            while (rs.next()){
                if (rs.getInt("count(0)")!=0){
                    tap=true;
                }
            }
            if (!tap) {
                ps=con.prepareStatement(sql1);
                ps.setObject(1, email);
                ps.setObject(2, uname);
                ps.setObject(3, type);
                ps.setObject(4, length);
                ps.executeLargeUpdate();
            }
            else {
                ps=con.prepareStatement(sql2);
                ps.setObject(1,length);
                ps.setObject(2,uname);
                ps.setObject(3,type);
                ps.executeLargeUpdate();
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            C3p0Util.close(con);
        }
    }
    //解封账号
    public static boolean setFree(String uname) {
        Connection con = C3p0Util.getconn();
        String sql = "update users set role=role+2 where uname=? and role<0";
        PreparedStatement ps;
        try {
            ps = con.prepareStatement(sql);
            ps.setObject(1, uname);
            ps.executeLargeUpdate();
            ps.close();
            con.close();
            return true;
        } catch (SQLException e) {
            try {
                con.rollback(); //回滚此次链接的所有操作
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            C3p0Util.close(con);
        }
        return false;
    }
    //查看被封的账号
    public static List<Blocked> getblockes(){
        Connection con=C3p0Util.getconn();
        List<Blocked> list=new ArrayList<>();
        String sql="select * from users where role<0";
        try {
            PreparedStatement ps=con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            while (rs.next()){
                Blocked bl=new Blocked();
                bl.setEmail(rs.getString("email"));
                bl.setUname(rs.getString("uname"));
                list.add(bl);
            }
            rs.close();
            ps.close();
            con.close();
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            C3p0Util.close(con);
        }
        return null;
    }
    //根据用户名找到邮箱
    public static String getEmail(String uname){
        Connection con=C3p0Util.getconn();
        String email="";
        String sql="select email from users where uname=?";
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps=con.prepareStatement(sql);
            ps.setObject(1,uname);
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
    //封锁用户
    public static boolean block(String uname,String role){
        Connection con=C3p0Util.getconn();
        String sql="update users set role=role-2 where uname=? and role>=0";
        String sql1="update foods set num=0 where boss=?";
        PreparedStatement ps;
        try {
            ps=con.prepareStatement(sql);
            ps.setObject(1,uname);
            ps.executeLargeUpdate();
            if (!role.equals("顾客")){
                ps=con.prepareStatement(sql1);
                ps.setObject(1,uname);
                ps.executeLargeUpdate();
            }
            ps.close();
            con.close();
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
    //获得操作日志
    public static List<Oplogs> opLog(){
        Connection con=C3p0Util.getconn();
        List<Oplogs> list=new ArrayList<>();
        String sql="select * from oplogs order by time desc";
        try {
            PreparedStatement ps=con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            while (rs.next()){
                Oplogs bl=new Oplogs();
                bl.setEmail(rs.getString("email"));
                bl.setUname(rs.getString("uname"));
                bl.setTime(rs.getString("time"));
                bl.setRole(rs.getString("role"));
                bl.setEvent(rs.getString("event"));
                list.add(bl);
            }
            rs.close();
            ps.close();
            con.close();
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            C3p0Util.close(con);
        }
        return null;
    }
    //获得用户购买日志
    public static List<Buylogs> buyLog(){
        Connection con=C3p0Util.getconn();
        List<Buylogs> list=new ArrayList<>();
        String sql="select * from buylogs order by time desc";
        try {
            PreparedStatement ps=con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            while (rs.next()){
                Buylogs bl=new Buylogs();
                bl.setEmail(rs.getString("email"));
                bl.setUname(rs.getString("uname"));
                bl.setBoss(rs.getString("boss"));
                bl.setTime(rs.getString("time"));
                bl.setFood(rs.getString("food"));
                bl.setNum(rs.getInt("num"));
                bl.setAllpay(rs.getDouble("allpay"));
                list.add(bl);
            }
            rs.close();
            ps.close();
            con.close();
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            C3p0Util.close(con);
        }
        return null;
    }
    //获得登录日志
    public static List<Loginlogs> getLogin(){
        Connection con=C3p0Util.getconn();
        List<Loginlogs> list=new ArrayList<>();
        String sql="select * from loginlogs order by logintime desc";
        try {
            PreparedStatement ps=con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            while (rs.next()){
                Loginlogs ll=new Loginlogs();
                ll.setEmail(rs.getString("email"));
                ll.setUsername(rs.getString("username"));
                ll.setIp(rs.getString("ip"));
                ll.setLogintime(rs.getString("logintime"));
                ll.setRole(rs.getString("role"));
                ll.setAction(rs.getString("action"));
                list.add(ll);
            }
            rs.close();
            ps.close();
            con.close();
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            C3p0Util.close(con);
        }
        return null;
    }
    //操作日志
    public static void oprLogs(String uname,String email,String role,String time,String event){
        Connection con=C3p0Util.getconn();
        String sql="insert into oplogs values(?,?,?,?,?)";
        try {
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setObject(1,uname);
            ps.setObject(2,email);
            ps.setObject(3,role);
            ps.setObject(4,time);
            ps.setObject(5,event);
            ps.executeLargeUpdate();
            ps.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            C3p0Util.close(con);
        }
    }
    //所有店家销售记录
    public static List<SoldSort> getAllboss(){
        Connection con=C3p0Util.getconn();
        List<SoldSort> list=new ArrayList<>();
        String sql="select users.email as email,users.uname as name,sum(allpay) as earn,sum(num) as nums from users left join buylogs on users.uname=buylogs.boss where users.role=1 group by users.uname,users.email order by nums desc";
        try {
            PreparedStatement ps=con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            while (rs.next()){
                SoldSort soldSort=new SoldSort();
                soldSort.setEmail(rs.getString("email"));
                soldSort.setBoss(rs.getString("name"));
                soldSort.setEarn(rs.getDouble("earn"));
                soldSort.setNum(rs.getInt("nums"));
                list.add(soldSort);
            }
            rs.close();
            ps.close();
            con.close();
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            C3p0Util.close(con);
        }
        return null;
    }
    //获得卖出记录
    public static List<MySoldFood> getSold(String boss){
        Connection con=C3p0Util.getconn();
        List<MySoldFood> list=new ArrayList<>();
        String sql="select * from buylogs where boss=?";
        try {
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setObject(1,boss);
            ResultSet rs=ps.executeQuery();
            while (rs.next()){
                MySoldFood s=new MySoldFood();
                s.setFood(rs.getString("food"));
                s.setFoodid(rs.getInt("foodid"));
                s.setTime(rs.getString("time"));
                s.setEarn(rs.getDouble("allpay"));
                s.setNum(rs.getInt("num"));
                s.setUname(rs.getString("uname"));
                s.setEmail(rs.getString("email"));
                list.add(s);
            }
            rs.close();
            ps.close();
            con.close();
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            C3p0Util.close(con);
        }
        return null;
    }
    //获得购买记录
    public static List<MyBuyFood> getRecord(String email){
        Connection con=C3p0Util.getconn();
        List<MyBuyFood> list=new ArrayList<>();
        String sql="select * from buylogs where email=? order by time desc";
        try {
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setObject(1,email);
            ResultSet rs=ps.executeQuery();
            while (rs.next()){
                MyBuyFood myBuyFood=new MyBuyFood();
                myBuyFood.setFood(rs.getString("food"));
                myBuyFood.setBoss(rs.getString("boss"));
                myBuyFood.setFoodid(rs.getInt("foodid"));
                myBuyFood.setTime(rs.getString("time"));
                myBuyFood.setAllpay(rs.getDouble("allpay"));
                myBuyFood.setNum(rs.getInt("num"));
                list.add(myBuyFood);
            }
            rs.close();
            ps.close();
            con.close();
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            C3p0Util.close(con);
        }
        return null;
    }
    //购买日志
    public static void buylogs(String email,String uname,String time,String boss,int foodid,String food,int num,double allpay){
        Connection con=C3p0Util.getconn();
        String sql="insert into buylogs values(?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setObject(1,email);
            ps.setObject(2,uname);
            ps.setObject(3,time);
            ps.setObject(4,boss);
            ps.setObject(5,foodid);
            ps.setObject(6,food);
            ps.setObject(7,num);
            ps.setObject(8,allpay);
            ps.executeLargeUpdate();
            ps.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            C3p0Util.close(con);
        }
    }
    //更新信息
    public static Users UpdateInfo(String email,String uname,String pwd,double money,int role){
        Connection con=C3p0Util.getconn();
        String sql="update users set uname=?,password=?,money=money+? where email=? and role=?";
        Users success=new Users();
        try {
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setObject(1,uname);
            ps.setObject(2,pwd);
            ps.setObject(3,money);
            ps.setObject(4,email);
            ps.setObject(5,role);
            ps.executeLargeUpdate();
            String sql1="select * from users where email=? and role=?";
            ps=con.prepareStatement(sql1);
            ps.setObject(1,email);
            ps.setObject(2,role);
            ResultSet rs=ps.executeQuery();
            while (rs.next()){
                success.setEmail(rs.getString("email"));
                success.setPassword(rs.getString("password"));
                success.setUname(rs.getString("uname"));
                success.setMoney(rs.getDouble("money"));
                success.setRole(rs.getInt("role"));
            }
            rs.close();
            ps.close();
            con.close();
            return success;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            C3p0Util.close(con);
        }
        return null;
    }
    //顾客的钱数
    public static double getMoney(String email){
        Connection con=C3p0Util.getconn();
        double m=0;
        String sql="select money from users where email=? and role=0";
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps=con.prepareStatement(sql);
            ps.setObject(1,email);
            rs=ps.executeQuery();
            while (rs.next()){
                m=rs.getDouble("money");
            }
            rs.close();
            ps.close();
            con.close();
            return m;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            C3p0Util.close(con);
        }
        return m;
    }
    //店长收钱
    public static boolean moneymoney(Connection con,double money,String username){
        String sql="update users set money=money+? where uname=? and role=1";
        try {
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setObject(1,money);
            ps.setObject(2,username);
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
    //购买扣钱
    public static boolean buybuuybuy(Connection con,double money,String email){
        String sql="update users set money=money-? where email=? and role=0";
        try {
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setObject(1,money);
            ps.setObject(2,email);
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
    //登录验证
    public static Users login(String email, String password,int role){
        Connection con=C3p0Util.getconn();
        String sql="select * from users where email=? and password=? and role=?";
        try {
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setObject(1,email);
            ps.setObject(2,password);
            ps.setObject(3,role);
            ResultSet rs=ps.executeQuery();
            while (rs.next()){
                Users user=new Users();
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getInt("role"));
                user.setUname(rs.getString("uname"));
                user.setMoney(rs.getDouble("money"));
                rs.close();
                ps.close();
                con.close();
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            C3p0Util.close(con);
        }
        //如果找不到相应的
        return null;
    }
    //用户名不可重复
    public static boolean checkName(String uname) {
        Connection con = C3p0Util.getconn();
        boolean ok = true;
        String sql = "select * from users where uname=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setObject(1, uname);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ok=false;
                rs.close();
                ps.close();
                con.close();
                return ok;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            C3p0Util.close(con);
        }
        return ok;
    }
        //消费者注册模块
    public static Users register(String email,String password,String username,int role){
        Connection con=C3p0Util.getconn();
        //同一角色不能注册两次
        String sql="select * from users where email=? and role=?";
        try {
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setObject(1,email);
            ps.setObject(2,role);
            ResultSet rs=ps.executeQuery();
            if (rs.next()){
                rs.close();
                ps.close();
                con.close();
                return null;
            }
            else {
                String sql1="insert into users(email,password,uname,role,money) values(?,?,?,?,?)";
                ps=con.prepareStatement(sql1);
                ps.setObject(1,email);
                ps.setObject(2,password);
                ps.setObject(3,username);
                ps.setObject(4,role);
                ps.setObject(5,0);
                ps.executeLargeUpdate();
                Users user=new Users();
                user.setEmail(email);
                user.setPassword(password);
                user.setUname(username);
                user.setRole(role);
                user.setMoney(0);
                NotifyEmail.sendMail(email,username+"您好，您的账号注册成功了！");
                ps.close();
                con.close();
                return user;
            }
        }
        catch (SQLException e){
            try {
                con.rollback(); //回滚此次链接的所有操作
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }finally {
            C3p0Util.close(con);
        }
        return null;
    }
    //用户登陆日志
     public static void lologs(String email,String username,String logtime,String ip,String role,String act){
         Connection con=C3p0Util.getconn();
         String sql="insert into loginlogs values(?,?,?,?,?,?)";
         try {
             PreparedStatement ps=con.prepareStatement(sql);
             ps.setObject(1,email);
             ps.setObject(2,username);
             ps.setObject(3,logtime);
             ps.setObject(4,ip);
             ps.setObject(5,role);
             ps.setObject(6,act);
             ps.executeLargeUpdate();
             ps.close();
             con.close();
         } catch (SQLException e) {
             e.printStackTrace();
         }finally {
             C3p0Util.close(con);
         }
     }
}
