package com.cuncis.noteapproomlivedata.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cuncis.noteapproomlivedata.R
import com.cuncis.noteapproomlivedata.model.Note
import kotlinx.android.synthetic.main.note_item.view.*

class NoteAdapter(private var context: Context): RecyclerView.Adapter<NoteAdapter.NoteHolder>() {

    private var noteList: List<Note> = ArrayList()
    private var clickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.note_item, parent, false)
        return NoteHolder(view)
    }

    override fun getItemCount(): Int = noteList.size

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        val note: Note = noteList[position]
        holder.title.text = note.title
        holder.description.text = note.description
        holder.priority.text = note.priority.toString()
    }

    fun setNoteList(noteList: List<Note>) {
        this.noteList = noteList
        notifyDataSetChanged()
    }

    fun getNoteAt(position: Int): Note {
        return noteList[position]
    }

    fun setClickListener(clickListener: OnItemClickListener) {
        this.clickListener = clickListener
    }

    inner class NoteHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var title: TextView = itemView.text_view_title
        var description: TextView = itemView.text_view_description
        var priority: TextView = itemView.text_view_priority

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            clickListener?.onItemClick(noteList[adapterPosition])
        }
    }

    interface OnItemClickListener {
        fun onItemClick(note: Note)
    }

}