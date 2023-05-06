package ru.myfitness.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ru.myfitness.R
import ru.myfitness.adapters.DayModel
import ru.myfitness.adapters.DaysAdapter
import ru.myfitness.adapters.ExerciseModel
import ru.myfitness.databinding.FragmentPlanBinding
import ru.myfitness.utils.FragmentManager
import ru.myfitness.utils.MainViewModel

class PlanFragment : Fragment(), DaysAdapter.Listener {
    private lateinit var binding: FragmentPlanBinding
    private var actionBar: ActionBar? = null
    private val model: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentPlanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.title = "План тренировки"
        initRcView()
    }

    private fun fillDaysArray(): ArrayList<DayModel> {
        val tempArray = ArrayList<DayModel>()
        resources.getStringArray(R.array.day_exercises).forEach {
            tempArray.add(DayModel(it, 0, false))
        }
        return tempArray
    }

    private fun initRcView() = with(binding) {
        val adapter = DaysAdapter(this@PlanFragment)
        rcView.layoutManager = LinearLayoutManager(activity as AppCompatActivity)
        rcView.adapter = adapter
        adapter.submitList(fillDaysArray())
    }
    private fun fillExerciseList(day: DayModel){
        val tempList = ArrayList<ExerciseModel>()
        day.exercises.split(",").forEach{
            val exerciseList = resources.getStringArray(R.array.exercise)
            val exercise = exerciseList[it.toInt()]
            val exerciseArray = exercise.split("|")
            tempList.add(ExerciseModel(exerciseArray[0],exerciseArray[1],false,exerciseArray[2]))
        }
        model.mutableListExercise.value = tempList
    }

    companion object {
        @JvmStatic
        fun newInstance() = PlanFragment()

    }

    override fun onClick(day: DayModel) {
        fillExerciseList(day)
        model.currentDay = day.dayNumber
        FragmentManager.setFragment(ExercisesListFragment.newInstance(),
            activity as AppCompatActivity)
    }
}