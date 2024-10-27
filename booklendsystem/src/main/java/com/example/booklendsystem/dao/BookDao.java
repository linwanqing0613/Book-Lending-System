package com.example.booklendsystem.dao;

import com.example.booklendsystem.dto.BookRequest;
import com.example.booklendsystem.dto.SearchRequest;
import com.example.booklendsystem.model.Book;
import com.example.booklendsystem.model.Borrowing;

import java.util.List;

public interface BookDao {
    public List<Book> getBooks();
    public Book getBook(String book_id);
    public List<Borrowing> getBorrowingRecord(Integer member_id, String model);
    public Book getBookByInventory(Integer inventory_id);
    public Integer postBook(BookRequest bookRequest);
    public void updateBook(Integer member_id, Integer inventory_id, String status);
    public Book deleteBookByInventory(Integer inventory_id);
    public List<Book> searchProduct(SearchRequest searchRequest);
}
