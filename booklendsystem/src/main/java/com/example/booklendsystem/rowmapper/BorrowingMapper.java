package com.example.booklendsystem.rowmapper;

import com.example.booklendsystem.model.Borrowing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BorrowingMapper implements RowMapper<Borrowing> {
    private static final Logger logger = LoggerFactory.getLogger(BorrowingMapper.class);
    @Override
    public Borrowing mapRow(ResultSet rs, int rowNum) throws SQLException {
        Borrowing borrowing = new Borrowing();
        String borrowing_time = String.valueOf(rs.getTimestamp("borrowing_time")).substring(0, 16);
        String return_time = String.valueOf(rs.getTimestamp("return_time"));
        borrowing.setRecord_id(rs.getInt("record_id"));
        borrowing.setMember_id(rs.getInt("user_id"));
        borrowing.setInventory_id(rs.getInt("inventory_id"));
        borrowing.setIsbn(rs.getString("isbn"));
        borrowing.setName(rs.getString("name"));
        borrowing.setAuthor(rs.getString("author"));
        borrowing.setBorrowing_time(borrowing_time);
        borrowing.setReturn_time(return_time.length() > 4? return_time.substring(0, 16): return_time);
        return borrowing;
    }
}
