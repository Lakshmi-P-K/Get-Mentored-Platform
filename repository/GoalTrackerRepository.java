package com.nineleaps.authentication.jwt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nineleaps.authentication.jwt.entity.GoalTracker;

@Repository
public interface GoalTrackerRepository extends JpaRepository<GoalTracker, Long> {

	boolean existsByDescriptionAndEngagement_Id(String description, Long engagementId);
	GoalTracker findByEngagementId(Long engagementId);
	List<GoalTracker> findAllByEngagementId(Long engagementId);

	//	Counting no of goals for an engagement
	@Query("SELECT COUNT(g) FROM GoalTracker g WHERE g.engagement.id = :engagementId")
	int countGoalsByEngagement(@Param("engagementId") Long engagementId);

}
