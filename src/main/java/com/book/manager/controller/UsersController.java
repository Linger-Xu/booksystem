package com.book.manager.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.book.manager.entity.Users;
import com.book.manager.service.UserService;
import com.book.manager.util.R;
import com.book.manager.util.consts.Constants;
import com.book.manager.util.consts.ConvertUtil;
import com.book.manager.util.http.CodeEnum;
import com.book.manager.util.vo.PageOut;
import com.book.manager.util.ro.PageIn;
import com.book.manager.util.vo.UserOut;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description 用户管理

 */
@Api(tags = "用户管理")
@RestController
@RequestMapping("/user")
public class UsersController {

    @Autowired
    private UserService userService;

    @ApiOperation("用户列表")
    @PostMapping("/list")
    public R getUsers(@RequestBody PageIn pageIn) {
        if (pageIn == null) {
            return R.fail(CodeEnum.PARAM_ERROR);
        }
        // 封装分页出参对象
        PageInfo<Users> userList = userService.getUserList(pageIn);
        PageOut pageOut = new PageOut();
        pageOut.setCurrPage(userList.getPageNum());
        pageOut.setPageSize(userList.getPageSize());
        pageOut.setTotal((int) userList.getTotal());
        List<UserOut> outs = new ArrayList<>();
        for (Users users : userList.getList()) {
            UserOut out = new UserOut();
            BeanUtils.copyProperties(users,out);
            out.setIdent(ConvertUtil.identStr(users.getIdentity()));
            out.setBirth(DateUtil.format(users.getBirthday(),Constants.DATE_FORMAT));
            outs.add(out);
        }
        pageOut.setList(outs);
        return R.success(CodeEnum.SUCCESS,pageOut);
    }


    /**
     * 添加读者操作
     * @param users
     * @return
     */
    @ApiOperation("添加读者")
    @ResponseBody
    @PostMapping("/addReader")
    public R addReader(Users users) {
        if (users == null) {
            return R.fail(CodeEnum.PARAM_ERROR);
        }
        
        if(StringUtils.isEmpty(users.getAvatar())) {
            return R.fail(CodeEnum.USER_HEAD_PIC_ERROR);
        }
        if(StringUtils.isEmpty(users.getUsername())) {
            return R.fail(CodeEnum.USERNAME_NOT_EXIST_ERROR);
        }
        if(StringUtils.isEmpty(users.getNickname())) {
            return R.fail(CodeEnum.NICKNAME_NOT_EXIST_ERROR);
        }
        if(StringUtils.isEmpty(users.getTel())) {
            return R.fail(CodeEnum.USER_MOBILE_NOT_EXIST_ERROR);
        }
        if(StringUtils.isEmpty(users.getEmail())) {
            return R.fail(CodeEnum.USER_EMAIL_NOT_EXIST_ERROR);
        }
        Users byUsername = userService.findByUsername(users.getUsername());
        if(byUsername!=null){
            return R.fail(CodeEnum.USER_NAME_IS_EXIST_ERROR);
        }
        // 读者默认是普通用户
        users.setSize(1000);
        users.setIsAdmin(1);
        Users users1 = userService.addUser(users);
        if(users1==null){
            return R.fail(CodeEnum.USER_ADD_ERROR);
        }
        return R.success(CodeEnum.SUCCESS);
    }


    /**
     * 编辑用户操作
     * @param users
     * @return
     */
    @ApiOperation("编辑用户")
    @ResponseBody
    @PostMapping("/edit")
    public R modifyUsers(Users users) {
        if (users == null) {
            return R.fail(CodeEnum.PARAM_ERROR);
        }
        if(StringUtils.isEmpty(users.getAvatar())) {
            return R.fail(CodeEnum.USER_HEAD_PIC_ERROR);
        }
        if(StringUtils.isEmpty(users.getUsername())) {
            return R.fail(CodeEnum.USERNAME_NOT_EXIST_ERROR);
        }
        if(StringUtils.isEmpty(users.getNickname())) {
            return R.fail(CodeEnum.NICKNAME_NOT_EXIST_ERROR);
        }
        if(StringUtils.isEmpty(users.getTel())) {
            return R.fail(CodeEnum.USER_MOBILE_NOT_EXIST_ERROR);
        }
        if(StringUtils.isEmpty(users.getEmail())) {
            return R.fail(CodeEnum.USER_EMAIL_NOT_EXIST_ERROR);
        }
        Users byUsername = userService.findByUsername(users.getUsername());
        if(byUsername!=null){
            if(!byUsername.getId().equals(users.getId())){
                return R.fail(CodeEnum.USER_NAME_IS_EXIST_ERROR);
            }
        }
        if(!userService.updateUser(users)){
            return R.fail(CodeEnum.USER_EDIT_ERROR);
        }
        return R.success(CodeEnum.SUCCESS);
    }


    @ApiOperation("用户详情")
    @GetMapping("/detail")
    public R userDetail(Integer id) {
        Users user = userService.findUserById(id);
        if (user!=null) {
            UserOut out = new UserOut();
            BeanUtils.copyProperties(user,out);
            out.setBirth(DateUtil.format(user.getBirthday(),Constants.DATE_FORMAT));
            out.setIdent(ConvertUtil.identStr(user.getIdentity()));
            return R.success(CodeEnum.SUCCESS,out);
        }

        return R.fail(CodeEnum.NOT_FOUND);
    }

    @ApiOperation("删除用户")
    @GetMapping("/delete")
    public R delUsers(Integer id) {
        userService.deleteUser(id);
        return R.success(CodeEnum.SUCCESS);
    }

    @ApiOperation("获取当前用户登陆信息")
    @GetMapping("/currUser")
    public R getCurrUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal!=null) {
            Map<String,Object> map = BeanUtil.beanToMap(principal);
            String username = (String) map.get("username");
            if (StrUtil.isNotBlank(username)) {
                Users users = userService.findByUsername(username);
                UserOut out = new UserOut();
                BeanUtils.copyProperties(users,out);
                out.setBirth(DateUtil.format(users.getBirthday(),Constants.DATE_FORMAT));
                Integer identity = users.getIdentity();
                String ident = "";
                if (identity == Constants.STUDENT) {
                    ident = Constants.STU_STR;
                }else if (identity == Constants.TEACHER) {
                    ident = Constants.TEA_STR;
                }else if (identity == Constants.OTHER) {
                    ident = Constants.OTHER_STR;
                }else if (identity == Constants.ADMIN) {
                    ident = Constants.ADMIN_STR;
                }
                out.setIdent(ident);
                return R.success(CodeEnum.SUCCESS,out);
            }
        }
        return R.fail(CodeEnum.USER_NOT_FOUND);
    }
}
