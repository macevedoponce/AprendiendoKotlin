package com.acevedo.todo

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.acevedo.todo.TaskCategory.*
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private val categories = listOf(
        Other,
        Business,
        Personal
    )

    private val tasks = mutableListOf(
        Task("PruebaBusiness", Business),
        Task("PruebaPersonal", Personal),
        Task("PruebaOther", Other)
    )

    private lateinit var rvCategorias:RecyclerView
    private lateinit var rvTasks:RecyclerView

    private lateinit var categoriesAdapter: CategoriesAdapter
    private lateinit var tasksAdapter: TasksAdapter

    private lateinit var fabAddTask: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initComponent()
        initUI()
        initListeners()
    }

    private fun initComponent() {
        rvCategorias = findViewById(R.id.rvCategories)
        rvTasks = findViewById(R.id.rvTasks)
        fabAddTask = findViewById(R.id.fabAddTask)
    }

    private fun initListeners(){
        fabAddTask.setOnClickListener { showDioalog() }
    }

    private fun showDioalog(){
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_task)

        val btnAddTask: Button = dialog.findViewById(R.id.btnAddTask)
        val edTask:EditText = dialog.findViewById(R.id.etTask)
        val rgCategories:RadioGroup = dialog.findViewById(R.id.rgCategories)

        btnAddTask.setOnClickListener {
            val currentTask = edTask.text.toString()
            if(currentTask.isNotEmpty()){ //validar que el edt no este vacio
                val selectedId = rgCategories.checkedRadioButtonId
                val selectedRadioButton:RadioButton = rgCategories.findViewById(selectedId)
                val currentCategory:TaskCategory = when(selectedRadioButton.text){
                    getString(R.string.business) -> Business
                    getString(R.string.personal)  -> Personal
                    else -> Other
                }
                tasks.add(Task(currentTask, currentCategory)) //agregar tarea a la lista
                updateTasks()
                dialog.hide()
            }
        }
        dialog.show()
    }

    private fun initUI() {
        categoriesAdapter = CategoriesAdapter(categories){ position -> updateCategories(position)}
        rvCategorias.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false)
        rvCategorias.adapter = categoriesAdapter

        tasksAdapter = TasksAdapter(tasks) {position -> onItemSelected(position)} //funcion lambda
        rvTasks.layoutManager = LinearLayoutManager(this)
        rvTasks.adapter = tasksAdapter

    }

    private fun updateCategories(position: Int){
        categories[position].isSelected = !categories[position].isSelected
        categoriesAdapter.notifyItemChanged(position)
        updateTasks()
    }

    private fun onItemSelected(position:Int){
        tasks[position].isSelected = !tasks[position].isSelected
        updateTasks()
    }

    private fun updateTasks(){

        val selectedCategories: List<TaskCategory> = categories.filter { it.isSelected }
        val newTasks = tasks.filter { selectedCategories.contains(it.category) }
        tasksAdapter.tasks = newTasks
        // avisar al adaptador de que existen nuevos items para mostrar
        tasksAdapter.notifyDataSetChanged()

    }
}