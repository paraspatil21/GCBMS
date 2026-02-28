USE gcbms;

DELIMITER $$

DROP PROCEDURE IF EXISTS insert_customers $$

CREATE PROCEDURE insert_customers()
BEGIN
    DECLARE i INT DEFAULT 1;

    WHILE i <= 100 DO
        INSERT IGNORE INTO Customers (username, password, full_name)
        VALUES (
            CONCAT('customer', i),
            'customer123',
            CONCAT('ग्राहक ', i)
        );
        SET i = i + 1;
    END WHILE;
END $$

DELIMITER ;

CALL insert_customers();
DROP PROCEDURE IF EXISTS insert_customers;
