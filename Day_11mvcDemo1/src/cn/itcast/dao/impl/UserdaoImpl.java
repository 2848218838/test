package cn.itcast.dao.impl;

import cn.itcast.dao.UserDao;
import cn.itcast.domain.User;
import cn.itcast.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UserdaoImpl implements UserDao {
    private JdbcTemplate template=new JdbcTemplate(JDBCUtils.getDataSource());

    //查询所有数据
    public List<User> findAll(){
        String sql="select * from user";
        List<User> users=template.query(sql, new BeanPropertyRowMapper<User>(User.class));
        return users;
    }
    //查询用于登录验证的用户名和密码
    public User findUserByUsernameAndPassword(String username,String password){
        try{
            String sql="select * from user where  username=? and password= ?";
            User user=template.queryForObject(sql,new BeanPropertyRowMapper<User>(User.class),username,password);
            return user;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    //添加一条信息
    @Override
    public void add(User user) {
        String sql="insert into user values(null,?,?,?,?,?,?,null,null)";
        template.update(sql, user.getName(), user.getGender(), user.getAge(), user.getAddress(), user.getQq(), user.getEmail());
    }

    //删除一条信息
    @Override
    public void delete(int id) {
        String sql="delete from user where id=?";
        template.update(sql,id);
    }

    @Override
    public Integer findTotalCount(Map<String, String[]> condition) {
        String sql="select count(*) from user where 1 = 1 ";
        StringBuilder stringBuilder = new StringBuilder(sql);
        Set<String> strings = condition.keySet();
        List<Object> objects = new ArrayList<>();
        for (String string : strings) {
            if("currentPage".equals(string)||"rows".equals(string)){
                continue;
            }

            String s = condition.get(string)[0];
            if(s!=null && !"".equals(s)){
                stringBuilder.append(" and "+string+" like ?");
                objects.add("%"+s+"%");
            }
        }
        System.out.println(stringBuilder.toString());
        System.out.println(objects);
        return template.queryForObject(stringBuilder.toString(),Integer.class,objects.toArray());
    }

    @Override
    public List<User> findByPage(int start, int rows, Map<String, String[]> condition) {
        String sql="select * from user where 1 = 1 ";
        StringBuilder stringBuilder = new StringBuilder(sql);

        Set<String> strings = condition.keySet();

        ArrayList<Object> objects = new ArrayList<>();

        for (String string : strings) {
            if("currentPage".equals(string)||"rows".equals(string)){
                continue;
            }
            String s = condition.get(string)[0];
            if(s!=null && !"".equals(s)){
                stringBuilder.append(" and "+s+" like ?  ");
                objects.add("%"+s+"%");
            }
        }
        stringBuilder.append(" limit ? , ?");
        objects.add(start);
        objects.add(rows);
        sql=stringBuilder.toString();

        return template.query(sql,new BeanPropertyRowMapper<User>(User.class),objects.toArray());
    }

    @Override
    public List<User> selectUser(int num, int rows) {
        String sql="select*from user limit ?,?";
        return  template.query(sql, new BeanPropertyRowMapper<User>(User.class),num,rows);
    }

    // 根据id进行分页
    @Override
    public User findById(int id) {
        String sql="select * from user where id= ?";
        return template.queryForObject(sql,new BeanPropertyRowMapper<User>(User.class),id);
    }

    //更新一条信息
    @Override
    public void update(User user) {
        String sql="update user set name = ?,gender = ?,age = ?,address = ?, qq =? ,email = ? where id=?";
        template.update(sql,user.getName(),user.getGender(),user.getAge(),user.getAddress(),user.getQq(),user.getEmail(),user.getId());
    }
}
