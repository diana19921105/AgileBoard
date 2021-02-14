package hu.dianaszanto.agileboard.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class StoryMinDto {
    private List<StoryDto> stories;
}
