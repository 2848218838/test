package cn.itcast.web.servlet;

import cn.itcast.dao.IUserServicedao;
import cn.itcast.dao.impl.UserServiceImpl;
import cn.itcast.domain.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/userServlet")
public class UserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //创建一个service对象
        IUserServicedao service=new UserServiceImpl();
        //使用service对象中封装的findAll方法 查询所有数据
        List<User> users = service.findAll();
        //将数据利用session域进行传输
        req.getSession().setAttribute("users",users);
        //使用重定向进行页面跳转
        resp.sendRedirect(req.getContextPath()+"/list.jsp");

    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        this.doPost(req,resp);
    }
}
