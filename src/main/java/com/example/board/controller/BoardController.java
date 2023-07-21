package com.example.board.controller;

import com.example.board.dto.Board;
import com.example.board.dto.LoginInfo;
import com.example.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

// HTTP 요청을 받아서 응답을 하는 컴포넌트, 스프링 부트가 자동으로 Bean을 생성한다.
@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    // 게시물 목록을 보여준다.
    // 컨트롤러의 메소드가 리턴하는 문자열은 템플릿 이름이다.
    // localhost:8080  ---> "list"라는 이름의 템플릿 이름이다.
    // list 를 리턴한다는 것은
    // classpath:template/list
    @GetMapping("/")
    public String list(@RequestParam(name = "page", defaultValue = "1") int page,
                       HttpSession session, Model model) {
        LoginInfo loginInfo = (LoginInfo) session.getAttribute("loginInfo");
        model.addAttribute("loginInfo", loginInfo);
        // 게시물 목록을 불러온다
        // 페이징 처리한다
        int totalCount = boardService.getTotalCount();
        List<Board> list = boardService.getBoards(page); // page가 1,2,3,4
        System.out.println("totalCount : " + totalCount);

        for (Board board : list) {
            System.out.println(board);
        }

        // 총 페이지 수 구하기
        int pageCount = totalCount / 10;
        if (totalCount % 10 > 0) { // 나머지가 있을 경우 1페이지 추가
            pageCount += 1;
        }
        int currentPage = page; // 현재 페이지

        // 템플릿 엔진에 전달
        model.addAttribute("list", list);
        model.addAttribute("pageCount", pageCount);
        model.addAttribute("currentPage", currentPage);

        return "list";
    }

    // /board?id=3 // 파라미터 id , 파라미터 id 값은 3
    // /board?id=2
    // /board?id=1
    @GetMapping("/board")
    public String board(@RequestParam("boardId") int boardId, Model model) {
        System.out.println("boardId : " + boardId);

        // id에 해당하는 게시물을 읽어온다
        // id에 해당하는 게시물을 조회수도 1증가한다

        Board board = boardService.getBoard(boardId);
        model.addAttribute("board", board);
        return "board";
    }


    // 삭제한다. 관리자는 모든 글을 수정할 수 있다.
    // 수정한다 관리자는 모든 글을 수정할 수 있다.

    @GetMapping("/writeForm")
    public String writeForm(HttpSession session, Model model) {
        // 로그인한 사용자만 글을 써야한다. 로그인을 하지 않았다면 리스트보기로 자동 이동
        // 세션에서 로그인한 정보를 읽어들인다.
        LoginInfo loginInfo = (LoginInfo) session.getAttribute("loginInfo");
        if (loginInfo == null) { // 세션에 로그인 정보가 없다면 로그인 창으로 리다이렉트
            return "redirect:/loginform";
        }
        model.addAttribute("loginInfo", loginInfo);
        return "writeForm";
    }

    @PostMapping("/write")
    public String write(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            HttpSession session
    ) {
        LoginInfo loginInfo = (LoginInfo) session.getAttribute("loginInfo");
        if (loginInfo == null) { // 세션에 로그인 정보가 없다면 로그인 창으로 리다이렉트
            return "redirect:/loginform";
        }
        // 로그인한 사용자만 글을 써야한다.
        // 세션에서 로그인한 정보를 읽어들인다. 로그인을 하지 않았다면 리스트보기로 자동 이동
        System.out.println("title : " + title);
        System.out.println("content : " + content);

        boardService.addBoard(loginInfo.getUserId(), title, content);

        // 로그인한 회원 정보 + 제목, 내용을 저장한다.

        return "redirect:/"; // 리스트 보기로 리다이렉트한다.
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("boardId") int boardId,
                         HttpSession session
    ) {
        LoginInfo loginInfo = (LoginInfo) session.getAttribute("loginInfo");
        if (loginInfo == null) { // 세션에 로그인 정보가 없다면 로그인 창으로 리다이렉트
            return "redirect:/loginform";
        }

        // loginInfo.getUserId() 사용자가 쓴 글일 경우에만 삭제한다.
        boardService.deleteBoard(loginInfo.getUserId(), boardId);
        return "redirect:/";
    }
}
