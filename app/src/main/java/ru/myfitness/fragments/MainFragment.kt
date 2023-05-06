package ru.myfitness.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import ru.myfitness.adapters.DayModel
import ru.myfitness.adapters.DaysAdapter
import ru.myfitness.adapters.ExerciseAdapter
import ru.myfitness.databinding.FragmentDaysBinding
import ru.myfitness.utils.FragmentManager

class MainFragment : Fragment() {
    private lateinit var binding: FragmentDaysBinding
    private var actionBar: ActionBar? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentDaysBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.title = "Главная"
        init()

    }

    private fun init() = with(binding){
        planCardView.setOnClickListener {
            FragmentManager.setFragment(PlanFragment.newInstance(),
                activity as AppCompatActivity)
        }
        goExercise.setOnClickListener(){
            FragmentManager.setFragment(WaitingFragment.newInstance(),activity as AppCompatActivity)
        }
    }


    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()

    }
}