package com.example.board.dao;

import com.example.board.dto.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

// 데이터를 처리하는 클래스
@Repository
public class UserDao {
    // Spring JDBC를 사용해서 DB에 접근한다.

    @Transactional
    public User addUser(String name, String email, String password) {
        // Service에서 이미 트랜젝션이 시작했기 때문에, 그 트렌젝션에 포함된다.
        // insert into user (name, email, password, regdate) values (?, ?, ?, now()); # user_id는 auto gen
        // select last_insert_id();
        return null;
    }


    @Transactional
    public void mappingUserRole(int userId) {
        // Service에서 이미 트랜젝션이 시작했기 때문에, 그 트렌젝션에 포함된다.
        // insert into user_role (user_id, role_name) values (?, 1);
    }
}

 /*
    insert into user (name, email, password, regdate) values (?, ?, ?, now()); # user_id는 auto gen
     select last_insert_id();
     insert into user_role (user_id, role_name) values (?, 1);
  */
