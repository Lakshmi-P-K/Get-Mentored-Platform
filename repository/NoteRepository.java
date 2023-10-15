package com.nineleaps.authentication.jwt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.nineleaps.authentication.jwt.entity.Note;
import com.nineleaps.authentication.jwt.enums.NoteVisibility;
@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
	 List<Note> findByVisibility(NoteVisibility visibility);
	 List<Note> findByEngagement_Id(Long engagementId);
	List<Note> findByUser_IdAndEngagement_IdAndVisibility(Long userId, Long engagementId, NoteVisibility visibility);
	List<Note> findByEngagement_IdAndVisibility(Long engagementId, NoteVisibility visibility);
	
	
}
