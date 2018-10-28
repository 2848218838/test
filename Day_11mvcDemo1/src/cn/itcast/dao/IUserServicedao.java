package cn.itcast.dao;

import cn.itcast.domain.User;
import cn.itcast.domain.PageBean;

import java.util.List;
import java.util.Map;

public interface IUserServicedao {
    //查询所有
    public List<User> findAll();
    //登录
    public User login(User user);
    //添加
    void addUser(User user);
    //删除
    void deleteUser(String id);
    //分页
    User findUserById(String id);
    //更新
    void updateUser(User user);

    void delSelectedUser(String[] ids);

    PageBean<User> findUserByPage(String currentPage, String rows, Map<String, String[]> parameterMap);
}
