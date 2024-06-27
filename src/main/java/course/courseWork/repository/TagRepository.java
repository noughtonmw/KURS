package course.courseWork.repository;

import course.courseWork.model.Tag;
import course.courseWork.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    Optional<Tag> findByTagIdAndUsers(Long id, Users user);

    List<Tag> findAllByUsers(Users user);

}
