package ru.myfitness.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ru.myfitness.R
import ru.myfitness.WorkoutGenerator
import ru.myfitness.adapters.DaysAdapter
import ru.myfitness.adapters.ExerciseListAdapter
import ru.myfitness.adapters.ExerciseModel
import ru.myfitness.adapters.PlanModel
import ru.myfitness.databinding.QuestionsFragmentBinding
import ru.myfitness.utils.DialogManager
import ru.myfitness.utils.FragmentManager
import ru.myfitness.utils.MainViewModel

open class QuestionsFragment : Fragment(), ExerciseListAdapter.Listener {
    private lateinit var binding: QuestionsFragmentBinding
    private lateinit var adapter: ExerciseListAdapter
    private var actionBar: ActionBar? = null
    private val model: MainViewModel by activityViewModels()
    private lateinit var context: Context
    private lateinit var generator: WorkoutGenerator
    internal open lateinit var array : Array<String>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = QuestionsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun init() = with(binding) {
        adapter = ExerciseListAdapter(this@QuestionsFragment)
        rcView.layoutManager = LinearLayoutManager(activity)
        rcView.adapter = adapter
        adapter.submitList(fillExerciseList())
        done.setOnClickListener() {
            if (etTime.text.toString().toDouble() <= 10.0) {
                generator.booty -= 80.0
                generator.legs -= 80.0
                generator.triceps -= 80.0
                generator.breast -= 80.0
                generator.back -= 80.0
                generator.pres -= 80.0
            }
            generator.time = etTime.text.toString().toDouble() * 60
            generator.day = etDay.text.toString().toInt()
            when (true) {
                cbBooty.isChecked -> {
                    generator.booty += 30.0
                    generator.legs += 15.0
                }
                cbLegs.isChecked -> {
                    generator.booty += 15.0
                    generator.legs += 30.0
                }
                cbHands.isChecked -> {
                    generator.triceps += 30.0
                    generator.breast += 10.0
                    generator.back += 10.0
                }
                cbAll.isChecked -> {
                    generator.booty += 10.0
                    generator.legs += 10.0
                    generator.triceps += 10.0
                    generator.breast += 10.0
                }
                cbPres.isChecked -> {
                    generator.pres += 30.0
                    generator.breast += 14.0
                }
                else -> {}
            }
            var get = generator.generateTraining()
            array = get.toTypedArray()
            model.planArray.add(PlanModel(name = "Мой план", array = array, planNumber = model.currentPlan++))
            FragmentManager.setFragment(MainFragment.newInstance(array), activity as AppCompatActivity)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.title = "Создание тренировки"
        context = requireContext()
        generator = WorkoutGenerator(context)

        init()
    }


    private fun fillExerciseList(): ArrayList<ExerciseModel> {
        val tempList = ArrayList<ExerciseModel>()
        val ex = resources.getStringArray(R.array.exercises)[0]

        ex.split(",").forEach {
            val exerciseList = resources.getStringArray(R.array.exercise)
            val exercise = exerciseList[it.toInt()]
            val exerciseArray = exercise.split("|")
            tempList.add(
                ExerciseModel(
                    name = exerciseArray[0],
                    time = exerciseArray[1],
                    kkal = exerciseArray[4],
                    image = exerciseArray[2],
                    muscle = exerciseArray[3].split("-")
                )
            )
        }

        return tempList
    }


    companion object {
        @JvmStatic
        fun newInstance() = QuestionsFragment()

    }

    override fun onClick(ex: ExerciseModel) {
        if (ex.favorite) {
            generator.maxPar[ex.exNumber] = 100.0
        } else {
            generator.maxPar[ex.exNumber] = 0.0
        }
    }
}