package hu.dianaszanto.agileboard.service;

import hu.dianaszanto.agileboard.model.Story;
import hu.dianaszanto.agileboard.model.StoryDto;
import hu.dianaszanto.agileboard.model.StoryStatus;
import hu.dianaszanto.agileboard.model.User;
import hu.dianaszanto.agileboard.model.UserDto;
import hu.dianaszanto.agileboard.repository.StoryRepository;
import hu.dianaszanto.agileboard.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    private static boolean isPerfectSquare(Double x) {
        int s = (int) Math.sqrt(x);
        return (s * s == x);
    }

    private static boolean isFibonacci(Double n) {
        return isPerfectSquare(5 * n * n + 4) || isPerfectSquare(5 * n * n - 4);
    }

    public List<StoryDto> findAllStories() {

        return storyRepository.findAll()
                .stream()
                .map(s -> new StoryDto(s.getTitle(), s.getIssueId(), s.getPoint(), s.getDescription(), s.getCreatedAt(),
                        s.getStatus(), s.getAssignee().getName()))
                .collect(Collectors.toList());
    }

    public List<UserDto> findAllUsers() {
        List<User> all = userRepository.findAll();

        return all.stream()
                .map(u -> new UserDto(u.getName(), u.getStories().stream()
                        .map(Story::getTitle).collect(Collectors.toList())))
                .collect(Collectors.toList());
    }

    public StoryDto addNewStory(StoryDto storyDto) {
        Story toSave = storyRepository.save(Story.builder().title(storyDto.getTitle()).description(storyDto.getDescription())
                .issueId(storyDto.getIssueId()).point(storyDto.getPoint())
                .status(storyDto.getStatus()).createdAt(new Timestamp(System.currentTimeMillis())).build());

        return new StoryDto(toSave.getTitle(), toSave.getIssueId(), toSave.getPoint(), toSave.getDescription(),
                toSave.getCreatedAt(), toSave.getStatus(), toSave.getAssignee().getName());
    }

    public UserDto addNewUser(UserDto userDto) {
        User toSave = userRepository.save(User.builder().name(userDto.getName()).build());

        return new UserDto(toSave.getName());
    }

    public Story addAssignee(Long storyId, Long userId) {
        Story story = storyRepository.findById(storyId).orElseThrow(NoSuchElementException::new);
        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);

        story.setAssignee(user);
        user.getStories().add(story);
        storyRepository.save(story);
        return story;
    }

    public Story updateStatus(Long id, StoryStatus status) {
        Story story = storyRepository.findById(id).orElseThrow(NoSuchElementException::new);
        story.setStatus(status);
        return storyRepository.save(story);
    }

    public Story updateStoryPoint(Long id, Double point) {
        Story story = storyRepository.findById(id).orElseThrow(NoSuchElementException::new);
        if (isFibonacci(point)) {
            story.setPoint(point);
            return storyRepository.save(story);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    public StoryDto getStoryDetails(String issueId) {
        Story story = storyRepository.findByIssueId(issueId).orElseThrow(NoSuchElementException::new);
        return new StoryDto(story.getTitle(), story.getIssueId(), story.getPoint(), story.getDescription(),
                story.getCreatedAt(), story.getStatus(), story.getAssignee().getName());
    }
}
