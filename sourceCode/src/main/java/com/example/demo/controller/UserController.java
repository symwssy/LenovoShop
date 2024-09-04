package com.example.demo.controller;

import com.example.demo.pojo.Result;
import com.example.demo.pojo.User;
import com.example.demo.service.UserService;
import com.example.demo.utils.JwtUtil;
import com.example.demo.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public Result register(@Pattern(regexp="^\\S{5,16}$") String username,@Pattern(regexp="^\\S{5,16}$") String password) {
            //查询用户
            User u = userService.findbyUserName(username);
            if (u == null) { //注册
                userService.register(username, password);
                return Result.success();
            } else {
                return Result.error("用户名已被占用");
            }
    }


    @PostMapping("/login")
    public Result login(@Pattern(regexp="^\\S{5,16}$") String username, @Pattern(regexp="^\\S{5,16}$") String password){
        //根据用户名查询用户
        User loginUser = userService.findbyUserName(username);
        //判断用户是否存在
        if (loginUser == null) {
            return Result.error("用户名错误");
        }
        //判断密码是否正确
        if (loginUser.getPassword().equals(password)) {
            Map<String,Object> claims = new HashMap<>();
            claims.put("id",loginUser.getId());
            claims.put("username",loginUser.getUsername());
            String token = JwtUtil.genToken(claims);
            return Result.success(token);
        }
        return Result.error("密码错误");
    }

    @GetMapping("/userInfo")
    public Result<User> userInfo(@RequestHeader(name="Authorization") String token){
        //根据用户名查询用户
        Map<String,Object> map =  ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User user = userService.findbyUserName(username);
        return Result.success(user);
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Validated User user){
        userService.update(user);
        return Result.success();
    }

    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl){
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }

    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String,String> params){
        //手动校验参数
        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        String rePwd = params.get("re_pwd");

        if (!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd) || !StringUtils.hasLength(rePwd)){
            return Result.error("缺少密钥参数");
        }

        Map<String,Object> map =  ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User loginUser = userService.findbyUserName(username);
        if (!loginUser.getPassword().equals(oldPwd))
            return Result.error("原密码错误");
        if (!newPwd.equals(rePwd))
            return Result.error("两次填写的新密码不一致");
        //调用service完成密码更新

        userService.updatePwd(newPwd);

        return Result.success();
    }

}
