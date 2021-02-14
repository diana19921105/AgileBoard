package hu.dianaszanto.agileboard.repository;

import hu.dianaszanto.agileboard.model.Story;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoryRepository extends JpaRepository<Story, Long> {
    Optional<Story> findByIssueId(String issueId);
}
