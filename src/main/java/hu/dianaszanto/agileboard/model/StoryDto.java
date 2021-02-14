package hu.dianaszanto.agileboard.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class StoryDto {
    private String title;
    private String issueId;
    private Double point;
    private String description;
    private Timestamp createdAt;
    private StoryStatus status;
    private User assignee;
}
