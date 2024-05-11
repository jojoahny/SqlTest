package com.example.sqltest

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(var list: ArrayList<Tasks>,var requireContext: Context) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {
    class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val title: TextView = itemview.findViewById(R.id.title)
        val body: TextView = itemview.findViewById(R.id.body)
        val isDoneImg: ImageView = itemview.findViewById(R.id.isDoneImg)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_recycle, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text=list[position].title
        holder.body.text=list[position].body
        val DBHelper = DBHelper(requireContext)
        if (list[position].isDone==1){
            holder.isDoneImg.setImageResource(R.drawable.done)
        }else {
            holder.isDoneImg.setImageResource(R.drawable.notdone)
        }
        holder.itemView.setOnClickListener {
            var action=AllTasksDirections.actionAllTasksToAddTask2(
                list[position].id
            )
            Navigation.findNavController(it).navigate(action)
        }
        holder.itemView.setOnLongClickListener {
            DBHelper.deleteTask(Tasks(list[position].id, "jovany", "eadrfaeqrf",1))
            list.removeAt(position)
            notifyDataSetChanged()
            true
        }
    }

}