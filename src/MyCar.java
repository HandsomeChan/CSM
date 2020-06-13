import Bean.FoodInfo;
import Bean.Foods;
import Bean.Users;
import Dao.FoodDao;
import Dao.UserDao;

import javax.mail.Session;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "MyCar")
public class MyCar extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        FoodDao foodDao=new FoodDao();
        UserDao userDao=new UserDao();
        HttpSession hs=request.getSession();
        List<FoodInfo> cart=(List) hs.getAttribute("cart");
        Users user=(Users) hs.getAttribute("user");
        //记录浏览时间
        if (hs.getAttribute("time")!=null&&hs.getAttribute("ftype")!=null) {
            LocalDateTime time = LocalDateTime.now();
            int begin=(int) hs.getAttribute("time");
            String type=(String) hs.getAttribute("ftype");
            hs.removeAttribute("time");
            hs.removeAttribute("ftype");
            int end = time.getSecond();
            int look=end-begin;
            userDao.lookLogs(user.getEmail(),user.getUname(),type,look);
        }
        String boss=request.getParameter("boss");
        String page=request.getParameter("page");
        String type=request.getParameter("type");
        String myrecord=request.getParameter("myrecord");
//        System.out.println(boss);
        //用于记录货物是否是分两次加入购物车
        Map<Integer,Integer> map=(Map) hs.getAttribute("map");
        int wanted=0;
        int id;
        String sw=request.getParameter("wanted");
        if (sw!=null){
            wanted=Integer.valueOf(sw)-1;
        }
        String sid=request.getParameter("id");
        Object fid=hs.getAttribute("fid");
        if (sid==null){
            if (fid!=null){
                id=(int) fid;
            }
            else {
                request.getRequestDispatcher("PageContent").forward(request,response);
                return;
            }
        }
        else {
            id = Integer.valueOf(sid);
        }
//        System.out.println(id);
        FoodInfo foodInfo= foodDao.getInfo(id);
        if (foodInfo==null){
            request.getRequestDispatcher("PageContent").forward(request,response);
            return;
        }
        if (cart==null){
            cart=new ArrayList<>();
            hs.setAttribute("cart",cart);
        }
        if (map==null){
            map=new HashMap<>();
            hs.setAttribute("map",map);
        }
//        System.out.println(map.size());
        if (map.containsKey(id)){
            cart.get(map.get(id)).setNum(cart.get(map.get(id)).getNum()+1+wanted);
        }
        else {
            foodInfo.setNum(wanted+1);
            cart.add(foodInfo);
            map.put(foodInfo.getId(),cart.size()-1);
        }
        Cookie cookie=new Cookie("JSESSIONID",hs.getId());
        cookie.setMaxAge(60*60*12);
        cookie.setPath("/");
        response.addCookie(cookie);
        request.setAttribute("cp",hs.getAttribute("cp"));
        request.setAttribute("ty",hs.getAttribute("ty"));
//        System.out.println(boss);
//        System.out.println(page);
//        System.out.println(type);
        if (myrecord!=null&&!myrecord.equals("")){
            response.sendRedirect("MyRecord.jsp?page="+page);
            return;
        }
        if (boss==null||page==null||type==null||boss.equals("")||page.equals("")||type.equals("")){
            request.getRequestDispatcher("PageContent").forward(request,response);
        }
        else {
            request.setAttribute("boss",boss);
            request.setAttribute("page",page);
            request.setAttribute("type",type);
            request.getRequestDispatcher("BossShop").forward(request, response);
        }
    }
}
