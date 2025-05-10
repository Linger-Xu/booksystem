package com.book.manager.controller;

import cn.hutool.core.date.DateUtil;
import com.book.manager.entity.Borrow;
import com.book.manager.service.BookService;
import com.book.manager.service.BorrowService;
import com.book.manager.util.R;
import com.book.manager.util.consts.Constants;
import com.book.manager.util.http.CodeEnum;
import com.book.manager.util.ro.RetBookIn;
import com.book.manager.util.vo.BackOut;
import com.book.manager.util.vo.BookOut;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description 用户管理

 */
@Api(tags = "借阅管理")
@RestController
@RequestMapping("/borrow")
public class BorrowController {

    @Autowired
    private BorrowService borrowService;

    @Autowired
    private BookService bookService;

    @ApiOperation("借阅列表")
    @GetMapping("/list")
    public R getBorrowList(Integer userId) {
        return R.success(CodeEnum.SUCCESS,borrowService.findAllBorrowByUserId(userId));
    }
    
    

    @ApiOperation("借阅图书")
    @PostMapping("/add")
    public R addBorrow(@RequestBody Borrow borrow) {
        Integer result = borrowService.addBorrow(borrow);
        if (result == Constants.BOOK_BORROWED) {
            return R.success(CodeEnum.BOOK_BORROWED);
        }else if (result == Constants.USER_POINTS_NOT_ENOUGH) {
            return R.success(CodeEnum.USER_POINTS_NOT_ENOUGH);
        }else if (result == Constants.BOOK_SIZE_NOT_ENOUGH) {
            return R.success(CodeEnum.BOOK_NOT_ENOUGH);
        }
        return R.success(CodeEnum.SUCCESS,Constants.OK);
    }
    
    @ApiOperation("加入购物车")
    @PostMapping("/addintochart")
    public R addintothechart(@RequestParam Integer userId, @RequestParam Integer bookId) {
    	Borrow borrow = new Borrow();
    	borrow.setUserId(userId);
    	borrow.setBookId(bookId);
        // 获取当前系统时间，作为日期
        Date currentDate = new Date();
        
        // 设置系统日期到 Borrow 对象的各个日期字段
        borrow.setCreateTime(currentDate);  // 设置借阅时间为当前时间
        borrow.setUpdateTime(currentDate);  // 设置更新日期为当前时间
        borrow.setEndTime(currentDate);     // 设置归还时间为当前时间（可以根据需要设置为当前时间或其他逻辑）
        
        // 调用借阅服务进行添加操作
        Integer result = borrowService.addBorrow2(borrow);

        // 根据不同的处理结果返回不同的消息
        if (result == Constants.BOOK_BORROWED) {
            return R.success(CodeEnum.BOOK_BORROWED);
        } else if (result == Constants.USER_POINTS_NOT_ENOUGH) {
            return R.success(CodeEnum.USER_POINTS_NOT_ENOUGH);
        } else if (result == Constants.BOOK_SIZE_NOT_ENOUGH) {
            return R.success(CodeEnum.BOOK_NOT_ENOUGH);
        }
        
        return R.success(CodeEnum.SUCCESS, Constants.OK);
    }


    @ApiOperation("编辑借阅")
    @PostMapping("/update")
    public R modifyBorrow(@RequestBody Borrow borrow) {
        return R.success(CodeEnum.SUCCESS,borrowService.updateBorrow(borrow));
    }


    @ApiOperation("借阅详情")
    @GetMapping("/detail")
    public R borrowDetail(Integer id) {
        return R.success(CodeEnum.SUCCESS,borrowService.findById(id));
    }

    @ApiOperation("删除归还记录")
    @PostMapping("/delete")
    @Transactional
    public R delBorrow(Integer id) {
        Borrow borrow=borrowService.findById(id);
        if (borrow == null) {
            System.out.println("Borrow record not found for id: " + id);
        } else {
        	borrow.setRet(2);
            borrowService.updateBorrow(borrow);
            System.out.println("Borrow record updated, ret set to 0");
        }
        return R.success(CodeEnum.SUCCESS);
    }


