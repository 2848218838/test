package cn.itcast.dao;

import cn.itcast.domain.User;

import java.util.List;

public interface IUser {
   public List<User> findAll();
}
