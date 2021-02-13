package hu.dianaszanto.agileboard.repository;

import hu.dianaszanto.agileboard.model.Story;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoryRepository extends JpaRepository<Story, Long> {
}
