package com.nineleaps.authentication.jwt.service;

import com.nineleaps.authentication.jwt.dto.NoteDTO;


public interface INoteService {
	
    NoteDTO updateNote(Long noteId, NoteDTO noteDTO);
    void deleteNote(Long noteId);
  
	

}
