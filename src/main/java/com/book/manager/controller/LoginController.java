package com.book.manager.controller;

import cn.hutool.core.util.StrUtil;
import com.book.manager.entity.Users;
import com.book.manager.service.UserService;
import com.book.manager.util.R;
import com.book.manager.util.http.CodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description 用户登录

 */
@Controller
public class LoginController {

    @Autowired
    private UserService userService;


   /* @ResponseBody
    @PostMapping(value = "/user/login")
    public R login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String identity = request.getParameter("identity");
        int role = 0;
        if (identity != null && identity.length() > 0) {
            role = Integer.parseInt(identity);
        }
        System.out.println(identity);
        if (StrUtil.isNotBlank(username) && StrUtil.isNotBlank(password)) {
            Users users = userService.login(username, password);
            if (users != null) {
                request.getSession().setAttribute("username", username);
                return R.success(CodeEnum.SUCCESS);
            }
        }

        return R.fail(CodeEnum.NAME_OR_PASS_ERROR);
    }*/
}
