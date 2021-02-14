package hu.dianaszanto.agileboard.controller;

import hu.dianaszanto.agileboard.model.Story;
import hu.dianaszanto.agileboard.model.StoryDto;
import hu.dianaszanto.agileboard.model.StoryMinDto;
import hu.dianaszanto.agileboard.model.StoryPointRequest;
import hu.dianaszanto.agileboard.model.StoryRequest;
import hu.dianaszanto.agileboard.model.StoryStatus;
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

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
// TODO rename to StoryController
public class MainController {
    private AgileBoardService agileBoardService;

    public MainController(AgileBoardService agileBoardService) {
        this.agileBoardService = agileBoardService;
    }

    @GetMapping("/stories")
    // TODO return with List<StoryDto>
    public ResponseEntity<StoryMinDto> listAllStories() {
        StoryMinDto allStories = agileBoardService.findAllStories();
        return ResponseEntity.ok(allStories);
    }

    // TODO move to UserController
    @GetMapping("/users")
    // TODO return with List<UserDto>
    public ResponseEntity<UserMinDto> listAllUsers() {
        UserMinDto allUsers = agileBoardService.findAllUsers();
        return ResponseEntity.ok(allUsers);
    }

    @PostMapping("/users")
    // TODO return with UserDto
    public ResponseEntity<User> addNewUser(@RequestBody UserDto userDto) {
        return new ResponseEntity<>(agileBoardService.addNewUser(userDto), HttpStatus.CREATED);
    }

    @PostMapping("/stories")
    // TODO return with StoryDto
    public ResponseEntity<Story> addNewStory(@RequestBody StoryDto storyDto) {
        return new ResponseEntity<>(agileBoardService.addNewStory(storyDto), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/stories/{id}/assignee", method = RequestMethod.PATCH)
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

    @RequestMapping(value = "/stories/{id}/status", method = RequestMethod.PATCH)
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

    @RequestMapping(value = "/stories/{id}/point", method = RequestMethod.PATCH)
    public ResponseEntity<StoryDto> updateStoryPoint(@PathVariable Long id, @RequestBody StoryPointRequest request) {
        if (id != null) {
            try {
                Story story = agileBoardService.updateStoryPoint(id, request.getPoint());
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

    @GetMapping("/stories/{issueId}")
    public ResponseEntity<StoryDto> getStoryDetails(@PathVariable String issueId) {
        if (issueId != null) {
            try {
                StoryDto storyDetails = agileBoardService.getStoryDetails(issueId);
                return ResponseEntity.ok(storyDetails);
            } catch (NoSuchElementException e) {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

//    @GetMapping("/board")
//    public ResponseEntity<Map<StoryStatus, List<StoryDto>>> getBoard() {
//        return ResponseEntity.ok(
//                Map.of(
//                        StoryStatus.T ODO, List.of(new StoryDto(null, null, null, null, null, null, null)),
//                        StoryStatus.IN_PROGRESS, List.of(new StoryDto(null, null, null, null, null, null, null))
//                )
//        );
//    }
}
