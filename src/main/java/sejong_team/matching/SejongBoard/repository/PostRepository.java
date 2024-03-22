package sejong_team.matching.SejongBoard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sejong_team.matching.SejongBoard.entity.Post;
import sejong_team.matching.SejongBoard.entity.Tag;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByTagsContaining(List<Tag> tag);
}
