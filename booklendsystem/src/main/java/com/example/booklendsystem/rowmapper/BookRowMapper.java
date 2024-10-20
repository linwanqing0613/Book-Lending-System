package com.example.booklendsystem.rowmapper;

import com.example.booklendsystem.model.Book;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookRowMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        Book book = new Book();
        book.setInventory_id(rs.getInt("inventory_id"));
        book.setIsbn(rs.getString("isbn"));
        book.setName(rs.getString("name"));
        book.setAuthor(rs.getString("author"));
        book.setIntroduction(rs.getString("Introduction"));
        book.setStatus(rs.getString("status"));
        return book;
    }
}
