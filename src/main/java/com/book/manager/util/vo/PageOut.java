package com.book.manager.util.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 分页返回
 */
@Data
public class PageOut {

    @ApiModelProperty("当前页")
    private Integer currPage;

    @ApiModelProperty("每页条数")
    private Integer pageSize;

    @ApiModelProperty("总数")
    private Integer total;

    @ApiModelProperty("数据")
    private Object list;

    public Integer getCurrPage() {
        return currPage;
    }

    public void setCurrPage(Integer currPage) {
        this.currPage = currPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Object getList() {
        return list;
    }

    public void setList(Object list) {
        this.list = list;
    }
}
