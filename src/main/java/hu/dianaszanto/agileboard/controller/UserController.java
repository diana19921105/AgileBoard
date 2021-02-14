package hu.dianaszanto.agileboard.controller;

import hu.dianaszanto.agileboard.model.User;
import hu.dianaszanto.agileboard.model.UserDto;
import hu.dianaszanto.agileboard.model.UserMinDto;
import hu.dianaszanto.agileboard.service.AgileBoardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class UserController {
    private AgileBoardService agileBoardService;

    public UserController(AgileBoardService agileBoardService) {
        this.agileBoardService = agileBoardService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> listAllUsers() {
        List<UserDto> allUsers = agileBoardService.findAllUsers();
        return ResponseEntity.ok(allUsers);
    }

    @PostMapping("/users")
    public ResponseEntity<UserDto> addNewUser(@RequestBody UserDto userDto) {
        return new ResponseEntity<>(agileBoardService.addNewUser(userDto), HttpStatus.CREATED);
    }
}
