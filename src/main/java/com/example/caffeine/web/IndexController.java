package com.example.caffeine.web;

/**
 * @author baoqy
 * @version 1.0
 * @date 2021/3/10 11:39
 */

import com.example.caffeine.entity.User;
import com.example.caffeine.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p></p>
 * Created by zhezhiyong@163.com on 2017/9/21.
 */
@RestController
public class IndexController {

    @Autowired
    private UserService userService;

    @RequestMapping("/index")
    public ModelAndView sayHello() {
        //return "hello.html";//访问静态页面

        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.setViewName("hello");
        modelAndView.addObject("key", "测试caffeine1");
        //return "hello";//访问动态页面,使用@RestController之后此种方式无法转到页面
        return modelAndView;
    }

    @GetMapping("/users")
    @ResponseBody
    public List<User> users() {
        return userService.list();
    }

    @GetMapping("/user/{id}")
    @ResponseBody
    public User findUserById(@PathVariable("id") Long id) {
        return userService.findUserById(id);
    }

    @GetMapping("/info/{id}")
    @ResponseBody
    public User findInfoById(@PathVariable("id") Long id) {
        return userService.findInfoById(id);
    }

    @GetMapping("/user/{id}/{name}")
    @ResponseBody
    public Map update(@PathVariable("id") Long id, @PathVariable("name") String name) {
        User user = userService.findUserById(id);
        user.setName(name);
        userService.update(user);
        Map<String, Object> result = new HashMap<>();
        result.put("ret", 0);
        result.put("msg", "ok");
        return result;
    }


}