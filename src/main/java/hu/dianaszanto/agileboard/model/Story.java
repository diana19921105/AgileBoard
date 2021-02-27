package hu.dianaszanto.agileboard.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Story {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String issueId;

    @Column
    private Double point;

    @Type(type = "text")
    private String description;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StoryStatus status;

    @ManyToOne
    @JoinColumn(name = "assignee_id")
    private User assignee;

    @OneToMany(mappedBy = "story", cascade = CascadeType.ALL)
    private List<Comment> commentList = new ArrayList<>();

    @Override
    public String toString() {
        return title;
    }
}
