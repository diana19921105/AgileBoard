package hu.dianaszanto.agileboard.controller;

import hu.dianaszanto.agileboard.model.StoryDto;
import hu.dianaszanto.agileboard.model.UserDto;
import hu.dianaszanto.agileboard.service.AgileBoardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MainController {
    private AgileBoardService agileBoardService;

    public MainController(AgileBoardService agileBoardService) {
        this.agileBoardService = agileBoardService;
    }

    @GetMapping("/stories")
    public ResponseEntity<List<StoryDto>> listAllStories() {
        List<StoryDto> allStories = agileBoardService.findAllStories();
        return ResponseEntity.ok(allStories);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> listAllUsers() {
        List<UserDto> allUsers = agileBoardService.findAllUsers();
        return ResponseEntity.ok(allUsers);
    }
}
