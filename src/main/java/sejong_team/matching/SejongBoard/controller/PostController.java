package sejong_team.matching.SejongBoard.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sejong_team.matching.SejongBoard.entity.Post;
import sejong_team.matching.SejongBoard.service.PostService;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService){
        this.postService=postService;
    }

    @GetMapping
    public List<Post> findAll(){
        return postService.findAll();
    }

    @GetMapping("/tags")
    public List<Post> findByTags(@RequestParam List<String> tags){
        return postService.findByTags(tags);
    }
}
