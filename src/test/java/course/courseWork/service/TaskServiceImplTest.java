package course.courseWork.service;

import course.courseWork.exceptions.TagNotFoundException;
import course.courseWork.exceptions.TaskNotFoundException;
import course.courseWork.exceptions.TaskNotFoundExceptionByDeadline;
import course.courseWork.model.Tag;
import course.courseWork.model.Task;
import course.courseWork.model.Users;
import course.courseWork.repository.TagRepository;
import course.courseWork.repository.TaskRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class TaskServiceImplTest {

    private final TaskRepository taskRepository = Mockito.mock(TaskRepository.class);

    private final TagRepository tagRepository = Mockito.mock(TagRepository.class);

    private final TaskService taskService = new TaskServiceImpl(taskRepository, tagRepository);


    @DisplayName("add task")
    @Test
    void addTask() {
        Task task = new Task();
        Task expectedTask = new Task();
        Users user = new Users();
        expectedTask.setUsers(user);
        Mockito.when(
                        taskRepository.save(task))
                .thenReturn(expectedTask);
        assertEquals(expectedTask, taskService.addTask(task, user));
    }

    @DisplayName("get all tasks")
    @Test
    void getAllTasksByUser() {
        Users user = new Users();
        Task task1 = new Task();
        task1.setUsers(user);
        Task task2 = new Task();
        task2.setUsers(user);
        List<Task> expectedTasks = List.of(task1, task2);
        Mockito.when(taskRepository
                        .findAllByUsers(user))
                .thenReturn(expectedTasks);
        assertEquals(expectedTasks, taskService.getAllTasksByUser(user));
    }

    @DisplayName("get tasks by deadline")
    @Test
    void getTasksByDeadlineAndUser() throws TaskNotFoundExceptionByDeadline {
        Users user = new Users();
        Date date = new Date();
        Task task = new Task();
        task.setDeadline(date);
        task.setUsers(user);
        List<Task> expectedTask = List.of(task);
        Mockito.when(taskRepository.findAllByDeadlineAndUsers(date, user))
                .thenReturn(expectedTask);
        assertEquals(expectedTask, taskService.getTasksByDeadlineAndUser(date, user));
    }

    @DisplayName("get tasks by importance")
    @Test
    void getTasksByImportanceAndUser() {
        Task task = new Task();
        Users user = new Users();
        task.setUsers(user);
        task.setImportance(1);
        List<Task> expectedTask = List.of(task);
        Mockito.when(taskRepository.findAllByImportanceAndUsers(1, user))
                .thenReturn(expectedTask);
        assertEquals(expectedTask, taskService.getTasksByImportanceAndUser(1, user));
    }

    @DisplayName("get tasks by deadline and sort by importance - not found")
    @Test
    void getTasksByDeadlineAndUserAndSortByImportance_NotFound() {
        Users user = new Users();
        Date date = new Date();
        Mockito.when(taskRepository.findAllByDeadlineAndUsers(date, user))
                .thenReturn(List.of());
        assertThrows(TaskNotFoundExceptionByDeadline.class,
                () -> taskService.getTasksByDeadlineAndUserAndSortByImportance(date, user, true));
    }

    @DisplayName("add tags to task")
    @Test
    void addTagsToTask() throws TaskNotFoundException {
        Users users = new Users();
        Long taskId = 1L;
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag());
        Task task = new Task();
        task.setUsers(users);

        Mockito.when(taskRepository.findByTaskIdAndUsers(taskId, users))
                .thenReturn(Optional.of(task));
        Mockito.when(taskRepository.save(task))
                .thenReturn(task);

        Task result = taskService.addTagsToTask(taskId, tagList, users);

        assertEquals(task, result);
        Mockito.verify(taskRepository, Mockito.times(1)).save(task);
    }

    @DisplayName("add tags to task - task not found")
    @Test
    void addTagsToTask_TaskNotFoundException() {
        Users users = new Users();
        Long taskId = 1L;
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag());

        Mockito.when(taskRepository.findByTaskIdAndUsers(taskId, users))
                .thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.addTagsToTask(taskId, tagList, users));
    }

//    @DisplayName("remove tag from task")
//    @Test
//    void removeTagFromTask() throws TaskNotFoundException, TagNotFoundException {
//        Users users = new Users();
//        Long taskId = 1L;
//        Tag tag = new Tag();
//        Task task = new Task();
//        task.getTags().add(tag);
//        task.setUsers(users);
//        taskRepository.save(task);
//
//        Mockito.when(taskRepository.findByTaskIdAndUsers(taskId, users))
//                .thenReturn(Optional.of(task));
//        Mockito.when(taskRepository.save(task))
//                .thenReturn(task);
//
//        Task result = taskService.removeTagFromTask(taskId, tag, users);
//
//        assertEquals(task, result);
//        Mockito.verify(taskRepository, Mockito.times(1)).save(task);
//    }

    @DisplayName("remove tag from task - task not found")
    @Test
    void removeTagFromTask_TaskNotFoundException() {
        Users users = new Users();
        Long taskId = 1L;
        Tag tag = new Tag();

        Mockito.when(taskRepository.findByTaskIdAndUsers(taskId, users))
                .thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.removeTagFromTask(taskId, tag, users));
    }

    @DisplayName("get all tasks by tags and user")
    @Test
    void getAllTasksByTagsAndUser() {
        Users users = new Users();
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag());
        Task task = new Task();
        task.setUsers(users);
        List<Task> tasks = new ArrayList<>();
        tasks.add(task);

        Mockito.when(taskRepository.findAllByTagsAndUsers(tagList, users))
                .thenReturn(tasks);

        List<Task> result = taskService.getAllTasksByTagsAndUser(tagList, users);

        assertEquals(tasks, result);
        Mockito.verify(taskRepository, Mockito.times(1)).findAllByTagsAndUsers(tagList, users);
    }
}