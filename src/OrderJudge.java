import Bean.FoodInfo;
import Bean.Users;
import Dao.C3p0Util;
import Dao.FoodDao;
import Dao.UserDao;
import SendEmail.NotifyEmail;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet(name = "OrderJudge")
public class OrderJudge extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        FoodDao foodDao=new FoodDao();
        UserDao userDao=new UserDao();
        Connection con=C3p0Util.getconn();
        PreparedStatement ps=null;
        ResultSet rs=null;
        HttpSession httpSession=request.getSession();
        Users us=(Users) httpSession.getAttribute("user");
        double sum=(double) httpSession.getAttribute("sum");
        List<FoodInfo> cart=(List) httpSession.getAttribute("cart");
        String[] list=(String[]) httpSession.getAttribute("orderlist");
        boolean success=false;
        String message=us.getUname()+"您好，您在CSM消费了"+sum+"元，其中有:\n";
        if (userDao.buybuuybuy(con,sum,us.getEmail())){
            for (int i=0;i<list.length;i++){
                int index=Integer.valueOf(list[i]);
                int actnum=Integer.valueOf(cart.get(index).getNum());
                if (actnum==0)
                    continue;
                String boss=cart.get(i).getBoss();
                double money=cart.get(index).getPrice()*actnum;
                if (foodDao.buyFood(con,cart.get(index).getId(),actnum)&&userDao.moneymoney(con,money,boss)){
                    message+=cart.get(i).getFoodname()+actnum+"份共"+money+"元\n";
                    Date d = new Date();
                    SimpleDateFormat sbf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String t=sbf.format(d);
                    userDao.buylogs(us.getEmail(),us.getUname(),t,cart.get(i).getBoss(),cart.get(i).getId(),cart.get(i).getFoodname(),actnum,money);
                }
                else {
                    C3p0Util.close(rs,ps,con);
                    success=false;
                    request.setAttribute("success",success);
                    request.getRequestDispatcher("OrderPage.jsp").forward(request,response);
                    return;
                }
            }
            message+="您余额还有"+(us.getMoney()-sum)+"元";
            C3p0Util.close(rs,ps,con);
            httpSession.removeAttribute("cart");
            NotifyEmail.sendMail(us.getEmail(),message);
            success=true;
            request.setAttribute("success",success);
            request.getRequestDispatcher("OrderPage.jsp").forward(request,response);
        }
        else {
            success=false;
            request.setAttribute("success",success);
            request.getRequestDispatcher("OrderPage.jsp").forward(request,response);
            C3p0Util.close(rs,ps,con);
        }
    }
}
