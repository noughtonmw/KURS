package course.courseWork.service;

import course.courseWork.exceptions.TagNotFoundException;
import course.courseWork.model.Tag;
import course.courseWork.model.Users;

import java.util.List;

public interface TagService {

    Tag createTagByUser(Tag tag, Users user);

    List<Tag> getAllTagsByUser(Users user);

    void deleteTagByIdAndUser(Long id, Users user) throws TagNotFoundException;
}
