package ru.myfitness.fragments

import android.os.Bundle
import android.view.*
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
import ru.myfitness.utils.DialogManager
import ru.myfitness.utils.FragmentManager
import ru.myfitness.utils.MainViewModel

class PlanFragment : Fragment(), DaysAdapter.Listener {
    private lateinit var adapter: DaysAdapter
    private lateinit var binding: FragmentPlanBinding
    private var actionBar: ActionBar? = null
    private val model: MainViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentPlanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        return inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.reset) {
            DialogManager.showDialog(activity as AppCompatActivity,
                R.string.reset_msg,
                object : DialogManager.Listener {
                    override fun onClick() {
                        model.pref?.edit()?.clear()?.apply()
                        adapter.submitList(fillDaysArray())
                    }
                })
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model.currentDay = 0
        initRcView()
    }

    private fun fillDaysArray(): ArrayList<DayModel> {
        val tempArray = ArrayList<DayModel>()
        resources.getStringArray(R.array.day_exercises).forEach {
            model.currentDay++
            val exCounter = it.split(",").size
            tempArray.add(DayModel(it, 0, model.getExerciseCount() == exCounter))
        }
        var daysDoneCounter = 0
        binding.progressBar.max = tempArray.size
        tempArray.forEach {
            if (it.isDone) daysDoneCounter++
        }
        updateRestDaysUi(tempArray.size - daysDoneCounter, tempArray.size)
        return tempArray
    }

    private fun updateRestDaysUi(restDays: Int, days: Int) = with(binding) {
        val rDays = "Осталось $restDays"
        tvRestDays.text = rDays
        progressBar.progress = days
    }

    private fun initRcView() = with(binding) {
        adapter = DaysAdapter(this@PlanFragment)
        actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.title = "План тренировки"
        rcView.layoutManager = LinearLayoutManager(activity as AppCompatActivity)
        rcView.adapter = adapter
        adapter.submitList(fillDaysArray())
    }

    private fun fillExerciseList(day: DayModel) {
        val tempList = ArrayList<ExerciseModel>()
        day.exercises.split(",").forEach {
            val exerciseList = resources.getStringArray(R.array.exercise)
            val exercise = exerciseList[it.toInt()]
            val exerciseArray = exercise.split("|")
            tempList.add(ExerciseModel(exerciseArray[0], exerciseArray[1], false, exerciseArray[2]))
        }
        model.mutableListExercise.value = tempList
    }

    companion object {
        @JvmStatic
        fun newInstance() = PlanFragment()

    }

    override fun onClick(day: DayModel) {
        if (!day.isDone) {
            fillExerciseList (day)
            model.currentDay = day.dayNumber
            FragmentManager.setFragment(ExercisesListFragment.newInstance(),
                activity as AppCompatActivity)
        } else {
            DialogManager.showDialog(activity as AppCompatActivity,
                R.string.reset_msg,
                object : DialogManager.Listener {
                    override fun onClick() {
                        model.savePref(day.dayNumber.toString(), 0)
                        fillExerciseList(day)
                        model.currentDay = day.dayNumber
                        FragmentManager.setFragment(ExercisesListFragment.newInstance(),
                            activity as AppCompatActivity)
                    }
                })
        }
    }
}