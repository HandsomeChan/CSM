import Bean.Foods;
import Dao.FoodDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "PageContent")
public class PageContent extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int currentPage=1,pageSize=16;
        String _currentPage=request.getParameter("page");
        Object _currentPage_=request.getAttribute("cp");
        if (_currentPage!=null){
            currentPage=Integer.valueOf(_currentPage);
        }
        else if (_currentPage_!=null){
            currentPage=(int)_currentPage_;
        }
        String type="精品推荐";
        String _type=request.getParameter("type");
        Object _type_=request.getAttribute("ty");
        if (_type!=null) {
            type = _type;
        }
        else if (_type_!=null){
            type=(String) _type_;
        }
        if (currentPage<1){
            request.setAttribute("foods",new ArrayList<Foods>());
            request.setAttribute("pagesize",pageSize);
            request.setAttribute("type",type);
            request.setAttribute("currentpage",currentPage);
            request.getRequestDispatcher("firstPage.jsp").forward(request,response);
            return;
        }
//        System.out.println(type);
        ArrayList<Foods> allfoods= null;
        FoodDao foodDao=new FoodDao();
        try {
            allfoods = foodDao.getfoods(type,(currentPage-1)*pageSize,pageSize);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //总页数，向上取整
        int allpages=(int) Math.ceil(foodDao.getNum(type)/pageSize);
        request.setAttribute("type",type);
        request.setAttribute("pages",allpages);
        request.setAttribute("foods",allfoods);
        request.setAttribute("pagesize",pageSize);
        request.setAttribute("currentpage",currentPage);
        request.getRequestDispatcher("firstPage.jsp").forward(request,response);
    }
}
