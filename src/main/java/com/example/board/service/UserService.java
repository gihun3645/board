package com.example.board.service;

import com.example.board.dao.UserDao;
import com.example.board.dto.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// 트랜잭션 단위로 실행될 메소드를 선언하고 있는 클래스
// 스프링이 관리하고 있는 Bean
@Service
@RequiredArgsConstructor // lombok 이 final 필드를 초기화하는 생성자를 자동으로 생성한다.
public class UserService {
    // UserDao를 인젝션 받아야함
    private final UserDao userDao;
//    // final 키워드를 사용하면 생성자 주입을 해야함
//    // spring 이 UserService를 Bean으로 생성할 때 생성자를 이용해 생성을 하는데, 이때 UserDao Bean이 있는지 보고
//    // 그 빈을 주입한다. 생성자 주입.
//    public UserService(UserDao userDao) {
//        this.userDao = userDao;
//    }


    // 보통 서비스에서는 @Transactional 을 붙여서 하나의 트렌젝션으로 처리하게 된다.
    // Spring Boot는 트렌잭션을 처리해주는 트랜젝션 관리자를 가지고 있다.
    @Transactional
    public User addUser(String name, String email, String password) {
        // 트랜젝션이 시작된다.
        // User 선언
        User user = userDao.addUser(email, name, password);
        userDao.mappingUserRole(user.getUserId()); // 권한을 부여한다.
        return user;
        // 트랜젝션이 끝난다.
    }
}
