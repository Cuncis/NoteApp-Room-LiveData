package com.cuncis.noteapproomlivedata

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cuncis.noteapproomlivedata.adapter.NoteAdapter
import com.cuncis.noteapproomlivedata.model.Note
import com.cuncis.noteapproomlivedata.util.Constants.ADD_NOTE_REQUEST
import com.cuncis.noteapproomlivedata.util.Constants.EDIT_NOTE_REQUEST
import com.cuncis.noteapproomlivedata.util.Constants.EXTRA_DESCRIPTION
import com.cuncis.noteapproomlivedata.util.Constants.EXTRA_ID
import com.cuncis.noteapproomlivedata.util.Constants.EXTRA_PRIORITY
import com.cuncis.noteapproomlivedata.util.Constants.EXTRA_TITLE
import com.cuncis.noteapproomlivedata.util.Utils.Companion.logd
import com.cuncis.noteapproomlivedata.util.Utils.Companion.showToast
import com.cuncis.noteapproomlivedata.viewmodel.NoteViewModel

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlin.math.E

class MainActivity : AppCompatActivity(), NoteAdapter.OnItemClickListener {

    lateinit var noteViewModel: NoteViewModel
    lateinit var noteAdapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        noteAdapter = NoteAdapter(this)
        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java)

        rv_notes.layoutManager = LinearLayoutManager(this)
        rv_notes.setHasFixedSize(true)
        noteAdapter.setClickListener(this)
        rv_notes.adapter = noteAdapter

        noteViewModel.getAllNotes().observe(this, Observer {
            logd("" + it)
            noteAdapter.setNoteList(it)
        })

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                noteViewModel.delete(noteAdapter.getNoteAt(viewHolder.adapterPosition))
                showToast(this@MainActivity, "Note Deleted")
            }

        }).attachToRecyclerView(rv_notes)

        fab.setOnClickListener {
            val intent = Intent(this, AddEditNoteActivity::class.java)
            startActivityForResult(intent, ADD_NOTE_REQUEST)
        }
    }

    override fun onItemClick(note: Note) {
        val intent = Intent(this, AddEditNoteActivity::class.java)
        intent.putExtra(EXTRA_ID, note.id)
        intent.putExtra(EXTRA_TITLE, note.title)
        intent.putExtra(EXTRA_DESCRIPTION, note.description)
        intent.putExtra(EXTRA_PRIORITY, note.priority)
        startActivityForResult(intent, EDIT_NOTE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_NOTE_REQUEST && resultCode == Activity.RESULT_OK) {
            val title = data?.getStringExtra(EXTRA_TITLE)
            val description = data?.getStringExtra(EXTRA_DESCRIPTION)
            val priority = data?.getIntExtra(EXTRA_PRIORITY, 1)

            val note = Note(title!!, description!!, priority!!)
            noteViewModel.insert(note)

            showToast(this, "Note Saved")
        } else if (requestCode == EDIT_NOTE_REQUEST && resultCode == Activity.RESULT_OK) {
            val id = data?.getIntExtra(EXTRA_ID, -1)

            if (id == -1) {
                showToast(this, "Note Can't be Updated")
            }

            val title = data?.getStringExtra(EXTRA_TITLE)
            val description = data?.getStringExtra(EXTRA_DESCRIPTION)
            val priority = data?.getIntExtra(EXTRA_PRIORITY, 1)

            val note = Note(title!!, description!!, priority!!)
            note.id = id!!
            noteViewModel.update(note)

            showToast(this, "Note Updated")
        } else {
            showToast(this, "Note not Saved")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_delete_all -> {
                noteViewModel.deleteAllNotes()
                showToast(this, "All Notes Deleted")
                true
            } else -> super.onOptionsItemSelected(item)
        }
    }

}
