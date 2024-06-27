package course.courseWork.service;

import course.courseWork.exceptions.TagNotFoundException;
import course.courseWork.model.Tag;
import course.courseWork.model.Users;
import course.courseWork.repository.TagRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TagServiceImplTest {

    private final TagRepository tagRepository = Mockito.mock(TagRepository.class);

    private final TagService tagService = new TagServiceImpl(tagRepository);

    private Users users;

    @Before("")
    public void setup() {
        users = new Users();
    }

    @DisplayName("create tag by user")
    @Test
    void createTagByUser() {
        Tag tag = new Tag();
        tag.setUsers(users);

        Mockito.when(tagRepository.save(tag))
                .thenReturn(tag);

        Tag result = tagService.createTagByUser(tag, users);

        assertEquals(tag, result);
        Mockito.verify(tagRepository, Mockito.times(1)).save(tag);
    }

    @DisplayName("get all tags by user")
    @Test
    void getAllTagsByUser() {
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag());
        tags.add(new Tag());

        Mockito.when(tagRepository.findAllByUsers(users))
                .thenReturn(tags);

        List<Tag> result = tagService.getAllTagsByUser(users);

        assertEquals(tags, result);
        Mockito.verify(tagRepository, Mockito.times(1)).findAllByUsers(users);
    }

    @DisplayName("delete tag by id and user - not found")
    @Test
    void deleteTagByIdAndUser_TagNotFoundException() {
        Long id = 1L;

        Mockito.when(tagRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(TagNotFoundException.class, () -> tagService.deleteTagByIdAndUser(id, users));
    }

}