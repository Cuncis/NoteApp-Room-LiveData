package com.cuncis.noteapproomlivedata.repository

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.cuncis.noteapproomlivedata.dao.NoteDao
import com.cuncis.noteapproomlivedata.db.NoteDatabase
import com.cuncis.noteapproomlivedata.model.Note

class NoteRepository(application: Application) {
    private var noteDao: NoteDao
    private var allNotes: LiveData<List<Note>>

    init {
        val noteDatabase = NoteDatabase.getInstance(application.applicationContext)!!
        noteDao = noteDatabase.noteDao()
        allNotes = noteDao.getAllNotes()
    }

    fun insert(note: Note) {
        InsertNoteAsyncTask(noteDao).execute(note)
    }

    fun update(note: Note) {
        UpdateNoteAsyncTask(noteDao).execute(note)
    }

    fun delete(note: Note) {
        DeleteNoteAsyncTask(noteDao).execute(note)
    }

    fun deleteAllNotes() {
        DeleteAllNotes(noteDao).execute()
    }

    fun getAllNotes(): LiveData<List<Note>> {
        return allNotes
    }

    private class InsertNoteAsyncTask(val noteDao: NoteDao): AsyncTask<Note, Unit, Unit>() {
        override fun doInBackground(vararg p0: Note?) {
            noteDao.insert(p0[0]!!)
        }
    }

    private class UpdateNoteAsyncTask(val noteDao: NoteDao): AsyncTask<Note, Unit, Unit>() {
        override fun doInBackground(vararg p0: Note?) {
            noteDao.update(p0[0]!!)
        }
    }

    private class DeleteNoteAsyncTask(val noteDao: NoteDao): AsyncTask<Note, Unit, Unit>() {
        override fun doInBackground(vararg p0: Note?) {
            noteDao.delete(p0[0]!!)
        }
    }

    private class DeleteAllNotes(val noteDao: NoteDao): AsyncTask<Unit, Unit, Unit>() {
        override fun doInBackground(vararg p0: Unit?) {
            noteDao.deleteAllNotes()
        }
    }

}