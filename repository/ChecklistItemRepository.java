package com.nineleaps.authentication.jwt.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nineleaps.authentication.jwt.entity.ChecklistItem;

@Repository
public interface ChecklistItemRepository extends JpaRepository<ChecklistItem, Long> {


	List<ChecklistItem> findAllByGoalTracker_Id(Long goalTrackerId);
	List<ChecklistItem> findAllByGoalTrackerId(Long goalTrackerId);
	Optional<ChecklistItem> findById(Long id);
}
