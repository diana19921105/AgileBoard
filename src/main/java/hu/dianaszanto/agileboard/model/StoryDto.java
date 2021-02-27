package hu.dianaszanto.agileboard.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoryDto {
  private String title;
  private String issueId;
  private Double point;
  private String description;
  private Timestamp createdAt;
  private StoryStatus status;
  private String assignee;
  private List<CommentResponseDto> comment;

  public StoryDto(String title, String issueId, Double point, String description, Timestamp createdAt,
                  StoryStatus status, String assignee) {
    this.title = title;
    this.issueId = issueId;
    this.point = point;
    this.description = description;
    this.createdAt = createdAt;
    this.status = status;
    this.assignee = assignee;
  }
}
