package sejong_team.matching.SejongBoard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sejong_team.matching.SejongBoard.entity.Post;
import sejong_team.matching.SejongBoard.entity.Tag;
import sejong_team.matching.SejongBoard.repository.PostRepository;
import sejong_team.matching.SejongBoard.repository.TagRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final TagRepository tagRepository;



    public PostService(PostRepository postRepository,TagRepository tagRepository){
        this.postRepository = postRepository;
        this.tagRepository = tagRepository;
    }

    public List<Post> findAll(){
        return postRepository.findAll();
    }

    public List<Post> findByTags(List<String> tags) {
        List<Tag> tagList = new ArrayList<>();
        for (String tagName : tags) {
            Tag tag=tagRepository.findByName(tagName);
            if(tag !=null){
                tagList.add(tag);
            }
        }
        return postRepository.findByTagsContaining(tagList);
    }


}
