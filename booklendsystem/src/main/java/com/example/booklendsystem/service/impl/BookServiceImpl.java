package com.example.booklendsystem.service.impl;

import com.example.booklendsystem.constant.StatusCategory;
import com.example.booklendsystem.dao.BookDao;
import com.example.booklendsystem.dto.BookRequest;
import com.example.booklendsystem.dto.SearchRequest;
import com.example.booklendsystem.model.Book;
import com.example.booklendsystem.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Component
public class BookServiceImpl implements BookService {
    @Autowired
    private BookDao bookDao;
    private static final Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);


    @Override
    public List<Book> getBooks() {
        return bookDao.getBooks();
    }

    @Override
    public Book getBook(String book_id) {
        return bookDao.getBook(book_id);
    }

    @Override
    public Book getBookByInventory(Integer inventory_id) {
        return bookDao.getBookByInventory(inventory_id);
    }

    @Override
    @Transactional
    public Integer postBook(BookRequest bookRequest) {
        logger.info("ADD BOOK Request: "+bookRequest.getIsbn());
        logger.info("ADD BOOK Name: "+bookRequest.getName());
        logger.info("ADD BOOK Author: "+bookRequest.getAuthor());
        logger.info("ADD BOOK Introduction: "+bookRequest.getIntroduction());
        return bookDao.postBook(bookRequest);
    }

    @Override
    @Transactional
    public void updateBook(Integer inventory_id, String status) {
        try {
            StatusCategory check = StatusCategory.valueOf(status);
            bookDao.updateBook(inventory_id, status);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid status: " + status);
        }
    }

    @Override
    @Transactional
    public Book deleteBookByInventory(Integer inventory_id) {
        return bookDao.deleteBookByInventory(inventory_id);
    }

    @Override
    public List<Book> searchProduct(SearchRequest searchRequest) {
        return bookDao.searchProduct(searchRequest);
    }
}
