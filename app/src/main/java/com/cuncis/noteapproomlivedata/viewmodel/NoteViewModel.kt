package com.cuncis.noteapproomlivedata.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.cuncis.noteapproomlivedata.model.Note
import com.cuncis.noteapproomlivedata.repository.NoteRepository

class NoteViewModel(application: Application): AndroidViewModel(application) {
    private var noteRepository: NoteRepository = NoteRepository(application)
    private var allNotes: LiveData<List<Note>>

    init {
        allNotes = noteRepository.getAllNotes()
    }

    fun insert(note: Note) {
        noteRepository.insert(note)
    }

    fun update(note: Note) {
        noteRepository.update(note)
    }

    fun delete(note: Note) {
        noteRepository.delete(note)
    }

    fun deleteAllNotes() {
        noteRepository.deleteAllNotes()
    }

    fun getAllNotes(): LiveData<List<Note>> {
        return allNotes
    }
}