package course.courseWork.service;

import course.courseWork.exceptions.TagNotFoundException;
import course.courseWork.model.Tag;
import course.courseWork.model.Users;
import course.courseWork.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Override
    public Tag createTagByUser(Tag tag, Users user) {
        tag.setUsers(user);
        tagRepository.save(tag);
        return tag;
    }

    @Override
    public List<Tag> getAllTagsByUser(Users user) {
        return tagRepository.findAllByUsers(user);
    }

    @Override
    public void deleteTagByIdAndUser(Long id, Users user) throws TagNotFoundException {
        Optional<Tag> tag = tagRepository.findByTagIdAndUsers(id, user);
        if (tag.isPresent() && tag.get().getUsers().equals(user)) {
            tag.get().setUsers(null);
            tagRepository.delete(tag.get());
        } else {
            throw new TagNotFoundException();
        }
    }
}
