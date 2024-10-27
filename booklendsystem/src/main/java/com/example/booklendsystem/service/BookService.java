package com.example.booklendsystem.service;

import com.example.booklendsystem.dto.BookRequest;
import com.example.booklendsystem.dto.SearchRequest;
import com.example.booklendsystem.model.Book;
import com.example.booklendsystem.model.Borrowing;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BookService {
    public List<Book> getBooks();
    public Book getBook(String isbn);
    public List<Borrowing> getBorrowingRecord(String jwt, String model) throws Exception;
    public Book getBookByInventory(Integer inventory_id);
    public Integer postBook(BookRequest bookRequest);
    public void updateBook(String jwt, Integer inventory_id, String status);
    public Book deleteBookByInventory(Integer inventory_id);
    public List<Book> searchProduct(SearchRequest searchRequest);
}
