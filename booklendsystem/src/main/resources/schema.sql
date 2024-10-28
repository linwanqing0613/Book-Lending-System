CREATE TABLE IF NOT EXISTS inventory (
    inventory_id INT AUTO_INCREMENT PRIMARY KEY UNIQUE, -- 每個庫存紀錄的唯一ID
    isbn VARCHAR(255) NOT NULL,                         -- 書籍的國際標準書號 (ISBN)
    store_time DATETIME NOT NULL,                       -- 書籍入庫的日期和時間
    status VARCHAR(255) NOT NULL,                       -- 書籍狀態 (例如：可借出、已借出等)

    CONSTRAINT uk_book_inventory UNIQUE (isbn, inventory_id) -- 確保每個ISBN與庫存ID組合唯一
);

CREATE TABLE IF NOT EXISTS book (
    isbn VARCHAR(255) PRIMARY KEY,                      -- 書籍的國際標準書號 (ISBN) (主鍵)
    name VARCHAR(255) NOT NULL,                         -- 書名
    author VARCHAR(255) NOT NULL,                       -- 作者名稱
    Introduction VARCHAR(255) NOT NULL,                 -- 書籍的簡介

    CONSTRAINT uk_book_isbn UNIQUE (isbn)               -- 確保每本書的ISBN唯一
);

CREATE TABLE IF NOT EXISTS borrowing_record (
    record_id INT PRIMARY KEY AUTO_INCREMENT,           -- 每筆借閱紀錄的唯一ID
    user_id INT NOT NULL,                               -- 借書的使用者ID
    inventory_id INT NOT NULL,                          -- 借出的書籍的庫存ID
    borrowing_time DATETIME NOT NULL,                   -- 借書的日期和時間
    return_time DATETIME,                               -- 還書的日期和時間

    INDEX idx_user_id (user_id),                        -- 使用者ID的索引，加速查詢
    INDEX idx_inventory_id (inventory_id),              -- 庫存ID的索引，加速查詢
    INDEX idx_borrowing_time (borrowing_time)           -- 借書時間的索引，加速查詢
);

CREATE TABLE IF NOT EXISTS members (
    member_id INT AUTO_INCREMENT PRIMARY KEY,           -- 每個會員的唯一ID
    phone_number VARCHAR(15) NOT NULL UNIQUE,           -- 手機號碼 (每位會員唯一)
    password VARCHAR(255) NOT NULL,                     -- 會員密碼 (需經過哈希和加鹽處理)
    member_name VARCHAR(100) NOT NULL,                  -- 會員名稱
    registration_time DATETIME DEFAULT CURRENT_TIMESTAMP, -- 註冊日期和時間
    last_login_time DATETIME,                           -- 最後登入時間
    INDEX idx_member_name (phone_number)                -- 手機號碼的索引，加速查詢
);

-- 以下是儲存程序

DELIMITER //

-- 查詢所有書籍及其庫存詳情
CREATE PROCEDURE SELECT_ALL_BOOK()
BEGIN
    SELECT inventory_id, inventory.isbn, name, author, Introduction, status
    FROM inventory
    LEFT JOIN book ON book.isbn = inventory.isbn;
END //

-- 根據ISBN查詢特定書籍詳情
CREATE PROCEDURE SELECT_BOOK(IN book_isbn VARCHAR(255))
BEGIN
    SELECT inventory_id, inventory.isbn, name, author, Introduction, status
    FROM inventory
    LEFT JOIN book ON book.isbn = inventory.isbn
    WHERE inventory.isbn = book_isbn;
END //

-- 根據狀態查詢書籍
CREATE PROCEDURE SELECT_BOOK_BY_STATUS(IN p_status VARCHAR(255))
BEGIN
    SELECT inventory_id, inventory.isbn, name, author, Introduction, status
    FROM inventory
    LEFT JOIN book ON book.isbn = inventory.isbn
    WHERE status = p_status;
END //

-- 根據ISBN和狀態查詢書籍
CREATE PROCEDURE SELECT_BOOK_BY_ISBN_AND_STATUS(IN book_isbn VARCHAR(255), IN p_status VARCHAR(255))
BEGIN
    SELECT inventory_id, inventory.isbn, name, author, Introduction, status
    FROM inventory
    LEFT JOIN book ON book.isbn = inventory.isbn
    WHERE inventory.isbn = book_isbn AND status = p_status;
END //

-- 根據庫存ID查詢書籍
CREATE PROCEDURE SELECT_BOOK_BY_INVENTORY(IN p_inventory_id INT(11))
BEGIN
    SELECT inventory_id, inventory.isbn, name, author, Introduction, status
    FROM inventory
    LEFT JOIN book ON book.isbn = inventory.isbn
    WHERE inventory_id = p_inventory_id;
END //

