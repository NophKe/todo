package com.example.todo

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText
import java.io.File

class MainActivity : AppCompatActivity() {
    lateinit var add_but: Button
    lateinit var don_but: Button
    lateinit var ski_but: Button
    lateinit var tod_txt: TextView
    lateinit var tod_inp: TextInputEditText
    lateinit var file: File
    var todo_items = mutableListOf<String>()


    fun read_file(){
        if (this.file.exists()) {
            for (line in file.readLines()) {
                this.todo_items.add(line)
            }
        }
    }

    fun write_file() {
        if (! this.file.exists()) {
            this.file.createNewFile()
        }
        this.file.writeText("")
        for (line in this.todo_items) {
            this.file.appendText(line)
            this.file.appendText("\n")
        }
    }

    fun apply_txt(){
        this.tod_txt.text = this.todo_items.last()
        this.tod_inp.setText("")
        this.write_file()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        this.file = File( applicationContext.filesDir, "todo-list")
        this.read_file()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.add_but = findViewById<Button>(R.id.add_but)
        this.don_but = findViewById<Button>(R.id.don_but)
        this.ski_but = findViewById<Button>(R.id.ski_but)
        this.tod_txt = findViewById<TextView>(R.id.tod_txt)
        this.tod_inp = findViewById<TextInputEditText>(R.id.txt_input)
        this.apply_txt()

        this.don_but.setOnLongClickListener{
            if (this.todo_items.isEmpty()){
                this.todo_items.add("add something in the todo list")
                this.apply_txt()
            } else {
                this.todo_items.removeAt(this.todo_items.lastIndex)
                this.apply_txt()
                //if (! this.todo_items.isEmpty()) {
                //    this.apply_txt()
                //}
            }
            true
        }
        this.add_but.setOnClickListener{
            if (! this.tod_inp.text.isNullOrEmpty()){
                this.todo_items.add(this.tod_inp.text.toString())
                this.apply_txt()
                this.tod_inp.clearComposingText()
            }
        }
        this.ski_but.setOnClickListener{
            if (this.todo_items.lastIndex >= 1 ) {
                val item = this.todo_items[0]
                this.todo_items.removeAt(0)
                this.todo_items.add(item)
                this.apply_txt()
            }
        }
    }
}