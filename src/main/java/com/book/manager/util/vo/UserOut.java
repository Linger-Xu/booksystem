package com.book.manager.util.vo;

import com.book.manager.entity.Users;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 用户vo类
 */
@Data
public class UserOut extends Users{

    @ApiModelProperty("身份(中文：学生/教师/社会人士/管理员)")
    private String ident;

    @ApiModelProperty("生日：yyyy-MM-dd格式")
    private String birth;


    public String getIdent() {
        return ident;
    }

    public void setIdent(String ident) {
        this.ident = ident;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }
}
