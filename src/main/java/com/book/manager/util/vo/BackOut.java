package com.book.manager.util.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 归还 vo对象
 */
@Data
public class BackOut extends BookOut{

    @ApiModelProperty("借阅时间")
    private String borrowTime;

    @ApiModelProperty("应还时间")
    private String endTime;
    
    @ApiModelProperty("ret")
    private int ret;

    @ApiModelProperty("是否逾期")
    private String late;


    public String getBorrowTime() {
        return borrowTime;
    }

    public void setBorrowTime(String borrowTime) {
        this.borrowTime = borrowTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
    
    public int getRet() {
    	return ret;
    }
    
    public void setRet(Integer ret) {
    	this.ret=ret;
    }

    public String getLate() {
        return late;
    }

    public void setLate(String late) {
        this.late = late;
    }
}
