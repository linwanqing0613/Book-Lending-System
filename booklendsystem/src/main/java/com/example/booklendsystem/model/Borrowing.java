package com.example.booklendsystem.model;

public class Borrowing {
    Integer record_id;
    String isbn;
    String name;
    String author;
    String borrowing_time;
    String return_time;

    public Integer getRecord_id() {
        return record_id;
    }

    public void setRecord_id(Integer record_id) {
        this.record_id = record_id;
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

    public String getBorrowing_time() {
        return borrowing_time;
    }

    public void setBorrowing_time(String borrowing_time) {
        this.borrowing_time = borrowing_time;
    }

    public String getReturn_time() {
        return return_time;
    }

    public void setReturn_time(String return_time) {
        this.return_time = return_time;
    }
}
