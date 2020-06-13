import Bean.Users;
import Dao.FoodDao;
import Dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet(name = "ChangeInfo")
public class ChangeInfo extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession=request.getSession();
        FoodDao foodDao=new FoodDao();
        UserDao userDao=new UserDao();
        if(httpSession.getAttribute("user")==null){
            request.getRequestDispatcher("login.jsp").forward(request,response);
            return;
        }
        Users user=(Users) httpSession.getAttribute("user");
        String email=user.getEmail();
        String username=user.getUname();
        String password=user.getPassword();
        double money=0;
        int role=user.getRole();
        request.setCharacterEncoding("UTF-8");
        if (role==0) {
            if (request.getParameter("uname") != null) {
                username = request.getParameter("uname");
            }
        }
        if (request.getParameter("pwd")!=null){
            password=request.getParameter("pwd");
        }
        if (request.getParameter("money")!=null){
            money=Double.valueOf(request.getParameter("money"));
        }
        boolean re=true;
        if (!userDao.checkName(username)&&!username.equals(user.getUname())){
            re=false;
            httpSession.setAttribute("repeat",re);
            if(role==0)
                response.sendRedirect("MyInfo.jsp");
            else
                response.sendRedirect("BossInfo.jsp");
            return;
        }
        Users success= userDao.UpdateInfo(email,username,password,money,role);
        boolean su=false;
        if (success!=null){
            Date d = new Date();
            SimpleDateFormat sbf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String t=sbf.format(d);
            String r=role==0?"顾客":"店家";
            String event;
            if (!username.equals(user.getUname())) {
                event="修改了用户名为"+username;
                userDao.oprLogs(user.getUname(), user.getEmail(), r, t, event);
            }
            if (!password.equals(user.getPassword())) {
                event="修改了密码";
                userDao.oprLogs(user.getUname(), user.getEmail(), r, t, event);
            }
            if (money!=0) {
                event="往账户充值了"+money;
                userDao.oprLogs(user.getUname(), user.getEmail(), r, t, event);
            }
            httpSession.setAttribute("user",success);
            su=true;
            httpSession.setAttribute("suInfo",su);
        }
        if(role==0)
            response.sendRedirect("MyInfo.jsp");
        else
            response.sendRedirect("BossInfo.jsp");
    }
}
