insert into roles (name,code) values ('ROLE_ADMIN','Admin');
insert into roles (name,code) values ('ROLE_DEVELOPER','Developer');
insert into roles (name,code) values ('ROLE_USER','User');

insert into genders (name) values('Male');
insert into genders (name) values ('Female');


insert into users (first_name, middle_name, last_name, gender_id,email,phone, password,is_active,is_deleted)
values('Oluwaseun','Joseph','Olotu', 1, 'seunolo2@gmail.com','08080643360','$2a$10$0XcwlivZ1hhetG8jNq1yI.A4WuM1rD6OOdQNKQ0SYugnufA8dsBFa',true,false);
insert into users (first_name, middle_name, last_name, gender_id,email,password,is_active,is_deleted)
values('Banking','System','App', 1, 'admin@gmail.com','$2a$10$vcYDiFEm/0IobK5Qe6G3kOsgSgi8YfeqLf3YymKNAb2eWFdnPQKlS',true,false);
insert into users (first_name, middle_name, last_name, gender_id,email,phone,password,is_active,is_deleted,created_by)
values('Femi','Joseph','Olotu', 1, 'josepholo2@yahoo.ca','08080643360','$2a$10$vcYDiFEm/0IobK5Qe6G3kOsgSgi8YfeqLf3YymKNAb2eWFdnPQKlS',true,false,1);
insert into users (first_name, middle_name, last_name, gender_id,email,phone,password,is_active,is_deleted,created_by)
values('Kunle','Peter','Olotu', 1, 'peterolo2001@gmail.com','08080643360','$2a$10$vcYDiFEm/0IobK5Qe6G3kOsgSgi8YfeqLf3YymKNAb2eWFdnPQKlS',true,false,1);


insert into users_roles (user_id,role_id) values(1,2);
insert into users_roles (user_id,role_id) values(2,1);
insert into users_roles (user_id,role_id) values(3,3);
insert into users_roles (user_id,role_id) values(4,3);

