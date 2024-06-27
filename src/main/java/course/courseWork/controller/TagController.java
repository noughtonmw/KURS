package course.courseWork.controller;

import course.courseWork.exceptions.TagNotFoundException;
import course.courseWork.model.Tag;
import course.courseWork.model.Users;
import course.courseWork.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tag")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @PostMapping
    public Tag createTagByUser(@RequestBody Tag tag, @AuthenticationPrincipal Users user) {
        return tagService.createTagByUser(tag, user);
    }

    @GetMapping
    public List<Tag> getAllTagsByUser(@AuthenticationPrincipal Users user) {
        return tagService.getAllTagsByUser(user);
    }

    @DeleteMapping("/{id}")
    public void deleteTagByIdAndUser(@PathVariable Long id, @AuthenticationPrincipal Users user) throws TagNotFoundException {
        tagService.deleteTagByIdAndUser(id, user);
    }

}
