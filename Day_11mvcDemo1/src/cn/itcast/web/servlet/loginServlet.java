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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/loginServlet")
public class loginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //1.设置编码
        resp.setCharacterEncoding("utf-8");

        //2.获取数据
        //用户填写的验证码并进行验证码校验
        String verifycode = req.getParameter("verifycode");
        HttpSession session = req.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        //获取后将本次验证码删除
        session.removeAttribute("CHECKCODE_SERVER");
        //判断验证码与输入的验证码值是否相同
        if(!checkcode_server.equalsIgnoreCase(verifycode)){
            //不一样则跳转至错误页面
            req.setAttribute("login_msg","验证码输入有误!");
            //这里使用的是请求转发来进行跳转
            req.getRequestDispatcher("/login.jsp").forward(req,resp);
            return;
        }else {
            //验证码正确则获取到用户名和密码
            Map<String, String[]> map = req.getParameterMap();
            //3.封装Uesr
            User user = new User();
            try {
                //将用户名与user中的对应进行查询
                BeanUtils.populate(user,map);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

            //为什么错误用请求转发 而正确就用重定向呢？
            //5.调用Service查询
            IUserServicedao userService = new UserServiceImpl();
            //将从登录中传来的值赋值
            User login = userService.login(user);
            //6.判断是否登录成功
            if(login!=null){
                //如果成功 就封装用户名 并进行重定向跳转到index页面进行显示
                session.setAttribute("user",login);
                resp.sendRedirect(req.getContextPath()+"/index.jsp");
            }else {
                //如果错误 就封装错误提示信息 使用请求转发
                req.setAttribute("login_msg","用户名或密码错误");
                req.getRequestDispatcher("/login.jsp").forward(req,resp);
            }

        }
    }
}
