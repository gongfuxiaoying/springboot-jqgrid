package com.example.caffeine.service;

/**
 * @author baoqy
 * @version 1.0
 * @date 2021/3/10 11:37
 */


import com.example.caffeine.entity.User;

import java.util.List;

/**
 * <p></p>
 * Created by zhezhiyong@163.com on 2017/9/21.
 */
public interface UserService {

    List<User> list();

    User findUserById(Long id);

    User findInfoById(Long id);

    User update(User user);

    void remove(Long id);

}