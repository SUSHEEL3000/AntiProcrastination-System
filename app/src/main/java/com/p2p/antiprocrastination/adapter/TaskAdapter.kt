package com.p2p.antiprocrastination.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.p2p.antiprocrastination.databinding.ItemTaskBinding
import com.p2p.antiprocrastination.model.Task

class TaskAdapter(
    private val tasks: MutableList<Task>,
    private val onTaskChanged: (Task, Boolean) -> Unit,
    private val onTaskDeleted: (Int) -> Unit

) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(
        val binding: ItemTaskBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TaskViewHolder {

        val binding = ItemTaskBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: TaskViewHolder,
        position: Int
    ) {

        val task = tasks[position]

        holder.binding.tvTaskTitle.text = task.title

        holder.binding.cbTask.setOnCheckedChangeListener(null)

        holder.binding.cbTask.isChecked =
            task.isCompleted

        holder.binding.cbTask.setOnCheckedChangeListener { _, isChecked ->

            task.isCompleted = isChecked

            onTaskChanged.invoke(task, isChecked)
        }
        holder.binding.btnDeleteTask.setOnClickListener {

            onTaskDeleted.invoke(
                holder.adapterPosition
            )
        }
    }

    override fun getItemCount(): Int {
        return tasks.size
    }
}