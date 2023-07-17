# board
즐거운 스프링부트 : 미니 게시판 만들기 

[강의 주소](https://www.youtube.com/watch?v=D74HLM718_o&list=PLHpaQi-LiUCz9fX2gXiutdLpuut72mWsb&index=4&ab_channel=%EB%B6%80%EB%B6%80%EA%B0%9C%EB%B0%9C%EB%8B%A8-%EC%A6%90%EA%B2%81%EA%B2%8C%ED%94%84%EB%A1%9C%EA%B7%B8%EB%9E%98%EB%B0%8D%EB%B0%B0%EC%9A%B0%EA%B8%B0)

## 사용기술

* Spring Boot
* Spring MVC
* Spring JDBC
* MySQL
* Thymeleaf

## 기능

* 회원가입
* 로그인 (HTTP Session)
* 글 쓰기
* 글 목록 보기 (page 처리)
* 글 상세 보기
* 글 수정하기
* 글 삭제하기
* 로그아웃
* 관리자의 글 임의 삭제 



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


# 게시판 테이블 만들기
create table board
(
    board_id int primary key auto_increment,
    title varchar(100) not null,
    content text not null,
    user_id int not null,
    regdate timestamp not null default now(),
    view_cnt int default 0,
    foreign key (user_id) references user (user_id)
);


```




