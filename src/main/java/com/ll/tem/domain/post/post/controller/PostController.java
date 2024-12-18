package com.ll.tem.domain.post.post.controller;

import com.ll.tem.domain.post.post.entity.Post;
import com.ll.tem.domain.post.post.service.PostService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;


    @GetMapping
    public String showList(Model model) {
        List<Post> posts = postService.findAll();
        model.addAttribute("posts", posts.reversed());

        return "domain/post/post/list";
    }

    @GetMapping("/{id}")
    public String showDetail(Model model, @PathVariable long id) {
        Post post = postService.findById(id).get();

        model.addAttribute("post", post);
        return "domain/post/post/detail";
    }

    //목적지 url이 자신의 url과 같으면 생략 가능
    @GetMapping("/write")
    public String showWrite(PostWriteForm form) {
        return "domain/post/post/write";
    }

    @PostMapping("/write")
    public String write(@Valid PostWriteForm form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "domain/post/post/write";
        }

        postService.save( Post.builder()
                .title(form.title)
                .content(form.content)
                .build());

        return "redirect:/posts";
    }

    private record PostWriteForm(
            @NotBlank(message = "01-제목을 입력해주세요.")
            @Length(min = 2, message = "02-제목을 2자 이상 입력해주세요.")
            String title,

            @NotBlank(message = "03-내용을 입력해주세요.")
            @Length(min = 2, message = "04-내용을 2자 이상 입력해주세요.")
            String content

    ) {
    }

}
