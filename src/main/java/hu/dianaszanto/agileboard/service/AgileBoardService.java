package hu.dianaszanto.agileboard.service;

import hu.dianaszanto.agileboard.model.Story;
import hu.dianaszanto.agileboard.model.StoryDto;
import hu.dianaszanto.agileboard.model.User;
import hu.dianaszanto.agileboard.model.UserDto;
import hu.dianaszanto.agileboard.repository.StoryRepository;
import hu.dianaszanto.agileboard.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AgileBoardService {
    private StoryRepository storyRepository;
    private UserRepository userRepository;

    public AgileBoardService(StoryRepository storyRepository, UserRepository userRepository) {
        this.storyRepository = storyRepository;
        this.userRepository = userRepository;
    }

    public List<StoryDto> findAllStories() {
        List<Story> all = storyRepository.findAll();

        return all.stream()
                .map(s -> new StoryDto(s.getTitle(), s.getIssueId(), s.getPoint(), s.getDescription(),
                        s.getCreatedAt(), s.getStatus(), s.getAssignee()))
                .collect(Collectors.toList());
    }

    public List<UserDto> findAllUsers() {
        List<User> all = userRepository.findAll();

        return all.stream()
                .map(u -> new UserDto(u.getName(), u.getStories()))
                .collect(Collectors.toList());
    }
}
