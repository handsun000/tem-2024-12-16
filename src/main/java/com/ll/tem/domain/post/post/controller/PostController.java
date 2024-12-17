package com.ll.tem.domain.post.post.controller;

import com.ll.tem.domain.post.post.entity.Post;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/posts")
public class PostController {

    public List<Post> posts = new ArrayList<>() {{
        add(
                Post.builder()
                        .title("제목1")
                        .content("내용1")
                        .build()
        );
        add(
                Post.builder()
                        .title("제목2")
                        .content("내용2")
                        .build()
        );
        add(
                Post.builder()
                        .title("제목3")
                        .content("내용3")
                        .build()
        );
    }};

    @GetMapping
    @ResponseBody
    public String showList() {

        String ul = "<ul>" +
                posts
                        .reversed()
                        .stream()
                        .map(post -> "<li>%s</li>".formatted(post.getTitle()))
                        .collect(Collectors.joining()) + "</ul>";
        String body = """
                <h1>글 목록</h1>
                %s
                <a href="/posts/write">글쓰기</a>
                """.formatted(ul);

        return body;
    }

    //목적지 url이 자신의 url과 같으면 생략 가능wwww
    @GetMapping("/write")
    @ResponseBody
    public String showWrite() {
        return getFormHtml("", "", "");
    }

    @PostMapping("/write")
    public ResponseEntity<String> write(@Valid PostWriteForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessages = bindingResult
                    .getAllErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .sorted()
                    .map(message -> message.split("-", 2)[1])
                    .collect(Collectors.joining("<br>"));

            return ResponseEntity
                    .badRequest()
                    .body(
                            getFormHtml(errorMessages, form.title, form.content)
                    );
        }
        posts.add(
                Post.builder()
                        .title(form.title)
                        .content(form.content)
                        .build()
        );
        return ResponseEntity.status(302).header("Location", "/posts").build();
    }

    private String getFormHtml(String errorMessage, String title, String content) {
        return """
                <div>%s</div>
                <form method="POST">
                    <input type="text" name="title" placeholder="제목" value="%s">
                    <br>
                    <textarea name="content" placeholder="내용">%s</textarea>
                    <br>
                    <button type="submit">글쓰기</button>
                </form>
                """.formatted(errorMessage, title, content);
    }

    private record PostWriteForm(
            @NotBlank(message = "01-제목을 입력해주세요.")
            @Length(min = 5, message = "02-제목을 5자 이상 입력해주세요.")
            String title,

            @NotBlank(message = "03-내용을 입력해주세요.")
            @Length(min = 10, message = "04-내용을 10자 이상 입력해주세요.")
            String content

    ) {
    }

}
