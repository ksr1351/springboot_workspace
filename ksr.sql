

CREATE TABLE todolist(
id number  PRIMARY KEY,
completed number(1) DEFAULT 0,
todoname  VARCHAR2(100) NOT NULL);


CREATE SEQUENCE todo_id_seq
START WITH 1
INCREMENT By 1
NOCACHE
NOCYCLE;


INSERT INTO todolist VALUES(todo_id_seq.nextval, 0, '여행');
commit;
SELECT * FROM todolist;


select * from todolist;

delete from todolist
where id=17;
commit;


select * from board;