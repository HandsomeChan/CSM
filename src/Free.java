import Bean.Users;
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

@WebServlet(name = "Free")
public class Free extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession hs=request.getSession();
        FoodDao foodDao=new FoodDao();
        UserDao userDao=new UserDao();
        Users user=(Users) hs.getAttribute("user");
        if (user==null||user.getRole()!=2){
            return;
        }
        String uname=request.getParameter("uname");
        int page=Integer.valueOf(request.getParameter("page"));
        boolean success= userDao.setFree(uname);
        if (success){
            String email=userDao.getEmail(uname);
            String message="您的账户"+uname+"已经成功解封！";
            NotifyEmail.sendMail(email,message);
        }
        hs.setAttribute("react",success);
        response.sendRedirect("blocked.jsp?page="+page);
    }
}