-- 新增一筆書籍資料到書籍表
CREATE PROCEDURE ADD_BOOK(
    IN p_isbn VARCHAR(255),
    IN p_name VARCHAR(255),
    IN p_author VARCHAR(255),
    IN p_introduction VARCHAR(255)
)
BEGIN
    IF NOT EXISTS (SELECT 1 FROM book WHERE isbn = p_isbn) THEN
        INSERT INTO book (isbn, name, author, Introduction)
        VALUES (p_isbn, p_name, p_author, p_introduction);
    END IF;
END //

-- 新增書籍到庫存紀錄
CREATE PROCEDURE ADD_INVENTORY(
    IN p_isbn VARCHAR(255),
    IN p_store_time DATETIME,
    IN p_status VARCHAR(255),
    OUT p_inventory_id INT
)
BEGIN
    INSERT INTO inventory (isbn, store_time, status)
    VALUES (p_isbn, p_store_time, p_status);

    SET p_inventory_id = LAST_INSERT_ID(); -- 輸出生成的庫存ID
END //

-- 更新庫存紀錄的狀態
CREATE PROCEDURE UPDATE_INVENTORY(
    IN p_inventory_id INT,
    IN p_status VARCHAR(255)
)
BEGIN
    UPDATE inventory
    SET status = p_status
    WHERE inventory_id = p_inventory_id;
END //

-- 新增一筆借閱紀錄
CREATE PROCEDURE ADD_BORROWING_RECORD(
    IN p_user_id INT,
    IN p_inventory_id INT
)
BEGIN
    INSERT INTO borrowing_record (user_id, inventory_id, borrowing_time)
    VALUES (p_user_id, p_inventory_id, NOW());
END //

-- 更新借閱紀錄以標記歸還
CREATE PROCEDURE UPDATE_BORROWING_RECORD(
    IN p_user_id INT,
    IN p_inventory_id INT,
    IN p_status VARCHAR(255)
)
BEGIN
    UPDATE borrowing_record
    SET return_time = NOW()
    WHERE user_id = p_user_id AND inventory_id = p_inventory_id;

    UPDATE inventory
    SET status = p_status
    WHERE inventory_id = p_inventory_id;
END //

-- 查詢會員未歸還的借閱紀錄
CREATE PROCEDURE SEARCH_BORROWING(
    IN p_user_id INT
)
BEGIN
    SELECT br.record_id, br.user_id, br.inventory_id, b.isbn, b.name, b.author, br.borrowing_time, br.return_time
    FROM borrowing_record br
    LEFT JOIN inventory i ON br.inventory_id = i.inventory_id
    LEFT JOIN book b ON b.isbn = i.isbn
    WHERE br.user_id = p_user_id AND br.return_time IS NULL;
END //

-- 查詢會員已歸還的所有借閱紀錄
CREATE PROCEDURE SEARCH_RECORD(
    IN p_user_id INT
)
BEGIN
    SELECT br.record_id, br.user_id, br.inventory_id, b.isbn, b.name, b.author, br.borrowing_time, br.return_time
    FROM borrowing_record br
    LEFT JOIN inventory i ON br.inventory_id = i.inventory_id
    LEFT JOIN book b ON b.isbn = i.isbn
    WHERE br.user_id = p_user_id AND br.return_time IS NOT NULL;
END //

-- 註冊新會員
CREATE PROCEDURE REGISTER(
    IN p_phone_number VARCHAR(15),
    IN p_password VARCHAR(255),
    IN p_member_name VARCHAR(100),
    OUT p_member_id INT
)
BEGIN
    INSERT INTO members (phone_number, password, member_name)
    VALUES (p_phone_number, p_password, p_member_name);

    SET p_member_id = LAST_INSERT_ID(); -- 輸出生成的會員ID
END //

-- 根據手機號碼查詢會員
CREATE PROCEDURE GET_MEMBER_BY_PHONE(
    IN p_phone_number VARCHAR(15)
)
BEGIN
    SELECT member_id, phone_number, password, member_name, registration_time, last_login_time
    FROM members
    WHERE phone_number = p_phone_number;
END //

-- 根據會員ID查詢會員
CREATE PROCEDURE GET_MEMBER_BY_ID(
    IN p_member_id INT
)
BEGIN
    SELECT member_id, phone_number, password, member_name, registration_time, last_login_time
    FROM members
    WHERE member_id = p_member_id;
END //

-- 更新會員的最後登入時間
CREATE PROCEDURE UPDATE_LOGIN_TIME(
    IN p_phone_number VARCHAR(15)
)
BEGIN
    UPDATE members
    SET last_login_time = NOW()
    WHERE phone_number = p_phone_number;
END //

-- 更新會員資料
CREATE PROCEDURE UPDATE_MEMBER(
    IN p_phone_number VARCHAR(15),
    IN p_password VARCHAR(255),
    IN p_member_name VARCHAR(100)
)
BEGIN
    UPDATE members
    SET password = p_password,
        member_name = p_member_name
    WHERE phone_number = p_phone_number;
END //

-- 根據手機號碼刪除會員
CREATE PROCEDURE DELETE_MEMBER(
    IN p_phone_number VARCHAR(15)
)
BEGIN
    DELETE FROM members
    WHERE phone_number = p_phone_number;
END //
