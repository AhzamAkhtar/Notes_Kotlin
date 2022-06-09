package com.example.android.notes_app_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), INOtesRVAdapter {
    lateinit var viewModel: NoteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recycleView: RecyclerView = findViewById(R.id.recycleView)
        recycleView.layoutManager = LinearLayoutManager(this)
        val adapter = NotesRVAdapter(this,this)
        recycleView.adapter = adapter

        viewModel = ViewModelProvider(this,
        ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)

        viewModel.allNotes.observe(this,
        Observer {list ->
            list?.let {
                adapter.updateList(it)
            }
        })

    }

    override fun onItemClikced(note: Note) {
        viewModel.deleteNote(note)
    }

    fun submitData(view: View) {
          val text: EditText = findViewById(R.id.input)
          val noteText = text.text.toString()
          if(noteText.isNotEmpty()){
              viewModel.insertNote(Note(noteText))
              Toast.makeText(this,"created $noteText",Toast.LENGTH_LONG).show()
          }


    }
}