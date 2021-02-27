package hu.dianaszanto.agileboard.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Type(type = "text")
  private String message;

  @OneToOne
  private User user;

  @Column(name = "created_at")
  private Timestamp createdAt;

  @ManyToOne
  @JoinColumn(name = "story_id")
  private Story story;

  public Comment(String message, User user, Timestamp createdAt, Story story) {
    this.message = message;
    this.user = user;
    this.createdAt = createdAt;
    this.story = story;
  }
}
