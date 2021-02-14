package hu.dianaszanto.agileboard.service;

import hu.dianaszanto.agileboard.model.Story;
import hu.dianaszanto.agileboard.model.StoryDto;
import hu.dianaszanto.agileboard.model.StoryMinDto;
import hu.dianaszanto.agileboard.model.User;
import hu.dianaszanto.agileboard.model.UserDto;
import hu.dianaszanto.agileboard.model.UserMinDto;
import hu.dianaszanto.agileboard.repository.StoryRepository;
import hu.dianaszanto.agileboard.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class AgileBoardService {
    private StoryRepository storyRepository;
    private UserRepository userRepository;

    public AgileBoardService(StoryRepository storyRepository, UserRepository userRepository) {
        this.storyRepository = storyRepository;
        this.userRepository = userRepository;
    }

    public StoryMinDto findAllStories() {
        List<StoryDto> stories = storyRepository.findAll()
                .stream()
                .map(s -> new StoryDto(s.getTitle(), s.getIssueId(), s.getPoint(), s.getDescription(), s.getCreatedAt(),
                        s.getStatus(), s.getAssignee().getName()))
                .collect(Collectors.toList());

        return new StoryMinDto(stories);

    }

    public UserMinDto findAllUsers() {
        List<User> all = userRepository.findAll();

        List<UserDto> collection = all.stream()
                .map(u -> new UserDto(u.getName(), u.getStories().stream()
                        .map(Story::getTitle).collect(Collectors.toList())))
                .collect(Collectors.toList());

        return new UserMinDto(collection);
    }

    public Story addNewStory(StoryDto storyDto) {
        return storyRepository.save(Story.builder().title(storyDto.getTitle()).description(storyDto.getDescription())
                .issueId(storyDto.getIssueId()).point(storyDto.getPoint())
                .status(storyDto.getStatus()).createdAt(new Timestamp(System.currentTimeMillis())).build());
    }

    public User addNewUser(UserDto userDto) {
        return userRepository.save(User.builder().name(userDto.getName()).build());
    }

    public Story addAssignee(Long storyId, Long userId) {
        Story story = storyRepository.findById(storyId).orElseThrow(NoSuchElementException::new);
        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);

        story.setAssignee(user);
        user.getStories().add(story);
        storyRepository.save(story);
        return story;

    }
}
