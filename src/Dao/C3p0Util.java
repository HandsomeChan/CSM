package Dao;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class C3p0Util {
    public static Connection getconn(){
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return connection;
    }
    public static void close(Connection con){
        try {
            //暂时先不关闭关闭总是会无法连接数据
            if(con!=null){
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
