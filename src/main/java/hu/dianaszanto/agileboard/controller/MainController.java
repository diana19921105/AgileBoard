package hu.dianaszanto.agileboard.controller;

import hu.dianaszanto.agileboard.model.Story;
import hu.dianaszanto.agileboard.model.StoryDto;
import hu.dianaszanto.agileboard.model.StoryMinDto;
import hu.dianaszanto.agileboard.model.StoryRequest;
import hu.dianaszanto.agileboard.model.User;
import hu.dianaszanto.agileboard.model.UserDto;
import hu.dianaszanto.agileboard.model.UserMinDto;
import hu.dianaszanto.agileboard.model.UserRequest;
import hu.dianaszanto.agileboard.service.AgileBoardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@RestController
public class MainController {
    private AgileBoardService agileBoardService;

    public MainController(AgileBoardService agileBoardService) {
        this.agileBoardService = agileBoardService;
    }

    @GetMapping("/stories")
    public ResponseEntity<StoryMinDto> listAllStories() {
        StoryMinDto allStories = agileBoardService.findAllStories();
        return ResponseEntity.ok(allStories);
    }

    @GetMapping("/users")
    public ResponseEntity<UserMinDto> listAllUsers() {
        UserMinDto allUsers = agileBoardService.findAllUsers();
        return ResponseEntity.ok(allUsers);
    }

    @PostMapping("/stories")
    public ResponseEntity<Story> addNewStory(@RequestBody StoryDto storyDto) {
        return new ResponseEntity<>(agileBoardService.addNewStory(storyDto), HttpStatus.CREATED);
    }

    @PostMapping("/users")
    public ResponseEntity<User> addNewUser(@RequestBody UserDto userDto) {
        return new ResponseEntity<>(agileBoardService.addNewUser(userDto), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/story/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<StoryDto> addAssignee(@PathVariable Long id, @RequestBody UserRequest userRequest) {
        if (id != null) {
            try {
                Story story = agileBoardService.addAssignee(id, userRequest.getUserId());
                StoryDto storyDto = new StoryDto(story.getTitle(), story.getIssueId(), story.getPoint(),
                        story.getDescription(), story.getCreatedAt(), story.getStatus(), story.getAssignee().getName());
                return ResponseEntity.ok(storyDto);
            } catch (NoSuchElementException e) {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping(value = "update/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<StoryDto> updateStatus(@PathVariable Long id, @RequestBody StoryRequest request) {
        if (id != null) {
            try {
                Story story = agileBoardService.updateStatus(id, request.getStatus());
                StoryDto storyDto = new StoryDto(story.getTitle(), story.getIssueId(), story.getPoint(),
                        story.getDescription(), story.getCreatedAt(), story.getStatus(), story.getAssignee().getName());
                return ResponseEntity.ok(storyDto);
            } catch (NoSuchElementException e) {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
