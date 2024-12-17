package com.ll.tem.domain.post.post.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/posts")
public class PostController {

    //목적지 url이 자신의 url과 같으면 생략 가능wwww
    @GetMapping("/write")
    @ResponseBody
    public String showWrite() {
        return """
                <form method="POST">
                    <input type="text" name="title" placeholder="제목">
                    <textarea name="content" placeholder="내용"></textarea>
                    <button type="submit">글쓰기</button>
                </form>
                """;
    }
    @PostMapping("/write")
    @ResponseBody
    public String doWrite(String title, String content) {
        if (title == null || title.isBlank()) {
            return """
                <div>%s</div>
                <form method="POST">
                    <input type="text" name="title" placeholder="제목">
                    <textarea name="content" placeholder="내용"></textarea>
                    <button type="submit">글쓰기</button>
                </form>
                """.formatted("제목을 입력하세요");
        }
        if (content == null || content.isBlank()) {
            return "내용을 입력하세요";
        }
        return """
                <h1>글쓰기 완료</h1>
                <div>
                    <h2>%s</h2>
                    <p>%s</p>
                </div>
                """.formatted(title, content);
    }
}
