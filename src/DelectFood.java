import Bean.Foods;
import Bean.Users;
import Dao.FoodDao;
import Dao.UserDao;
import SendEmail.NotifyEmail;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet(name = "DelectFood")
public class DelectFood extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("id")==null)
            return;
        int id=Integer.valueOf(request.getParameter("id"));
        String email=FoodDao.findboss(id);
        Foods f=FoodDao.getFood(id);
        boolean success= FoodDao.deleFood(id);
        Users user=(Users)request.getSession().getAttribute("user");
        int role=user.getRole();
        if (role==1) {
            String r = "店家";
            String event;
            Date d = new Date();
            SimpleDateFormat sbf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String t = sbf.format(d);
            event = "下架了商品" + id;
            UserDao.oprLogs(user.getUname(), user.getEmail(), r, t, event);
            request.getSession().setAttribute("dele", success);
            response.sendRedirect("MyFoods");
        }
        else {
            int page=Integer.valueOf(request.getParameter("page"));
            String message="您的商品"+f.getFoodname()+"被管理员下架了，详情请联系管理员！";
            NotifyEmail.sendMail(email,message);
            response.sendRedirect("allthings.jsp?page="+page);
        }
    }
}
