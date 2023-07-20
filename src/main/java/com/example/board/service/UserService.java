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

    @Transactional
    public User addUser(String name, String email, String password) {
        User user = userDao.addUser(name, email, password);
        System.out.println(user);
        userDao.mappingUserRole(user.getUserId());
        return user;
    }

    @Transactional
    public User getUser(String email) {
        return userDao.getUser(email);
    }

}
