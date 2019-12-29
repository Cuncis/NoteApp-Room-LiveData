package com.cuncis.noteapproomlivedata

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.cuncis.noteapproomlivedata.util.Constants.EXTRA_DESCRIPTION
import com.cuncis.noteapproomlivedata.util.Constants.EXTRA_ID
import com.cuncis.noteapproomlivedata.util.Constants.EXTRA_PRIORITY
import com.cuncis.noteapproomlivedata.util.Constants.EXTRA_TITLE
import com.cuncis.noteapproomlivedata.util.Utils.Companion.showToast
import kotlinx.android.synthetic.main.activity_add_edit_note.*

class AddEditNoteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_note)


        np_priority.minValue = 1
        np_priority.maxValue = 10


        if (intent.hasExtra(EXTRA_ID)) {
            et_title.setText(intent.getStringExtra(EXTRA_TITLE))
            et_description.setText(intent.getStringExtra(EXTRA_DESCRIPTION))
            np_priority.value = intent.getIntExtra(EXTRA_PRIORITY, 0)
            title = "Edit Note"
        } else {
            title = "Add Note"
        }
    }

    private fun saveNote() {
        val title = et_title.text.toString().trim()
        val description = et_description.text.toString().trim()
        val priority = np_priority.value

        if (title.isEmpty() || description.isEmpty()) {
            showToast(this, "Please Insert a Title and Description")
        }

        val data = Intent()
        data.putExtra(EXTRA_TITLE, title)
        data.putExtra(EXTRA_DESCRIPTION, description)
        data.putExtra(EXTRA_PRIORITY, priority)

        val id = intent.getIntExtra(EXTRA_ID, -1)
        if (id != -1) {
            data.putExtra(EXTRA_ID, id)
        }

        setResult(Activity.RESULT_OK, data)
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_note_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.save_menu -> {
                saveNote()
                true
            } else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}
