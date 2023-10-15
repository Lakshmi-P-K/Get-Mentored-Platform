package com.nineleaps.authentication.jwt.service;

import java.time.LocalDateTime;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nineleaps.authentication.jwt.dto.NoteDTO;

import com.nineleaps.authentication.jwt.entity.Engagement;
import com.nineleaps.authentication.jwt.entity.Note;
import com.nineleaps.authentication.jwt.entity.User;
import com.nineleaps.authentication.jwt.enums.NoteVisibility;
import com.nineleaps.authentication.jwt.repository.EngagementRepository;
import com.nineleaps.authentication.jwt.repository.NoteRepository;
import com.nineleaps.authentication.jwt.repository.UserRepository;
@Service
public class NoteService implements INoteService {
	@Autowired
	private NoteRepository noteRepository;
	@Autowired 
	private EngagementRepository engagementRepository;
	@Autowired
	private UserRepository userRepository;
	
	

	
	public NoteDTO createNote(NoteDTO privateNoteDTO) {
        Note note = new Note();
        note.setTitle(privateNoteDTO.getTitle());
        note.setContent(privateNoteDTO.getContent());
        note.setVisibility(privateNoteDTO.getVisibility());
        note.setCreatedTime(LocalDateTime.now()); // Set the current timestamp as the creation time

        Long userId = privateNoteDTO.getUserId();
        User user = userRepository.findById(userId).orElseThrow();

        note.setUser(user);

        if (privateNoteDTO.getEngagementId() != null) {
            // Set the engagement ID if provided
            Engagement engagement = engagementRepository.findById(privateNoteDTO.getEngagementId()).orElseThrow();
            note.setEngagement(engagement);
        }

        Note savedNote = noteRepository.save(note);
        NoteDTO noteResponseDTO = mapNoteToDTO(savedNote);

        return noteResponseDTO;
    }

    @Override
    public NoteDTO updateNote(Long noteId, NoteDTO noteDTO) {
        Note note = noteRepository.findById(noteId).orElseThrow();

        note.setContent(noteDTO.getContent());
        note.setVisibility(noteDTO.getVisibility());
        note.setTitle(noteDTO.getTitle());
        note.setUpdatedTime(LocalDateTime.now()); // Set the current timestamp as the update time

        // Set the User only if the userId is not null
        if (noteDTO.getUserId() != null) {
            User user = userRepository.findById(noteDTO.getUserId()).orElseThrow();
            note.setUser(user);
        }

        Note updatedNote = noteRepository.save(note);
        NoteDTO updatedNoteDTO = mapNoteToDTO(updatedNote);

        return updatedNoteDTO;
    }

    // ...







		@Override
		public void deleteNote(Long noteId) {
			
			 Note note = noteRepository.findById(noteId)
		                .orElseThrow();
	
		        noteRepository.delete(note);
			
		}


		
		private NoteDTO mapNoteToDTO(Note note) {
		    NoteDTO noteDTO = new NoteDTO();
		    noteDTO.setId(note.getId());
		    noteDTO.setTitle(note.getTitle());
		    noteDTO.setContent(note.getContent());
		    noteDTO.setVisibility(note.getVisibility());

		    noteDTO.setUserId(note.getUser() != null ? note.getUser().getId() : null);
		    noteDTO.setEngagementId(note.getEngagement() != null ? note.getEngagement().getId() : null);

		    noteDTO.setCreatedTime(note.getCreatedTime());
		    noteDTO.setUpdatedTime(note.getUpdatedTime());

		    return noteDTO;
		}

		 public List<Note> getNotesByUserIdAndEngagementIdAndVisibility(
		            Long userId, Long engagementId, NoteVisibility visibility) {
		        return noteRepository.findByUser_IdAndEngagement_IdAndVisibility(userId, engagementId, visibility);
		    }
	
		 public List<Note> getNotesByEngagementIdAndVisibility(Long engagementId, NoteVisibility visibility) {
		        return noteRepository.findByEngagement_IdAndVisibility(engagementId, visibility);
		    }
	
}





	



	
	
		
	


