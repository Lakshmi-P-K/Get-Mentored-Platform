package com.nineleaps.authentication.jwt.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.nineleaps.authentication.jwt.dto.NoteDTO;
import com.nineleaps.authentication.jwt.entity.Note;
import com.nineleaps.authentication.jwt.enums.NoteVisibility;
import com.nineleaps.authentication.jwt.service.NoteService;

import io.swagger.annotations.ApiOperation;

import java.util.List;
import java.util.stream.Collectors;

	@RestController
	@RequestMapping("/api/v1/notes")
	public class NoteController {
	    
		 private NoteService noteService;

		    public NoteController(NoteService noteService) {
		        this.noteService = noteService;
		    }
		    
		    @PostMapping("/createNotesForMenteeAndMentor")
		    @ApiOperation("Write Notes For Both Mentor and Mentee ")
		    public ResponseEntity<NoteDTO> createNote(@RequestBody NoteDTO privateNoteDTO) {
		        NoteDTO noteResponseDTO = noteService.createNote(privateNoteDTO);
		        return ResponseEntity.ok(noteResponseDTO);
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
		    
	   
		    @PutMapping("/updatingNotes")
		    @ApiOperation("Updating the  Notes For Both Mentor and Mentee by Note Id")
		    public ResponseEntity<NoteDTO> updateNote(
		            @RequestParam Long noteId,
		            @RequestBody NoteDTO noteDTO
		    ) {
		        NoteDTO updatedNote = noteService.updateNote(noteId, noteDTO);
		        return ResponseEntity.ok(updatedNote);
		    }

		    @DeleteMapping("/delete")
		    @ApiOperation("Deleting the  Notes by Note Id")
		    public ResponseEntity<Void> deleteNote(@RequestParam Long noteId) {
		        noteService.deleteNote(noteId);
		        return ResponseEntity.noContent().build();
		    }
		    
		    @GetMapping("/gettingPublicNotes")
		    @ApiOperation("Get Public notes of both Mentor and Mentee by Engagement Id")
		    public List<NoteDTO> getPublicNotesByEngagementId(@RequestParam Long engagementId) {
		        NoteVisibility visibility = NoteVisibility.PUBLIC;
		        List<Note> notes = noteService.getNotesByEngagementIdAndVisibility(engagementId, visibility);
		        return notes.stream().map(this::mapNoteToDTO).collect(Collectors.toList());
		    }
		    
		    @GetMapping("/gettingPrivateNotes")
		    @ApiOperation("Get Private notes of both Mentor and Mentee by User Id and Engagement Id")

		    public List<NoteDTO> getPrivateNotesByUserIdAndEngagementId(
		            @RequestParam Long userId,
		            @RequestParam Long engagementId
		    ) {
		        NoteVisibility visibility = NoteVisibility.PRIVATE;
		        List<Note> notes = noteService.getNotesByUserIdAndEngagementIdAndVisibility(userId, engagementId, visibility);
		        return notes.stream().map(this::mapNoteToDTO).collect(Collectors.toList());
		    }
		    

		    } 

