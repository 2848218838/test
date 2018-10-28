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

@WebServlet("/updateUserServlet")
public class UpdateUserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //设置编码格式
        req.setCharacterEncoding("utf-8");
        //获取浏览器中的两个值
        Map<String, String[]> map = req.getParameterMap();
        //创建一个user对象
        User user = new User();
        try {
            //将浏览器中的两个value值传递给user
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //创建一个service对象
        IUserServicedao userService = new UserServiceImpl();
        //将传递后的user值当作该方法的参数执行
        userService.updateUser(user);
        //最后进行重定向跳转至userServlet页面进行刷新
        resp.sendRedirect(req.getContextPath()+"/userServlet");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        this.doPost(req,resp);
    }
}
