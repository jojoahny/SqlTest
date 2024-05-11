package com.example.sqltest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.sqltest.databinding.FragmentAddTaskBinding

class AddTask : Fragment() {
    private var _binding: FragmentAddTaskBinding? = null
    private val binding get() = _binding!!
    val args: AddTaskArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val DBHelper = DBHelper(requireContext())
        var selectedTasks = DBHelper.readTask()
        var idSelected = args.idd
        var item: Tasks? = null
        var isDone: Int? = null
        for (i in 0..selectedTasks.size - 1) {
            if (selectedTasks[i].id == idSelected) {
                item = selectedTasks[i]
                break
            }
        }
        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            // Check which RadioButton is selected
            val radioButton = binding.root.findViewById<RadioButton>(checkedId)
            val selectedText = radioButton.text.toString()
            // Perform actions based on the selected RadioButton
            if (selectedText == "Done") {
                // Do something when "Done" RadioButton is selected
                isDone = 1
            } else if (selectedText == "False") {
                // Do something when "False" RadioButton is selected
                isDone = 0
            }
        }
        if (item != null && idSelected != null) {
            binding.titleText.setText(item?.title)
            binding.bodyText.setText(item?.body)
            if (item.isDone == 1) {
                binding.radioButtonDone.isChecked = true
                binding.radioButtonFalse.isChecked = false
            } else {
                binding.radioButtonFalse.isChecked = true
                binding.radioButtonDone.isChecked = false
            }
        } else {
            idSelected = 0
        }
        binding.addTask.setOnClickListener {
            if (binding.titleText.text.isEmpty()) {
                Toast.makeText(requireContext(), "Please Enter Title For Task", Toast.LENGTH_LONG)
                    .show()
            } else if (binding.titleText.text.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Please Enter Description For Task",
                    Toast.LENGTH_LONG
                ).show()
            } else if (isDone != 0 && isDone != 1) {
                Toast.makeText(
                    requireContext(),
                    "Please Select Task Done or False",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                if (idSelected == 0) {
                    DBHelper.insertTask(
                        Tasks(
                            idSelected,
                            binding.titleText.text.toString(),
                            binding.bodyText.text.toString(),
                            isDone!!
                        )
                    )
                } else {
                    DBHelper.updateTask(
                        Tasks(
                            idSelected,
                            binding.titleText.text.toString(),
                            binding.bodyText.text.toString(),
                            isDone!!
                        )
                    )
                }
                val action = AddTaskDirections.actionAddTask2ToAllTasks()
                findNavController().navigate(action)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

}