package sejong_team.matching.SejongBoard.controller;

import org.springframework.web.bind.annotation.*;
import sejong_team.matching.SejongBoard.entity.Tag;
import sejong_team.matching.SejongBoard.service.TagService;

import java.util.List;
@RestController
@RequestMapping("/api/tags")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService){
        this.tagService=tagService;
    }
    @GetMapping
    public List<Tag> findAll(){
        return tagService.findAll();
    }
    @PostMapping
    public Tag save(@RequestParam Tag tag){
        return tagService.save(tag);
    }
}
