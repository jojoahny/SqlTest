package com.example.sqltest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sqltest.databinding.FragmentAllTasksBinding

class AllTasks : Fragment() {
    private var _binding: FragmentAllTasksBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val DBHelper = DBHelper(requireContext())
//        DBHelper.insertTask(Tasks(1, "john", "qweqwe",0))
//        DBHelper.insertTask(Tasks(2, "fgdf", "ewrew",1))
//        DBHelper.updateTask(Tasks(1, "jovany", "eadrfaeqrf",1))
        var Task = DBHelper.readTask()
//        DBHelper.deleteTask(Tasks(1, "jovany", "eadrfaeqrf",1))
        if (Task!=null){
            binding.noTasks.isVisible=false
        }
        binding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.recyclerView.adapter = CustomAdapter(Task, requireContext())
        binding.floatingActionButton.setOnClickListener {
            var action = AllTasksDirections.actionAllTasksToAddTask2(0)
            findNavController().navigate(action)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAllTasksBinding.inflate(inflater, container, false)
        return binding.root
    }

}