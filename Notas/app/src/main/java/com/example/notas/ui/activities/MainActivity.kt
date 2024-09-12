package com.example.notas.ui.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notas.R
import com.example.notas.models.Nota
import com.example.notas.ui.adapters.NoteAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), NoteAdapter.OnNoteClickListener {
    private val datalist = arrayListOf(
        Nota("Cosas que faltan", "Leche, Pan, Frutas"),
        Nota("Contraseña facebook", "ParaGUay069"),
        Nota("Texto de prueba", "Lorem ipsum dolor sit amet, consectetur adipiscing elit," +
                " sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim" +
                " veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat." +
                " Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat" +
                " nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui " +
                "officia deserunt mollit anim id est laborum."),
        Nota("Tarea 12 de sep", "Terminar la app de notas"),
        Nota("Recordatorio cumpleaños", "Hacerle fiesta el 24 de septiembre"),
        Nota("Contraseña ig", "Vi3tnam1ta$25"),
        Nota("Mejor casa", "Ravenclaw"),
        Nota("Hechizo de luz", "Lumos"),
        Nota("Antagonista perfecto", "Snape"),
    )
    private lateinit var rvNoteList: RecyclerView
    private lateinit var fabAddNote: FloatingActionButton
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        fabAddNote = findViewById(R.id.fabAddNote)
        rvNoteList = findViewById(R.id.rvNoteList)
        setupRecyclerView()
        setupEventListeners()
    }

    private fun setupEventListeners() {
        fabAddNote.setOnClickListener {
            buildAlertDialog()
        }
    }

    private fun setupRecyclerView() {
        rvNoteList.adapter = NoteAdapter(datalist, this)
        rvNoteList.layoutManager = LinearLayoutManager(this).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
    }

    private fun buildAlertDialog(nota: Nota? = null) {

        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Formulario de nota")


        val viewInflated: View = LayoutInflater.from(this)
            .inflate(R.layout.form_layout, null, false)

        val txtNewContactName: EditText = viewInflated.findViewById(R.id.txtNewNoteTitle)
        val txtNewContactPhone: EditText = viewInflated.findViewById(R.id.txtNewNoteMsg)
        //val txtNewContactR : EditText= viewInflated.findViewById(R.id.txtNewContactR)
        //val txtNewContactG : EditText= viewInflated.findViewById(R.id.txtNewContactG)
        //val txtNewContactB : EditText= viewInflated.findViewById(R.id.txtNewContactB)
        txtNewContactName.setText(nota?.titulo)
        txtNewContactPhone.setText(nota?.nota)
        builder.setView(viewInflated)

        builder.setPositiveButton(android.R.string.ok) { dialog, _ ->
            dialog.dismiss()
            val Titulo = txtNewContactName.text.toString()
            val Nota = txtNewContactPhone.text.toString()

            //val red = txtNewContactR.text.toString().toIntOrNull() ?: 0
            //val green = txtNewContactG.text.toString().toIntOrNull() ?: 0
            //val blue = txtNewContactB.text.toString().toIntOrNull() ?: 0

            if (nota != null) {
                nota.titulo = Titulo
                nota.nota = Nota
                nota.colorIndex = (nota.colorIndex + 1) % NoteAdapter.ColorGenerator.colors.size
                editNoteFromList(nota)
            } else {
                addNoteToList(Titulo, Nota)
            }
        }
        builder.setNegativeButton(android.R.string.cancel) { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }

    private fun editNoteFromList(nota: Nota) {
        val adapter = rvNoteList.adapter as NoteAdapter
        adapter.itemUpdated(nota)
    }

    private fun addNoteToList(titulo: String, Nota: String) {
        val nota = Nota(titulo, Nota)
        val adapter = rvNoteList.adapter as NoteAdapter
        adapter.itemAdded(nota)
    }

    override fun onNoteEditClickListener(nota: Nota) {
        buildAlertDialog(nota)
    }

    override fun onNoteDeleteClickListener(nota: Nota) {
        val adapter = rvNoteList.adapter as NoteAdapter
        adapter.itemDeleted(nota)
    }
}