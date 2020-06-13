import Bean.Users;
import Dao.FoodDao;
import Dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet(name = "logout")
public class logout extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Users user=(Users) request.getSession().getAttribute("user");
        FoodDao foodDao=new FoodDao();
        UserDao userDao=new UserDao();
        if(user==null){
            response.sendRedirect("PageContent");
            return;
        }
        Date d = new Date();
        SimpleDateFormat sbf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String t = sbf.format(d);
        String ip = GetIp.getIpAddress(request);
        String act="登出";
        String r=user.getRole()==0?"顾客":"店家";
        userDao.lologs(user.getEmail(), user.getUname(), t, ip, r,act);
        request.getSession().removeAttribute("user");
        Cookie cookie=new Cookie("JSESSIONID","msg");
        cookie.setPath(request.getContextPath());
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        response.sendRedirect("PageContent");
    }
}
