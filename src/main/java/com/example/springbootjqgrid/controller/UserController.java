package com.example.springbootjqgrid.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.springbootjqgrid.entity.PageUtils;
import com.example.springbootjqgrid.entity.User;
import com.example.springbootjqgrid.service.UserService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/findAll")
    public List<User> findAll(){
        return userService.findAll();
    }

    @RequestMapping("/queryPages")
    public Map<String,PageUtils> queryPages(String username, @RequestParam Map<String, String> params){

        String page1 = params.get("page");
        String limit = params.get("limit");
        int fromIndex = Integer.parseInt(page1);
        int subIndex = Integer.parseInt(page1) * Integer.parseInt(limit);
        List<User> result = getResult(fromIndex, subIndex);
        PageUtils page = new PageUtils(result,200,Integer.parseInt(limit),fromIndex);
        Map<String,PageUtils> map = new HashMap<>();
        map.put("rows", page);
        return map;
    }


    @RequestMapping("/queryPagesFromJsonfile")
    public List<User> queryPagesFromJsonfile(String username, @RequestParam Map<String, Object> params){
        List<User> result = getResult(1, 10);
        return result;
    }

    public static List<User> getResult(int fromIndex,int subIndex){
        List<User> result = new ArrayList<>();

        JSONObject jsonObject = fileToJson("static\\jqgrid\\json\\user.json");
        JSONArray array = (JSONArray)jsonObject.get("data");
        for (int i = 0; i < array.size(); i++) {
            JSONObject jsonObject1 = array.getJSONObject(i);
            Long  id = Long.parseLong(jsonObject1.get("id").toString());
            String username1 = (String)jsonObject1.get("username");
            String email = (String)jsonObject1.get("email");
            String city = (String)jsonObject1.get("city");
            User u = new User();
            u.setId(id);
            u.setUsername(username1);
            u.setAddress(city);
            u.setEmail(email);
            result.add(u);
        }
        return result.subList(fromIndex, subIndex);
    }
    public static JSONObject fileToJson(String fileName) {
        JSONObject json = null;
        try (
                InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
        ) {
            json = JSONObject.parseObject(IOUtils.toString(is, "utf-8"));
        } catch (Exception e) {
            System.out.println(fileName + "文件读取异常" + e);
        }
        return json;
    }
}