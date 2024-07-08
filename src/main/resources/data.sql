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


insert into account_types (name,code) values('Savings', 'SAVINGS');
insert into account_types (name,code) values('Current', 'CURRENT');


insert into account_status (name,code) values('Active','ACTIVE');
insert into account_status (name,code) values('Disable','DISABLE');



insert into states (name,code) values('Abia',upper('Abia'));
insert into states (name,code) values('Adamawa',upper('Adamawa'));
insert into states (name,code) values('Akwa Ibom',upper('Akwa_Ibom'));
insert into states (name,code) values('Anambra',upper('Anambra'));
insert into states (name,code) values('Abuja',upper('Abuja'));
insert into states (name,code) values('Bauchi',upper('Bauchi'));
insert into states (name,code) values('Bayelsa',upper('Bayelsa'));
insert into states (name,code) values('Benue',upper('Benue'));
insert into states (name,code) values('Borno',upper('Borno'));
insert into states (name,code) values('Cross River',upper('Cross_River'));
insert into states (name,code) values('Delta',upper('Delta'));
insert into states (name,code) values('Ebonyi',upper('Ebonyi'));
insert into states (name,code) values('Edo',upper('Edo'));
insert into states (name,code) values('Ekiti',upper('Ekiti'));
insert into states (name,code) values('Enugu',upper('Enugu'));
insert into states (name,code) values('Gombe',upper('Gombe'));
insert into states (name,code) values('Imo',upper('Imo'));
insert into states (name,code) values('Jigawa',upper('Jigawa'));
insert into states (name,code) values('Kaduna',upper('Kaduna'));
insert into states (name,code) values('Kano',upper('Kano'));
insert into states (name,code) values('Katsina',upper('Katsina'));
insert into states (name,code) values('Kebbi',upper('Kebbi'));
insert into states (name,code) values('Kogi',upper('Kogi'));
insert into states (name,code) values('Kwara',upper('Kwara'));
insert into states (name,code) values('Lagos',upper('Lagos'));
insert into states (name,code) values('Nasarawa',upper('Nasarawa'));
insert into states (name,code) values('Niger',upper('Niger'));
insert into states (name,code) values('Ogun',upper('Ogun'));
insert into states (name,code) values('Ondo',upper('Ondo'));
insert into states (name,code) values('Osun',upper('Osun'));
insert into states (name,code) values('Oyo',upper('Oyo'));
insert into states (name,code) values('Plateau',upper('Plateau'));
insert into states (name,code) values('Rivers',upper('Rivers'));
insert into states (name,code) values('Sokoto',upper('Sokoto'));
insert into states (name,code) values('Taraba',upper('Taraba'));
insert into states (name,code) values('Yobe',upper('Yobe'));
insert into states (name,code) values('Zamfara',upper('Zamfara'));


insert into accounts
(account_number,account_type_id,account_status_id,balance,first_name,middle_name,last_name,email,date_of_birth,gender_id,address,state_of_origin,phone_number,alternative_phone_number,created_by,created_at)
values
('2024000001',1,1,2450500.50,'Oluwaseun','Joseph','Olotu','seunolo2@gmail.com','1985-03-21',1,'Block 103, Flat 3, Plot 56, Zone A, Iba Housing Estate, Ojo, Lagos, Nigeria',28,'2348080643360','2349090643360',4,CURRENT_TIMESTAMP());

insert into accounts
(account_number,account_type_id,account_status_id,balance,first_name,middle_name,last_name,email,date_of_birth,gender_id,address,state_of_origin,phone_number,alternative_phone_number,created_by,created_at)
values
    ('2024000002',2,1,2500.50,'Oluwaseun','Joseph','Olotu','seunolo2@gmail.com','1985-03-21',1,'Block 103, Flat 3, Plot 56, Zone A, Iba Housing Estate, Ojo, Lagos, Nigeria',28,'2348080643360','2349090643360',4,CURRENT_TIMESTAMP());

insert into transaction_types (name,code) values ( 'Transfer',upper('Transfer'));
insert into transaction_types (name,code) values ( 'Deposit',upper('deposit'));
insert into transaction_types (name,code) values ( 'Withdrawal',upper('Withdrawal'));


insert into transaction_statuses (name,code) values ( 'Successful',upper('successful') );
insert into transaction_statuses (name,code) values ( 'Pending',upper('pending') );
insert into transaction_statuses (name,code) values ( 'Failed',upper('failed') );

insert into amount_types (name,code) values('Credit',upper('Credit'));
insert into amount_types (name,code) values('Debit',upper('Debit'));
