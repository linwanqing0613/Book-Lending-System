package com.example.booklendsystem.controller;

import com.example.booklendsystem.dto.BookRequest;
import com.example.booklendsystem.dto.SearchRequest;
import com.example.booklendsystem.model.Book;
import com.example.booklendsystem.model.Borrowing;
import com.example.booklendsystem.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {

    @Autowired
    private BookService bookService;
    @GetMapping("/book")
    public ResponseEntity<List<Book>> getBooks(){
        List<Book> books = bookService.getBooks();
        return books != null? ResponseEntity.ok(books): ResponseEntity.notFound().build();
    }
    @GetMapping("/book/{ibsn}")
    public ResponseEntity<Book> getBook(@PathVariable String ibsn){
        Book book = bookService.getBook(ibsn);
        return book != null? ResponseEntity.ok(book): ResponseEntity.notFound().build();
    }
    @GetMapping("/record/{model}")
    public ResponseEntity<List<Borrowing>> getBorrowingRecord(@RequestHeader("Authorization") String jwt, @PathVariable String model) throws Exception {
        List<Borrowing> borrowings = bookService.getBorrowingRecord(jwt, model);
        return borrowings != null? ResponseEntity.ok(borrowings): ResponseEntity.notFound().build();
    }
    @PostMapping("/book")
    public ResponseEntity<Book> postBook(@RequestBody BookRequest bookRequest){
        Integer inventory_id = bookService.postBook(bookRequest);
        Book book = bookService.getBookByInventory(inventory_id);
        return book != null? ResponseEntity.ok(book): ResponseEntity.notFound().build();
    }
    @PutMapping("/book/{inventory_id}/{book_status}")
    public ResponseEntity<Book> updateBook(@RequestHeader("Authorization") String jwt, @PathVariable Integer inventory_id, @PathVariable String book_status){
        bookService.updateBook(jwt, inventory_id, book_status);
        Book book = bookService.getBookByInventory(inventory_id);
        return book != null? ResponseEntity.ok(book): ResponseEntity.notFound().build();
    }
    @DeleteMapping("/book/{ibsn}")
    public ResponseEntity<Book> deleteBook(@PathVariable String ibsn){
        return null;
    }
    @GetMapping("/books")
    public ResponseEntity<List<Book>> searchBook(
            @RequestParam(required = false) String isbn,
            @RequestParam(required = false) String status,

            @RequestParam(defaultValue = "order") String order,
            @RequestParam(defaultValue = "ASC") String sort
    ) {
        SearchRequest searchParameter = new SearchRequest();
        searchParameter.setIsbn("".equals(isbn)? null: isbn);
        searchParameter.setStatus("".equals(status)? null: status);
        searchParameter.setOrder(order);
        searchParameter.setSort(sort);
        List<Book> list = bookService.searchProduct(searchParameter);
        return list == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(list);
    }
}
