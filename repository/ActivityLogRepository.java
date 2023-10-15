package com.nineleaps.authentication.jwt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.nineleaps.authentication.jwt.entity.ActivityLog;
import com.nineleaps.authentication.jwt.entity.ChecklistItem;

@Repository
public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long> {
	
    List<ActivityLog> findByChecklistItem(ChecklistItem checklistItem);


}
