package com.book.manager.util.ro;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 图书归还 ro 对象
 */
@Data
public class RetBookIn {

    @ApiModelProperty("用户ID")
    private Integer userId;

    @ApiModelProperty("图书ID")
    private Integer bookId;
}
