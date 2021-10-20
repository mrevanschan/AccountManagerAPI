INSERT INTO CUSTOMER (id,password,balance)select * from (
select 12345678, '1234567890',1000000
) x where 12345678 not in (select id from CUSTOMER);

INSERT INTO CUSTOMER (id,password,balance)select * from (
select 88888888, '1234567890',1000000
) x where 88888888 not in (select id from CUSTOMER);

MERGE INTO CUSTOMER
  (ID, password,balance)
  KEY(ID)
VALUES (1, '1234567890',500),
  (2, '1234567890',0);