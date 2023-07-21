package com.example.board.controller;

import com.example.board.dao.UserDao;
import com.example.board.dto.LoginInfo;
import com.example.board.dto.User;
import com.example.board.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // localhost:8080/userRegForm
    // classpath:/templates/userRegForm.html
    @GetMapping("/userRegForm")
    public String userRegForm() {
        return "userRegForm";
    }

    /**
     * 회원가입을 처리한다.
     *
     * @param name
     * @param email
     * @param password
     * @return
     */


    @PostMapping("/userReg")
    public String userReg(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("password") String password
    ) {
        System.out.println("name : " + name);
        System.out.println("email : " + email);
        System.out.println("password : " + password);

        userService.addUser(name, email, password);

        // 어떤 기능이 필요한지 미리 알 수 있다. (인터페이스를 구현 하자!)
        // 회원정보를 저장한다.

        return "redirect:/welcome"; // 브라우저에게 자동으로 localhost:8080/welcome 으로 이동
    }


    // localhost:8080/welcome
    @GetMapping("/welcome")
    public String welcome() {
        return "welcome";
    }

    @GetMapping("/loginform")
    public String loginform() {
        return "loginform";
    }


    @PostMapping("/login")
    public String login(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            HttpSession httpSession
    ) {
        // email 에 해당하는 회원 정보를 읽어온 후
        // 아이디 암호가 맞다면 세션에 회원정보를 저장한다.
        System.out.println("email : " + email);
        System.out.println("password : " + password);

        try {
            User user = userService.getUser(email);
            if (user.getPassword().equals(password)) {
                System.out.println("암호가 일치합니다.");
                LoginInfo loginInfo = new LoginInfo(user.getUserId(), user.getEmail(), user.getName());

                // 권한정보를 읽어와서 loginInfo에 추가한다
                List<String> roles = userService.getRoles(user.getUserId());
                loginInfo.setRoles(roles);

                httpSession.setAttribute("loginInfo", loginInfo);
                System.out.println("세션에 로그인 정보를 저장했습니다.");
                return "redirect:/";
            } else {
                System.out.println("암호가 일치하지 않습니다." );
                return "redirect:/loginform?error=true";
            }
        } catch (Exception e) {
            return "redirect:/loginform?error=true";
        }
    }


    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        // 세션에서 회원정보를 삭제한다.
        httpSession.removeAttribute("loginInfo");
        return "redirect:/";
    }
}
