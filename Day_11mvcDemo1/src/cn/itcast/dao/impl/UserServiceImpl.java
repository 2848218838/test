package cn.itcast.dao.impl;

import cn.itcast.dao.IUserServicedao;
import cn.itcast.dao.UserDao;
import cn.itcast.domain.User;
import cn.itcast.domain.PageBean;

import java.util.List;
import java.util.Map;

public class UserServiceImpl implements IUserServicedao {
    private UserDao dao= new UserdaoImpl();//创建一个dao成员对象

    // 查询所有数据 并返回一个List<User>集合
    public List<User> findAll(){
        return dao.findAll();
    }

    //登录 返回的是用户名和密码两个数据
    public User login(User user){
        return dao.findUserByUsernameAndPassword(user.getUsername(),user.getPassword());
    }

    @Override
    public void addUser(User user) {
        dao.add(user);
    }

    @Override
    public void deleteUser(String id) {
        dao.delete(Integer.parseInt(id));
    }

    @Override
    public User findUserById(String id) {
        return dao.findById(Integer.parseInt(id));
    }

    @Override
    public void updateUser(User user) {
        dao.update(user);
    }

    @Override
    public void delSelectedUser(String[] ids) {
        if(ids !=null && ids.length>0){
            for (String id : ids) {
                dao.delete(Integer.parseInt(id));
            }
        }
    }

    @Override
    public PageBean<User> findUserByPage(String _currentPage, String _rows, Map<String, String[]> condition) {
        int currentPage=Integer.parseInt(_currentPage);
        int rows=Integer.parseInt(_rows);
           int num =(currentPage-1)*currentPage;
               List<User> list=dao.selectUser(num,rows);
        if(currentPage<=0){
            currentPage=1;
        }
        //1.创建空的PageBean对象
        PageBean pageBean = new PageBean();
        pageBean.setList(list);
        //2.设置参数
        pageBean.setCurrentPage(currentPage);
        pageBean.setRows(rows);
        //3调用dao查询总记录数
        Integer totalCount = dao.findTotalCount(condition);
        pageBean.setTotalCount(totalCount);
        //4.调用dao查询List集合
        int start=(currentPage - 1) * rows;
        dao.findByPage(start,rows,condition);
        //5.计算总页码
        int totalPage=(totalCount% rows)== 0 ? totalCount/rows:(totalCount/rows)+1;
        pageBean.setTotalPage(totalPage);
        return pageBean;
    }


}
