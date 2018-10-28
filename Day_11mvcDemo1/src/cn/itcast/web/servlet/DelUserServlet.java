package cn.itcast.web.servlet;

import cn.itcast.dao.IUserServicedao;
import cn.itcast.dao.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/delUserServlet")
public class DelUserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 删除根据id进行删除 所以先获取id值
        String id=req.getParameter("id");
        // 创建一个Service对象
        IUserServicedao service=new UserServiceImpl();
        //然后将当前的id值进行传递 并执行删除方法
        service.deleteUser(id);
        //进行重定向 跳转至userServlet页面进行页面更新   没有共享数据就使用重定向
        resp.sendRedirect(req.getContextPath()+"/userServlet");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        this.doPost(req,resp);
    }
}
