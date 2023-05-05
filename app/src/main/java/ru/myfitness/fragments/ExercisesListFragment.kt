package ru.myfitness.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ru.myfitness.adapters.DayModel
import ru.myfitness.adapters.ExerciseAdapter
import ru.myfitness.databinding.FragmentExercisesListBinding
import ru.myfitness.utils.FragmentManager
import ru.myfitness.utils.MainViewModel

class ExercisesListFragment : Fragment() {
    private lateinit var binding: FragmentExercisesListBinding
    private lateinit var adapter: ExerciseAdapter
    private val model: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentExercisesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun init() = with(binding){
        adapter = ExerciseAdapter()
        rcView.layoutManager = LinearLayoutManager(activity)
        rcView.adapter = adapter
        goExercise.setOnClickListener(){
            FragmentManager.setFragment(WaitingFragment.newInstance(),activity as AppCompatActivity)
        }

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        model.mutableListExercise.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }

    }



    companion object {
        @JvmStatic
        fun newInstance() = ExercisesListFragment()

    }
}