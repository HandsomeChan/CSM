import Bean.Foods;
import Bean.Users;
import Dao.FoodDao;
import Dao.UserDao;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@WebServlet(name = "ChangeFoodInfo")
public class ChangeFoodInfo extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession=request.getSession();
        FoodDao foodDao=new FoodDao();
        UserDao userDao=new UserDao();
        if (httpSession.getAttribute("fid")==null){
            response.sendRedirect("BossFirstPage");
            return;
        }
        int id=(int) httpSession.getAttribute("fid");
        Foods f=(Foods) foodDao.getFood(id);
        String name=f.getFoodname();
        Double price=f.getPrice();
        String intro=f.getIntroduction();
        String type=f.getType();
        int add=0;
        String pict=f.getPicture();
        try {
            DiskFileItemFactory factory=new DiskFileItemFactory();
            File file=new File(this.getServletContext().getRealPath("/huancun"));
            if (!file.exists()){
                file.mkdir();
            }
            //设置文件的缓存路径
            factory.setRepository(file);
            ServletFileUpload fileUpload=new ServletFileUpload(factory);
            fileUpload.setHeaderEncoding("utf-8");
            //解析获得上传文件得FileItem对象
            List<FileItem> fileItems=fileUpload.parseRequest(request);
            //获得字符流
            PrintWriter writer=response.getWriter();
            for (FileItem fileItem:fileItems){
                if (fileItem.isFormField()){
                    if (fileItem.getFieldName().equals("fname")){
                        if (!fileItem.getString().equals("")){
                           name=fileItem.getString("utf-8");
                        }
                    }
                    if (fileItem.getFieldName().equals("price")){
                        if (!fileItem.getString().equals("")){
                            price=Double.valueOf(fileItem.getString("utf-8"));
                        }
                    }
                    if (fileItem.getFieldName().equals("add")){
                        if (!fileItem.getString().equals("")){
                            add=Integer.valueOf(fileItem.getString("utf-8"));
                        }
                    }
                    if (fileItem.getFieldName().equals("intro")){
                        if (!fileItem.getString().equals("")){
                            intro=fileItem.getString("utf-8");
                        }
                    }
                    if (fileItem.getFieldName().equals("type")){
                        if (!fileItem.getString().equals("")){
                            type=fileItem.getString("utf-8");
                        }
                    }
                }else{
                    String fileName=fileItem.getName();
                    System.out.println(fileName);
                    if (fileName!=null&&!fileName.equals("")){
                        //文件名唯一
                        fileName= UUID.randomUUID().toString()+"_"+fileName;
                        pict=fileName;
                        //服务器端创建同名文件
                        String path=this.getServletContext().getRealPath("/foods");
                        System.out.println(path);
                        String filePath=path+"/"+fileName;
                        //创建文件
                        File file1=new File(filePath);
                        file1.getParentFile().mkdir();
                        file1.createNewFile();
                        //文件上传流
                        InputStream inputStream=fileItem.getInputStream();
                        FileOutputStream fileOutputStream=new FileOutputStream(file1);
                        byte[] bytes=new byte[1024];
                        int len;
                        while((len=inputStream.read(bytes))>0)
                            fileOutputStream.write(bytes,0,len);
                        inputStream.close();
                        fileOutputStream.close();
                        fileItem.delete();
                    }
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        boolean success=foodDao.updateInfo(id,name,price,type,intro,pict,add);
        Users user=(Users) httpSession.getAttribute("user");
        Date d = new Date();
        SimpleDateFormat sbf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String t=sbf.format(d);
        String r="店家";
        String event;
        if (!name.equals(f.getFoodname())) {
            event="将食物"+f.getFoodname()+"改名为"+name;
            userDao.oprLogs(user.getUname(), user.getEmail(), r, t, event);
        }
        if (price!=f.getPrice()) {
            event="将食物"+name+"价格修改为"+price;
            userDao.oprLogs(user.getUname(), user.getEmail(), r, t, event);
        }
        if (add!=0) {
            event="食物"+name+"补货了"+add+"份";
            userDao.oprLogs(user.getUname(), user.getEmail(), r, t, event);
        }
        if (!type.equals(f.getType())) {
            event="将食物"+name+"类别修改为"+type;
            userDao.oprLogs(user.getUname(), user.getEmail(), r, t, event);
        }
        if (!intro.equals(f.getIntroduction())) {
            event="将食物"+name+"简介修改为"+intro;
            userDao.oprLogs(user.getUname(), user.getEmail(), r, t, event);
        }
        if (!pict.equals(f.getPicture())) {
            event="修改了食物配图";
            userDao.oprLogs(user.getUname(), user.getEmail(), r, t, event);
        }
        httpSession.setAttribute("success",success);
        response.sendRedirect("BossFoodInfo.jsp?id="+id);
    }
}
