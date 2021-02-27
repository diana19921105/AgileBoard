package hu.dianaszanto.agileboard.service;

import hu.dianaszanto.agileboard.model.Comment;
import hu.dianaszanto.agileboard.model.CommentRequestDto;
import hu.dianaszanto.agileboard.model.CommentResponseDto;
import hu.dianaszanto.agileboard.model.Story;
import hu.dianaszanto.agileboard.model.StoryDto;
import hu.dianaszanto.agileboard.model.StoryStatus;
import hu.dianaszanto.agileboard.model.User;
import hu.dianaszanto.agileboard.model.UserDto;
import hu.dianaszanto.agileboard.repository.StoryRepository;
import hu.dianaszanto.agileboard.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
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

    ModelMapper modelMapper = new ModelMapper();

    return storyRepository.findAll()
        .stream()
        .map(s -> modelMapper.map(s, StoryDto.class))
        .collect(Collectors.toList());
  }

  public List<UserDto> findAllUsers() {
    ModelMapper modelMapper = new ModelMapper();

    return userRepository.findAll().stream()
        .map(u -> modelMapper.map(u, UserDto.class))
        .collect(Collectors.toList());
  }

  public StoryDto addNewStory(StoryDto storyDto) {
    Story toSave =
        storyRepository.save(Story.builder().title(storyDto.getTitle()).description(storyDto.getDescription())
            .issueId(storyDto.getIssueId()).point(storyDto.getPoint())
            .status(storyDto.getStatus()).createdAt(new Timestamp(System.currentTimeMillis())).build());

    return StoryDto.builder().title(toSave.getTitle()).issueId(toSave.getIssueId()).point(toSave.getPoint())
        .description(toSave.getDescription()).createdAt(toSave.getCreatedAt()).status(toSave.getStatus())
        .assignee(toSave.getAssignee().getName()).build();
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
    return storyRepository.save(story);
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
    ModelMapper modelMapper = new ModelMapper();

    List<CommentResponseDto> commentList = story.getCommentList().stream()
        .map(c -> modelMapper.map(c, CommentResponseDto.class))
        .collect(Collectors.toList());

    return StoryDto.builder().title(story.getTitle()).issueId(story.getIssueId()).point(story.getPoint())
        .description(story.getDescription()).createdAt(story.getCreatedAt()).status(story.getStatus())
        .assignee(story.getAssignee().getName()).comment(commentList).build();
  }

  public Map<StoryStatus, List<StoryDto>> getBoard() {
    List<Story> stories = storyRepository.findAll();

    ModelMapper modelMapper = new ModelMapper();

    return stories.stream()
        .map(a -> modelMapper.map(a, StoryDto.class))
        .collect(Collectors.groupingBy(StoryDto::getStatus));
  }

  public Story addComment(String issueId, CommentRequestDto commentRequestDto) {
    Story story = storyRepository.findByIssueId(issueId).orElseThrow(NoSuchElementException::new);
    User userById = userRepository.findById(commentRequestDto.getUserId()).orElseThrow(NoSuchElementException::new);

    story.getCommentList()
        .add(new Comment(commentRequestDto.getMessage(), userById, new Timestamp(System.currentTimeMillis()), story));
    return storyRepository.save(story);
  }
}
