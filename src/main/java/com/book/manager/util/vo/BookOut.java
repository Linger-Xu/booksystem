package com.book.manager.util.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 图书出参对象
 */
@Data
public class BookOut {

    @ApiModelProperty("书籍ID")
    private Integer id;

    @ApiModelProperty("书籍ISBN编码")
    private String isbn;

    @ApiModelProperty("书名")
    private String name;

    @ApiModelProperty("作者")
    private String author;

    @ApiModelProperty("页数")
    private Integer pages;

    @ApiModelProperty("翻译")
    private String translate;

    @ApiModelProperty("出版社")
    private String publish;

    @ApiModelProperty("定价")
    private Double price;

    @ApiModelProperty("库存")
    private Integer size;

    @ApiModelProperty("分类")
    private String type;

    @ApiModelProperty("出版时间")
    private String publishTime;

    @ApiModelProperty("封面图")
    private String pic;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public String getTranslate() {
        return translate;
    }

    public void setTranslate(String translate) {
        this.translate = translate;
    }

    public String getPublish() {
        return publish;
    }

    public void setPublish(String publish) {
        this.publish = publish;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
