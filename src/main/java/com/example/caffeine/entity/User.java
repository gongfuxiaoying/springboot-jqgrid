package com.example.caffeine.entity;

/**
 * @author baoqy
 * @version 1.0
 * @date 2021/3/10 11:36
 */


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p></p>
 * Created by zhezhiyong@163.com on 2017/9/21.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Long id;
    private String name;
    private String password;

}