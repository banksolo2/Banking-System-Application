insert into roles (name,code) values ('ROLE_ADMIN','Admin');
insert into roles (name,code) values ('ROLE_DEVELOPER','Developer');
insert into roles (name,code) values ('ROLE_USER','User');

insert into genders (name) values('Male');
insert into genders (name) values ('Female');


insert into users (first_name, middle_name, last_name, gender_id,email,phone, password,is_active,is_deleted, created_at)
values('Oluwaseun','Joseph','Olotu', 1, 'seunolo2@gmail.com','08080643360','$2a$10$0XcwlivZ1hhetG8jNq1yI.A4WuM1rD6OOdQNKQ0SYugnufA8dsBFa',true,false,CURRENT_TIMESTAMP());
insert into users (first_name, middle_name, last_name, gender_id,email,password,is_active,is_deleted, created_by, created_at)
values('Banking','System','App', 1, 'admin@gmail.com','$2a$10$vcYDiFEm/0IobK5Qe6G3kOsgSgi8YfeqLf3YymKNAb2eWFdnPQKlS',true,false,1,CURRENT_TIMESTAMP());
insert into users (first_name, middle_name, last_name, gender_id,email,phone,password,is_active,is_deleted,created_by, created_at)
values('Femi','Joseph','Olotu', 1, 'josepholo2@yahoo.ca','08080643360','$2a$10$vcYDiFEm/0IobK5Qe6G3kOsgSgi8YfeqLf3YymKNAb2eWFdnPQKlS',true,false,1, CURRENT_TIMESTAMP());
insert into users (first_name, middle_name, last_name, gender_id,email,phone,password,is_active,is_deleted,created_by, created_at)
values('Kunle','Peter','Olotu', 1, 'peterolo2001@gmail.com','08080643360','$2a$10$vcYDiFEm/0IobK5Qe6G3kOsgSgi8YfeqLf3YymKNAb2eWFdnPQKlS',true,false,1,CURRENT_TIMESTAMP());


insert into users_roles (user_id,role_id) values(1,2);
insert into users_roles (user_id,role_id) values(2,1);
insert into users_roles (user_id,role_id) values(3,3);
insert into users_roles (user_id,role_id) values(4,3);


insert into banks (name, code,created_at,created_by,is_deleted) values('Access Bank Plc',upper('Access_Bank_Plc'),CURRENT_TIMESTAMP(),1,false);
insert into banks (name, code,created_at,created_by,is_deleted) values('Citibank Nigeria Limited',upper('Citibank_Nigeria_Limited'),CURRENT_TIMESTAMP(),1,false);
insert into banks (name, code,created_at,created_by,is_deleted) values('Ecobank Nigeria Plc',upper('Ecobank_Nigeria_Plc'),CURRENT_TIMESTAMP(),1,false);
insert into banks (name, code,created_at,created_by,is_deleted) values('Fidelity Bank Plc',upper('Fidelity_Bank_Plc'),CURRENT_TIMESTAMP(),1,false);
insert into banks (name, code,created_at,created_by,is_deleted) values('First Bank Nigeria Limited',upper('First_Bank_Nigeria_Limited'),CURRENT_TIMESTAMP(),1,false);
insert into banks (name, code,created_at,created_by,is_deleted) values('First City Monument Bank Plc',upper('First_City_Monument_Bank_Plc'),CURRENT_TIMESTAMP(),1,false);
insert into banks (name, code,created_at,created_by,is_deleted) values('Globus Bank Limited',upper('Globus_Bank_Limited'),CURRENT_TIMESTAMP(),1,false);
insert into banks (name, code,created_at,created_by,is_deleted) values('Guaranty Trust Bank Plc',upper('Guaranty_Trust_Bank_Plc'),CURRENT_TIMESTAMP(),1,false);
insert into banks (name, code,created_at,created_by,is_deleted) values('Heritage Banking Company Ltd',upper('Heritage_Banking_Company_Ltd'),CURRENT_TIMESTAMP(),1,false);
insert into banks (name, code,created_at,created_by,is_deleted) values('Keystone Bank Limited',upper('Keystone_Bank_Limited'),CURRENT_TIMESTAMP(),1,false);