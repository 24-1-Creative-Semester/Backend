package sejong_team.matching.SejongBoard.service;

import org.springframework.stereotype.Service;
import sejong_team.matching.SejongBoard.entity.Tag;
import sejong_team.matching.SejongBoard.repository.TagRepository;

import java.util.List;

@Service
public class TagService {

    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository){
        this.tagRepository = tagRepository;
    }

    public List<Tag> findAll(){
        return tagRepository.findAll();
    }

    public Tag save(Tag tag){
        return tagRepository.save(tag);
    }
}
