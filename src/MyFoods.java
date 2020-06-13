import Bean.Foods;
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
import java.util.ArrayList;

@WebServlet(name = "MyFoods")
public class MyFoods extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession=request.getSession();
        FoodDao foodDao=new FoodDao();
        UserDao userDao=new UserDao();
        if (httpSession.getAttribute("user")==null){
            response.sendRedirect("BossFirstPage.jsp");
            return;
        }
        Users boss=(Users) httpSession.getAttribute("user");
        int currentPage = 1, pageSize = 16;
        String _currentPage = request.getParameter("page");
        Object _currentPage_ = request.getAttribute("cp");
        if (_currentPage != null) {
            currentPage = Integer.valueOf(_currentPage);
        } else if (_currentPage_ != null) {
            currentPage = (int) _currentPage_;
        }
        String type = "全部";
        String _type = request.getParameter("type");
        Object _type_ = request.getAttribute("ty");
        if (_type != null) {
            type = _type;
        } else if (_type_ != null) {
            type = (String) _type_;
        }
        if (currentPage < 1) {
            request.setAttribute("foods", new ArrayList<Foods>());
            request.setAttribute("pagesize", pageSize);
            request.setAttribute("type", type);
            request.setAttribute("currentpage", currentPage);
            request.getRequestDispatcher("BossFirstPage.jsp").forward(request, response);
            return;
        }
//        System.out.println(type);
        ArrayList<Foods> allfoods = foodDao.getMyFoods(boss.getUname(),type, (currentPage - 1) * pageSize, pageSize);
//        System.out.println(allfoods.size());
        //总页数，向上取整
        int allpages = (int) Math.ceil(foodDao.getBossNum(boss.getUname(),type) / pageSize);
        request.setAttribute("type", type);
        request.setAttribute("pages", allpages);
        request.setAttribute("foods", allfoods);
        request.setAttribute("pagesize", pageSize);
        request.setAttribute("currentpage", currentPage);
        request.getRequestDispatcher("BossFirstPage.jsp").forward(request, response);
    }
}
