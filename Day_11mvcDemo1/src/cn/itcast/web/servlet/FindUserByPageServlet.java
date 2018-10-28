package cn.itcast.web.servlet;

import cn.itcast.dao.IUserServicedao;
import cn.itcast.dao.impl.UserServiceImpl;
import cn.itcast.domain.User;
import cn.itcast.domain.PageBean;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/findUserByPageServlet")
public class FindUserByPageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        //1.获取参数
        String currentPage = req.getParameter("currentPage");
        String rows = req.getParameter("rows");

        if(currentPage == null || "".equals(currentPage)){
            currentPage="1";
        }
        if(rows==null||"".equals(rows)){
            rows="5";
        }

        Map<String, String[]> condition = req.getParameterMap();
        //调用service查询
        IUserServicedao servicedao=new UserServiceImpl();
       PageBean<User> pb= servicedao.findUserByPage(currentPage,rows, condition);
        System.out.println(pb);

        //将PageBean存入request
        req.setAttribute("pb",pb);
        req.setAttribute("condition",condition);
        //转发到list.jsp
        req.getRequestDispatcher("/list.jsp").forward(req,resp);
    }
}
