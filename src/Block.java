import Bean.Users;
import Dao.UserDao;
import SendEmail.NotifyEmail;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "Block")
public class Block extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession hs=request.getSession();
        Users user=(Users) hs.getAttribute("user");
        if (user==null||user.getRole()!=2){
            return;
        }
        String uname=request.getParameter("uname");
        String role=request.getParameter("role");
        int page=Integer.valueOf(request.getParameter("page"));
        int login=Integer.valueOf(request.getParameter("login"));
        boolean success= UserDao.block(uname,role);
        if (success){
            String email=UserDao.getEmail(uname);
            String message="您的账户"+uname+"因为违规被管理员封了，请联系管理员了解具体情形。";
            NotifyEmail.sendMail(email,message);
        }
        hs.setAttribute("react",success);
        if (login==1)
            response.sendRedirect("loglogs.jsp?page="+page);
        else
            response.sendRedirect("checkboss.jsp?page="+page);
    }
}
