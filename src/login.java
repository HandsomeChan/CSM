import Bean.Foods;
import Bean.Users;
import Dao.FoodDao;
import Dao.UserDao;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class login extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        doGet(request,response);
    }
    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        FoodDao foodDao=new FoodDao();
        UserDao userDao=new UserDao();
        String email=request.getParameter("email");
        String password=request.getParameter("password");
        String roles=request.getParameter("role");
        if (email.equals("")){
            request.setAttribute("login_message","电子邮箱不能为空！");
            request.getRequestDispatcher("login.jsp").forward(request,response);
            return;
        }
        if (password.equals("")){
            request.setAttribute("login_message","密码不能为空！");
            request.getRequestDispatcher("login.jsp").forward(request,response);
            return;
        }
        if (roles==null){
            request.setAttribute("login_message","请选择您的身份！");
            request.getRequestDispatcher("login.jsp").forward(request,response);
            return;
        }
        int role=roles.equals("customer")?0:roles.equals("boss")?1:2;
        String r=roles.equals("customer")?"顾客":roles.equals("boss")?"店家":"管理员";
        Users user= userDao.login(email,password,role);
        if (user!=null){
            //设置日志文件
            if (role!=2) {
                Date d = new Date();
                SimpleDateFormat sbf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String t = sbf.format(d);
                String ip = GetIp.getIpAddress(request);
                String act="登入";
                userDao.lologs(user.getEmail(), user.getUname(), t, ip, r,act);
            }
            HttpSession session=request.getSession();
            session.setAttribute("user",user);
            Cookie cookie=new Cookie("JSESSIONID",session.getId());
            cookie.setMaxAge(60*60*12);
            cookie.setPath("/");
            response.addCookie(cookie);
            if (role==0)
                response.sendRedirect("PageContent");
            else if(role==1)
                response.sendRedirect("MyFoods");
            else
                response.sendRedirect("ManagerFirstPage.jsp");
        }
        else{
            request.setAttribute("login_message","用户名或密码错误，请重试！");
            request.getRequestDispatcher("login.jsp").forward(request,response);
        }
    }
}
