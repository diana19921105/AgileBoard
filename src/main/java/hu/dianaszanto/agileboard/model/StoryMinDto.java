package hu.dianaszanto.agileboard.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StoryMinDto {
    private String title;
    private String issueId;
    private Double point;
    private StoryStatus status;
    private String assignee;
}
