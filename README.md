# DB 초기 세팅

```sql


# 권한 테이블
create table role
(
    role_id int primary key,
    name    varchar(20) not null
);

# 미리 저장해야할 데이터
insert into role(role_id, name)
values (1, 'ROLE_USER');
insert into role(role_id, name)
values (2, 'ROLE_ADMIN');

# 유저 테이블 만들기
create table user
(
    user_id  int primary key auto_increment,
    email    varchar(255) not null,
    name varchar(50) not null,
    password varchar(500) not null,
    regdate timestamp not null default now()
);


# 회원권한 테이블 만들기
create table user_role
(
    user_id int,
    role_id int,
    primary key (user_id, role_id),
    foreign key (user_id) references user (user_id),
    foreign key (role_id) references role (role_id)
);

```