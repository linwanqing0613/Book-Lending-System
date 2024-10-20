CREATE TABLE IF NOT EXISTS inventory (
    inventory_id INT AUTO_INCREMENT PRIMARY KEY UNIQUE,        -- 庫存 ID
    isbn VARCHAR(255) NOT NULL,                 -- 國際標準書號
    store_time DATETIME NOT NULL,               -- 書籍入庫(購買)日期時間
    status VARCHAR(255) NOT NULL,               -- 書籍狀態

    CONSTRAINT uk_book_inventory UNIQUE (isbn, inventory_id)
);

CREATE TABLE IF NOT EXISTS book (
    isbn VARCHAR(255) PRIMARY KEY,              -- 國際標準書號
    name VARCHAR(255) NOT NULL,                 -- 書名
    author VARCHAR(255) NOT NULL,               -- 作者
    Introduction VARCHAR(255) NOT NULL,         -- 書籍內容簡介

    CONSTRAINT uk_book_isbn UNIQUE (isbn)
);

CREATE TABLE IF NOT EXISTS borrowing_record (
    record_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,                        -- 使用者ID
    inventory_id INT NOT NULL,                   -- 庫存ID
    borrowing_time DATETIME NOT NULL,            -- 借出日期時間
    return_time DATETIME,                        -- 歸還日期時間

    INDEX idx_user_id (user_id),
    INDEX idx_inventory_id (inventory_id),
    INDEX idx_borrowing_time (borrowing_time)
);