    @ApiOperation("已借阅列表")
    @GetMapping("/borrowed")
    public R borrowedList(Integer userId) {
        List<BackOut> outs = new ArrayList<>();
        if (userId!=null&&userId>0) {
            // 获取所有 已借阅 未归还书籍
            List<Borrow> borrows = borrowService.findAllBorrowByUserId(userId);
            for (Borrow borrow : borrows) {
                BackOut backOut = new BackOut();
                BookOut out = bookService.findBookById(borrow.getBookId());
                BeanUtils.copyProperties(out,backOut);

                backOut.setBorrowTime(DateUtil.format(borrow.getCreateTime(),Constants.DATE_FORMAT));

                String endTimeStr = DateUtil.format(borrow.getEndTime(), Constants.DATE_FORMAT);
                backOut.setEndTime(endTimeStr);
                // 判断是否逾期
                String toDay = DateUtil.format(new Date(), Constants.DATE_FORMAT);
                int i = toDay.compareTo(endTimeStr);
                if (i>0) {
                    backOut.setLate(Constants.YES_STR);
                }else {
                    backOut.setLate(Constants.NO_STR);
                }
                int label_id=borrow.getId();
                backOut.setId(label_id); 
                backOut.setRet(borrow.getRet());
                if(borrow.getRet()<0) {
                	outs.add(backOut);
                }
            }
        }

        return R.success(CodeEnum.SUCCESS,outs);
    }
    
    @ApiOperation("已加入购物车列表")
    @GetMapping("/addtoshopchart")
    public R shopedList(Integer userId) {
        List<BackOut> outs = new ArrayList<>();
        if (userId!=null&&userId>0) {
            List<Borrow> borrows = borrowService.findAllBorrowByUserId(userId);
            for (Borrow borrow : borrows) {
                BackOut backOut = new BackOut();
                BookOut out = bookService.findBookById(borrow.getBookId());
                BeanUtils.copyProperties(out,backOut);

                backOut.setBorrowTime(DateUtil.format(borrow.getCreateTime(),Constants.DATE_FORMAT));

                String endTimeStr = DateUtil.format(borrow.getEndTime(), Constants.DATE_FORMAT);
                backOut.setEndTime(endTimeStr);
                // 判断是否逾期
                String toDay = DateUtil.format(new Date(), Constants.DATE_FORMAT);
                int i = toDay.compareTo(endTimeStr);
                if (i>0) {
                    backOut.setLate(Constants.YES_STR);
                }else {
                    backOut.setLate(Constants.NO_STR);
                }
                int label_id=borrow.getId();
                backOut.setId(label_id);
                backOut.setRet(borrow.getRet());
                int ret=borrow.getRet();
                if(ret>2){
                	outs.add(backOut);
                }
            }
        }

        return R.success(CodeEnum.SUCCESS,outs);
    }
    
    @ApiOperation("计算总金额")
    @GetMapping("/check")
    public int checkAccout(Integer userId) {
    	int sum=0;
    	List<Borrow> borrows=borrowService.findAllBorrowByUserId(userId);
    	for(Borrow borrow:borrows)
    	{
    		if(borrow.getRet()>2)
    		{
    			int bookId=borrow.getBookId();
    			int price=borrowService.cir(bookId);
    			int num=borrow.getRet()-2;
    			sum=sum+num*price;
    		}
    	}
    	return sum;
    }

    @ApiOperation("归还书籍")
    @PostMapping("/ret")
    public R retBook(Integer userId, Integer bookId) {
        // 归还图书
        borrowService.retBook(userId,bookId);
        return R.success(CodeEnum.SUCCESS);
    }
    

    @ApiOperation("购买图书")
    @PostMapping("/buy")
    public R buyBooks(@RequestParam Integer userId, @RequestParam Integer totalAmount) {
        // 打印接收到的参数
        System.out.println("Received userId: " + userId);
        System.out.println("Received totalAmount: " + totalAmount);

        if (userId == null || totalAmount == null) {
            // 如果任意参数为 null, 返回错误提示
            return R.fail(CodeEnum.NULLFAIL);
        }

        borrowService.buyBooks(userId, totalAmount);
        return R.success(CodeEnum.SUCCESS);
    }
    
    @ApiOperation("删除归还记录")
    @PostMapping("/deletefromchart")
    @Transactional
    public R delFromChart(Integer id) {
        Borrow borrow=borrowService.findById(id);
        borrowService.delFromChart(borrow);
        return R.success(CodeEnum.SUCCESS);
    }



}
