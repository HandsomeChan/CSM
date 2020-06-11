import Bean.Foods;
import Bean.Users;
import Dao.FoodDao;
import Dao.UserDao;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
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
import java.util.Random;
import java.util.UUID;

@WebServlet(name = "AddFood")
public class AddFood extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Random random=new Random();
        HttpSession httpSession=request.getSession();
        if (httpSession.getAttribute("user")==null)
            return;
        Users boss=(Users) httpSession.getAttribute("user");
        int id=random.nextInt(Integer.MAX_VALUE);
        String name="";
        Double price=0.0;
        String intro="好味大家爱";
        String type="";
        int add=0;
        String pict="";
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
                        else {
                            httpSession.setAttribute("addInfo","请输入用户名");
                            response.sendRedirect("addFood.jsp");
                            return;
                        }
                    }
                    if (fileItem.getFieldName().equals("add")){
                        if (!fileItem.getString().equals("0")){
                            add=Integer.valueOf(fileItem.getString("utf-8"));
                        }
                        else {
                            httpSession.setAttribute("addInfo","货量至少为1");
                            response.sendRedirect("addFood.jsp");
                            return;
                        }
                    }
                    if (fileItem.getFieldName().equals("intro")){
                        if (!fileItem.getString().equals("")){
                            intro=fileItem.getString("utf-8");
                        }
                        else {
                            httpSession.setAttribute("addInfo","给食物加点介绍吧");
                            response.sendRedirect("addFood.jsp");
                            return;
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
                    else {
                        httpSession.setAttribute("addInfo","要导入文件哦");
                        response.sendRedirect("addFood.jsp");
                        return;
                    }
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        boolean success=FoodDao.addFood(id,boss.getUname(),name,price,type,intro,pict,add);
        Date d = new Date();
        SimpleDateFormat sbf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String t=sbf.format(d);
        String r="店家";
        String event="上架了商品"+name+"共"+add+"份";
        UserDao.oprLogs(boss.getUname(),boss.getEmail(),r,t,event);
        httpSession.setAttribute("success",success);
        response.sendRedirect("MyFoods");
    }
}
