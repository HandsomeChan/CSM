import Bean.Users;
import Dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet(name = "Regist")
public class Regist extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        UserDao userDao=new UserDao();
        String email=request.getParameter("email");
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        String pc=request.getParameter("password-again");
        String roles=request.getParameter("role");
        if (email.equals("")){
            request.setAttribute("register_message","电子邮箱不能为空！");
            request.getRequestDispatcher("register.jsp").forward(request,response);
            return;
        }
        if (username.equals("")){
            request.setAttribute("register_message","用户名不能为空！");
            request.getRequestDispatcher("register.jsp").forward(request,response);
            return;
        }
        if (password.equals("")){
            request.setAttribute("register_message","密码不能为空！");
            request.getRequestDispatcher("register.jsp").forward(request,response);
            return;
        }
        if (pc.equals("")){
            request.setAttribute("register_message","请确认密码！");
            request.getRequestDispatcher("register.jsp").forward(request,response);
            return;
        }
        if (roles==null){
            request.setAttribute("register_message","请选择您的身份！");
            request.getRequestDispatcher("register.jsp").forward(request,response);
            return;
        }
        if (!password.equals(pc)){
            request.setAttribute("register_message","两次密码输入不正确，请校正！");
            request.getRequestDispatcher("register.jsp").forward(request,response);
            return;
        }
        int role=roles.equals("customer")?0:1;
        String r=roles.equals("customer")?"顾客":"店家";
//        System.out.println("role"+role);
        boolean ok=userDao.checkName(username);
        if (!ok){
            request.setAttribute("register_message","用户名已被使用，请换一个名字！");
            request.getRequestDispatcher("register.jsp").forward(request,response);
            return;
        }
        Users user= userDao.register(email,password,username,role);
        if (user==null){
            request.setAttribute("register_message","电子邮箱已被使用，请检查！");
            request.getRequestDispatcher("register.jsp").forward(request,response);
            return;
        }
        //设置日志文件
        Date d = new Date();
        SimpleDateFormat sbf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String t=sbf.format(d);
        String ip=GetIp.getIpAddress(request);
        String event="注册了账号";
        String act="登入";
        userDao.oprLogs(user.getUname(),user.getEmail(),r,t,event);
        userDao.lologs(user.getEmail(),user.getUname(),t,ip,r,act);
        HttpSession session=request.getSession();
        session.setAttribute("user",user);
        Cookie cookie=new Cookie("JSESSIONID",session.getId());
        cookie.setMaxAge(60*60*12);
        cookie.setPath("/");
        response.addCookie(cookie);
        if (role==0)
            response.sendRedirect("PageContent");
        else
            response.sendRedirect("MyFoods");
    }
}
