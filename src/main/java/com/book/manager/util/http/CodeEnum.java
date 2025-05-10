package com.book.manager.util.http;

/**
 * @Description 响应状态码枚举类
 */
public enum CodeEnum {
	NULLFAIL(100,"参数为空"),
    /** 请求成功 */
    SUCCESS(200,"成功!"),
    /** 您已借阅过该图书, 且未归还 */
    BOOK_BORROWED(300,"您已借阅过该图书, 且未归还!"),
    /** 图书库存不够,无法借阅! */
    BOOK_NOT_ENOUGH(301,"图书库存不够,无法购买!"),
    /** 用户可借数量不够,无法借阅! */
    USER_NOT_ENOUGH(302,"用户可借数量不够,无法借阅!"),
    /** 用户积分不足，无法借阅！ */
    USER_POINTS_NOT_ENOUGH(303,"用户积分不足，无法购买!"),
    /** 找不到资源 */
    NOT_FOUND(404,"找不到资源!"),
    /** 请求参数错误 */
    PARAM_ERROR(444,"请求参数错误!"),
    /** 用户名或密码错误 */
    NAME_OR_PASS_ERROR(445,"用户名或密码错误!"),
    /** 找不到用户 */
    USER_NOT_FOUND(446,"找不到用户!"),
    /** 服务器发生异常 */
    FAIL(500,"服务器发生异常!"),
    BOOK_NAME_NOT_EXIST_ERROR(-1,"请填写图书名称"),
    BOOK_ISBN_NOT_EXIST_ERROR(-1,"请填写编号"),
    BOOK_IMAGE_NOT_EXIST_ERROR(-1,"请填写封面图"),
    BOOK_AUTHOR_NOT_EXIST_ERROR(-1,"请填写作者"),
    BOOK_TYPE_NOT_EXIST_ERROR(-1,"请填写图书分类"),
    BOOK_ISBN_EXIST_ERROR(-1,"该图书编号已存在"),
    BOOK_ADD_ERROR(-1,"图书添加失败"),
    BOOK_EDIT_ERROR(-1,"图书编辑失败"),
    BOOK_NOT_EXIST_ERROR(-1,"未找到该图书"),
    USER_HEAD_PIC_ERROR(-1,"请上传头像"),
    USERNAME_NOT_EXIST_ERROR(-1,"用户名不能为空"),
    NICKNAME_NOT_EXIST_ERROR(-1,"昵称不能为空"),
    USER_MOBILE_NOT_EXIST_ERROR(-1,"请填写手机号"),
    USER_EMAIL_NOT_EXIST_ERROR(-1,"请填写邮箱"),
    USER_ADD_ERROR(-1,"读者添加失败"),
    USER_EDIT_ERROR(-1,"读者修改失败"),
    USER_NAME_IS_EXIST_ERROR(-1,"用户名已存在！");
    CodeEnum(int code, String data) {
        this.code = code;
        this.data = data;
    }

    private int code;
    private String data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
