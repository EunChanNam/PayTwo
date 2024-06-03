INSERT INTO user_entity (id, name, username, password, notification_enabled)
VALUES (100, '홍길동', 'hong', 'password123', TRUE);
INSERT INTO user_entity (id, name, username, password, notification_enabled)
VALUES (101, '김땡글', 'kim', 'password123', TRUE);

INSERT INTO account_entity (id, bank_name, account_number, balance, user_id)
VALUES (200, '우리은행', 1234567890, 100000, 100);
INSERT INTO account_entity (id, bank_name, account_number, balance, user_id)
VALUES (201, '국민은행', 9876543210, 500000, 101);
