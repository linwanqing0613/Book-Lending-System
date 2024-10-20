package com.example.booklendsystem.dao.impl;

import com.example.booklendsystem.constant.StatusCategory;
import com.example.booklendsystem.dao.BookDao;
import com.example.booklendsystem.dto.BookRequest;
import com.example.booklendsystem.dto.SearchRequest;
import com.example.booklendsystem.model.Book;
import com.example.booklendsystem.rowmapper.BookRowMapper;
import com.example.booklendsystem.service.impl.BookServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BookDaoImpl implements BookDao {
    private static final Logger logger = LoggerFactory.getLogger(BookDaoImpl.class);
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Override
    public List<Book> getBooks() {
        Map<String, Object> map = new HashMap<>();
        String sql = "call SELECT_ALL_BOOK();";
        List<Book> list = namedParameterJdbcTemplate.query(sql, map,new BookRowMapper());
        return list.isEmpty() ? null: list;
    }

    public Book getBook(String isbn){
        Map<String, Object> map = new HashMap<>();
        String sql = "call SELECT_BOOK(:isbn);";
        map.put("isbn",isbn);
        List<Book> list = namedParameterJdbcTemplate.query(sql, map,new BookRowMapper());
        return list.isEmpty() ? null: list.get(0);
    }

    @Override
    public Book getBookByInventory(Integer inventory_id) {
        Map<String, Object> map = new HashMap<>();
        String sql = "call SELECT_BOOK_BY_INVENTORY(:inventory_id);";
        map.put("inventory_id",inventory_id);
        List<Book> list = namedParameterJdbcTemplate.query(sql, map,new BookRowMapper());
        return list.isEmpty() ? null: list.get(0);
    }

    @Override
    public Integer postBook(BookRequest bookRequest) {
        Map<String, Object> map = new HashMap<>();

        String sql = "call ADD_BOOK(:isbn, :name, :author,:introduction);";
        map.put("isbn", bookRequest.getIsbn());
        map.put("name", bookRequest.getName());
        map.put("author", bookRequest.getAuthor());
        map.put("introduction", bookRequest.getIntroduction());
        namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource(map));

        map.clear();

        sql = "call ADD_INVENTORY(:isbn, :store_time, :status, @inventory_id);";
        map.put("isbn", bookRequest.getIsbn());
        map.put("store_time",getTime());
        map.put("status", StatusCategory.READY.toString());
        namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource(map));

        sql = "SELECT @inventory_id";
        return namedParameterJdbcTemplate.queryForObject(sql, map, Integer.class);
    }

    @Override
    public void updateBook(Integer inventory_id, String status) {
        Map<String, Object> map = new HashMap<>();
        String sql = "call UPDATE_INVENTORY(:inventory_id, :status);";
        map.put("inventory_id", inventory_id);
        map.put("status", status);
        namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource(map));
    }

    @Override
    public Book deleteBookByInventory(Integer inventory_id) {
        return null;
    }

    @Override
    public List<Book> searchProduct(SearchRequest searchRequest) {
        Map<String, Object> map = new HashMap<>();
        String sql = "call SELECT_ALL_BOOK();";
        if(searchRequest.getIsbn() == null) {
            if (searchRequest.getStatus() != null) {
                sql = "call SELECT_BOOK_BY_STATUS( :status);";
                map.put("status", searchRequest.getStatus());
            }
        }else if(searchRequest.getStatus() == null){
            if (searchRequest.getIsbn() != null) {
                sql = "call SELECT_BOOK( :isbn);";
                map.put("isbn", searchRequest.getIsbn());
            }
        }else if(searchRequest.getStatus() != null && searchRequest.getIsbn() != null){
            sql = "call SELECT_BOOK_BY_ISBN_AND_STATUS( :isbn, :status);";
            map.put("status", searchRequest.getStatus());
            map.put("isbn", searchRequest.getIsbn());
        }
        logger.info(searchRequest.getIsbn());
        logger.info(sql);
        List<Book> list = namedParameterJdbcTemplate.query(sql, map,new BookRowMapper());
        return list.isEmpty() ? null: list;
    }

    private String getTime(){
        LocalDateTime nowDate = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return  nowDate.atZone(ZoneId.of("GMT+8")).format(formatter);
    }
}
