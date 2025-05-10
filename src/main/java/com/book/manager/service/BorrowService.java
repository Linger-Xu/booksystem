package com.book.manager.service;

import cn.hutool.core.bean.BeanUtil;
import com.book.manager.dao.BookMapper;
import com.book.manager.dao.BorrowMapper;
import com.book.manager.dao.UsersMapper;
import com.book.manager.entity.Book;
import com.book.manager.entity.Borrow;
import com.book.manager.entity.Users;
import com.book.manager.repos.BookRepository;
import com.book.manager.repos.BorrowRepository;
import com.book.manager.util.consts.Constants;
import com.book.manager.util.vo.BookOut;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @Description 借阅管理
 */
@Service
public class BorrowService {

    @Autowired
    private BorrowRepository borrowRepository;

    @Autowired
    private BorrowMapper borrowMapper;

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    /**
     * 添加
     * （添加事物）
     */
    @Transactional
    public Integer addBorrow(Borrow borrow) {
        Book book = bookService.findBook(borrow.getBookId());
        Users users = userService.findUserById(borrow.getUserId());
        // 库存数量减一
        int size = book.getSize();
        if (size>0) {
            size--;
            book.setSize(size);
            bookService.updateBook(book);
        }else {
            return Constants.BOOK_SIZE_NOT_ENOUGH;
        }

        // 用户积分扣除
        int userPoints = users.getSize();
        double bookPrice = book.getPrice();
        int requiredPoints = (int) Math.floor(bookPrice);
        if( userPoints >= requiredPoints ) {
        	userPoints -= requiredPoints;
        	users.setSize(userPoints);
        	userService.updateUser(users);
        } else {
        	return Constants.USER_POINTS_NOT_ENOUGH;
        }
        
        borrow.setRet(-1);
        borrowRepository.saveAndFlush(borrow);
        // 一切正常
        return Constants.OK;
    }
    
    @Transactional
    public Integer addBorrow2(Borrow borrow) {
        Book book = bookService.findBook(borrow.getBookId());
        int bookId=book.getId();
        // 库存数量减一
        int size = book.getSize();
        if (size>0) {
            size--;
            book.setSize(size);
            bookService.updateBook(book);
        }else {
            return Constants.BOOK_SIZE_NOT_ENOUGH;
        }
        int userId=borrow.getUserId();
        List<Borrow> borrows=this.findAllBorrowByUserId(userId);
        for(Borrow bborrow:borrows)
        {
        	if(bborrow.getRet()>2 && bborrow.getBookId()==bookId)
        	{
        		int ret=bborrow.getRet();
        		ret++;
        		bborrow.setRet(ret);
        		this.updateBorrow(bborrow);
        		return Constants.OK;
        	}
        }
        borrow.setRet(3);
        borrowRepository.saveAndFlush(borrow);
        // 一切正常
        return Constants.OK;
    }

    /**
     * user id查询所有借阅信息
     */
    public List<Borrow> findAllBorrowByUserId(Integer userId) {
        return borrowRepository.findBorrowByUserId(userId);
    }

    /**
     * user id查询所有 已借阅信息
     */
    public List<Borrow> findBorrowsByUserIdAndRet(Integer userId, Integer ret) {
        return borrowRepository.findBorrowsByUserIdAndRet(userId,ret);
    }


    /**
     * 详情
     */
    public Borrow findById(Integer id) {
        Optional<Borrow> optional = borrowRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }


    /**
     * 编辑
     */
    public boolean updateBorrow(Borrow borrow) {
        return borrowMapper.updateBorrow(borrow)>0;
    }


    /**
     * 编辑
     */
    public Borrow updateBorrowByRepo(Borrow borrow) {
        return borrowRepository.saveAndFlush(borrow);
    }

    /**
     * s删除
     */
    public void deleteBorrow(Integer id) {
        borrowRepository.deleteById(id);
    }

    /**
     * 查询用户某一条借阅信息
     * @param userId 用户id
     * @param bookId 图书id
     */
    public Borrow findBorrowByUserIdAndBookId(int userId,int bookId) {
        return borrowMapper.findBorrowByUserIdAndBookId(userId,bookId);
    }

    /**
     * 归还书籍, 使用事务保证 ACID
     * @param userId 用户Id
     * @param bookId 书籍id
     */
    @Transactional(rollbackFor = Exception.class)
    public void retBook(int userId,int bookId) {
        Borrow borrow = this.findBorrowByUserIdAndBookId(userId, bookId);
        this.deleteBorrow(borrow.getId());
    }
    
    public int cir(int bookId)
    {
    	Book book=bookService.findBook(bookId);
    	int price=(int) Math.floor(book.getPrice());	
    	return price;
    }
    
    @Transactional
    public void buyBooks(int userId,int totalAmount)
    {
        Users user = userService.findUserById(userId);
        int userPoints = user.getSize(); // 剩余积分

        // 扣除积分并更新用户信息
        user.setSize(userPoints - totalAmount);  // 扣除积分
        userService.updateUser(user);  // 更新用户信息

        List<Borrow> borrows = this.findAllBorrowByUserId(userId);
        for (Borrow borrow : borrows) {
            if (borrow.getRet() > 2) {  // 如果该记录仍在购物车中
            	int ret=borrow.getRet()-2;
            	ret=-ret;
                borrow.setRet(ret);  // 标记为已购买
                this.updateBorrow(borrow);  // 更新借阅记录
            }
        }
    }
    
    @Transactional
    public void delFromChart(Borrow borrow) {
    	int bookId=borrow.getBookId();
    	Book book=bookService.findBook(bookId);
    	int size=book.getSize();
    	size+=borrow.getRet()-2;
    	book.setSize(size);
    	bookService.updateBook(book);
    	this.deleteBorrow(borrow.getId());
    	
    }
}
