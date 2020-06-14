package Dao;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.activation.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class C3p0Util {
    private static ComboPooledDataSource dataSource=null;
    static{
        dataSource=new ComboPooledDataSource();
    }
    public static Connection getconn(){
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return connection;
    }
    public static void close(ResultSet rs,PreparedStatement ps,Connection con){
        try {
            //暂时先不关闭关闭总是会无法连接数据
            if (rs!=null){
                rs.close();
            }
            if (ps!=null){
                ps.close();
            }
            if(con!=null){
                con.close();
//                System.out.println("连接已关闭！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
