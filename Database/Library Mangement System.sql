-- physical schema
CREATE TABLE books (
book_id INT AUTO_INCREMENT PRIMARY KEY , 
title VARCHAR(40) NOT NULL ,
author_name VARCHAR(40) NOT NULL ,
publisher_name VARCHAR(40) NOT NULL , 
exist BOOLEAN NOT NULL
);
CREATE TABLE members (
    member_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(40) NOT NULL,
    last_name VARCHAR(40) NOT NULL,
    email VARCHAR(40) NOT NULL,
    phone CHAR(11)
);
CREATE TABLE borrowing (
    book_id INT,
    member_id INT,
    borrowing_date DATE NOT NULL,
    due_date DATE NOT NULL,
    FOREIGN KEY (book_id) REFERENCES books(book_id),
    FOREIGN KEY (member_id) REFERENCES members(member_id) ,
    PRIMARY KEY (member_id , book_id , borrowing_date)
);
CREATE TABLE returning (
    member_id INT ,
    book_id INT ,
    returning_date DATE NOT NULL,
    FOREIGN KEY (book_id) REFERENCES books(book_id),
    FOREIGN KEY (member_id) REFERENCES members(member_id) , 
    PRIMARY KEY (member_id , book_id , returning_date)
);
CREATE TABLE fines (
    fine_id INT AUTO_INCREMENT PRIMARY KEY,
    fine_date DATE NOT NULL,
    fine_value DECIMAL(10, 2) NOT NULL,
    member_id INT ,
    FOREIGN KEY (member_id) REFERENCES members(member_id)
);
-- trigger
DELIMITER $$
CREATE TRIGGER returnings
AFTER INSERT ON returning
FOR EACH ROW
BEGIN
    UPDATE books
    SET exist = 1
    WHERE book_id = NEW.book_id;
    IF NEW.returning_date > (SELECT due_date FROM borrowing WHERE book_id = NEW.book_id AND member_id = NEW.member_id) THEN
		INSERT INTO fines(fine_date , fine_value , member_id)
        VALUES (NEW.returning_date , 5.00 , NEW.member_id);
	END IF ;
END $$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER borrowings
AFTER INSERT ON borrowing
FOR EACH ROW
BEGIN
    UPDATE books
    SET exist = 0
    WHERE book_id = NEW.book_id;
END $$
DELIMITER ;
-- insertion
INSERT INTO books (title, author_name, publisher_name)
VALUES
('The Great Gatsby', 'F. Scott Fitzgerald', 'Scribner'),
('1984', 'George Orwell', 'Secker & Warburg'),
('To Kill a Mockingbird', 'Harper Lee', 'J.B. Lippincott & Co.'),
('The Catcher in the Rye', 'J.D. Salinger', 'Little, Brown and Company'),
('Pride and Prejudice', 'Jane Austen', 'Thomas Egerton');
INSERT INTO members (first_name, last_name, email, phone)
VALUES
('Ahmed', 'Ali', 'ahmed.ali@example.com', '01234567890'),
('Sara', 'Mohamed', 'sara.mohamed@example.com', '01122334455'),
('Omar', 'Hassan', 'omar.hassan@example.com', '01011223344'),
('Noha', 'Adel', 'noha.adel@example.com', '01556677889'),
('Khaled', 'Youssef', 'khaled.youssef@example.com', '01211223355');
INSERT INTO borrowing(book_id , member_id , borrowing_date , due_date)
VALUES(1 , 2 , '2024-05-10' , '2024-05-20');
INSERT INTO returning(member_id , book_id , returning_date)
VALUES(2 , 1 , '2024-05-21') ;
-- DML FROM HERE --
select * from books ; 
select * from members ; 
select * from borrowing ;
select * from returning ;
select * from fines ;











