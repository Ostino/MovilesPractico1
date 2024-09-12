package com.example.notas.ui.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notas.R
import com.example.notas.models.Nota

import kotlin.random.Random

class NoteAdapter(
    private val notaList: ArrayList<Nota>,
    private val listener: OnNoteClickListener
) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : NoteViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.note_item_layout, parent, false)
        return NoteViewHolder(view)
    }

    override fun getItemCount(): Int {
        return notaList.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(notaList[position], listener)
    }

    fun itemAdded(nota: Nota) {
        notaList.add(1, nota)
        notifyItemInserted(1)
    }

    fun itemDeleted(nota: Nota) {
        val index = notaList.indexOf(nota)
        notaList.removeAt(index)
        notifyItemRemoved(index)
    }

    fun itemUpdated(nota: Nota) {
        val index = notaList.indexOf(nota)
        notaList[index] = nota
        notifyItemChanged(index)
    }
    object ColorGenerator {
        val colors: List<Int> by lazy {
            List(10) {
                Color.argb(
                    255,
                    127+Random.nextInt(128),
                    127+Random.nextInt(128),
                    127+ Random.nextInt(128)
                )
            }
        }
    }
    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var lblNoteTitle = itemView.findViewById<TextView>(R.id.lblNoteItemTitle)
        private var lblNoteMsg = itemView.findViewById<TextView>(R.id.lblNoteItemMsg)
        private var btnEditNote = itemView.findViewById<ImageButton>(R.id.btnEditNote)
        private var btnDeleteNote =
            itemView.findViewById<ImageButton>(R.id.btnDeleteNote)
        fun bind(nota: Nota, listener: OnNoteClickListener) {
            lblNoteTitle.text = nota.titulo
            lblNoteMsg.text = nota.nota
            val color = ColorGenerator.colors[nota.colorIndex]
            lblNoteMsg.setBackgroundColor(color)
            lblNoteTitle.setBackgroundColor(color)

            btnEditNote.setOnClickListener {
                listener.onNoteEditClickListener(nota)
            }
            btnDeleteNote.setOnClickListener {
                listener.onNoteDeleteClickListener(nota)
            }
        }
    }

    public interface OnNoteClickListener {
        fun onNoteEditClickListener(nota: Nota)
        fun onNoteDeleteClickListener(nota: Nota)
    }
}