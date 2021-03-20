package com.example.springbootjqgrid.service.impl;

import com.example.springbootjqgrid.entity.User;
import com.example.springbootjqgrid.mapper.UserMapper;
import com.example.springbootjqgrid.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserServiceimpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }
}