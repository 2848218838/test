package cn.itcast.web.servlet;

import cn.itcast.dao.IUserServicedao;
import cn.itcast.dao.impl.UserServiceImpl;
import cn.itcast.domain.User;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/addUserServlet")
public class addUserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //设定编码格式
        req.setCharacterEncoding("utf-8");
        //将浏览器中传输的用户和密码的值进行查询
        Map<String, String[]> map = req.getParameterMap();
        //创建user对象
        User user = new User();
        try {
            //将user中数据域浏览器中查询的数据进行对比 并将map中value值赋值给user
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //创建一个service对象
        IUserServicedao userService = new UserServiceImpl();
        //使用service对象的addUser方法将赋值后的数据进行传输
        userService.addUser(user);
        //使用请求转发跳转至userServlet页面
        resp.sendRedirect(req.getContextPath()+"/userServlet");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        this.doPost(req,resp);
    }
}
