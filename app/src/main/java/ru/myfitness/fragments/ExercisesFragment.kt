package ru.myfitness.fragments

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import pl.droidsonroids.gif.GifDrawable
import ru.myfitness.adapters.ExerciseModel
import ru.myfitness.databinding.ExerciseBinding
import ru.myfitness.utils.FragmentManager
import ru.myfitness.utils.MainViewModel
import ru.myfitness.utils.TimeUtils

class ExercisesFragment : Fragment() {
    private lateinit var binding: ExerciseBinding
    private var timer: CountDownTimer? = null
    private var exerciseCounter = 0
    private var currentDay = 0
    private var exList: ArrayList<ExerciseModel>? = null
    private var actionBar: ActionBar? = null
    private val model: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = ExerciseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        actionBar = (activity as AppCompatActivity).supportActionBar
        exerciseCounter = model.getExerciseCount()
        currentDay = model.currentDay
        model.mutableListExercise.observe(viewLifecycleOwner) {
            exList = it
            nextExercise()
        }

        binding.bNext.setOnClickListener() {
            nextExercise()
        }

    }

    private fun nextExercise() {
        if (exerciseCounter < exList?.size!!) {
            val exercise = exList?.get(exerciseCounter++) ?: return
            showExercise(exercise)
            setExerciseType(exercise)
            showNextExercise()
        } else {
            exerciseCounter++
            FragmentManager.setFragment(DayFinishFragment.newInstance(),
                activity as AppCompatActivity)
        }
    }

    private fun showNextExercise() = with(binding) {
        if (exerciseCounter < exList?.size!!) {
            val exercise = exList?.get(exerciseCounter) ?: return
            imNext.setImageDrawable(GifDrawable(root.context.assets, exercise.image))
            tvNextName.text = "Далее " + exercise.name
        } else {
            Toast.makeText(activity, "Готово", Toast.LENGTH_LONG).show()
            tvNextName.text = "Это последнее упражнение"
        }
    }

    private fun showExercise(exercise: ExerciseModel) = with(binding) {
        tvName.text = exercise.name
        imMain.setImageDrawable(GifDrawable(root.context.assets, exercise.image))
        val title = "$exerciseCounter/${exList?.size}"
        actionBar?.title = title
        setExerciseType(exercise)
    }

    private fun setExerciseType(exercise: ExerciseModel) {
        if (exercise.time.startsWith("x")) {
            binding.tvTime.text = exercise.time
        } else {
            startTimer(exercise)
        }
    }

    private fun startTimer(exercise: ExerciseModel) = with(binding) {
        pbMain.max = exercise.time.toInt() * 1000
        timer?.cancel()
        timer = object : CountDownTimer(exercise.time.toLong() * 1000, 1) {
            override fun onTick(restTime: Long) {
                tvTime.text = TimeUtils.getTime(restTime)
                pbMain.progress = restTime.toInt()
            }

            override fun onFinish() {
                nextExercise()
            }

        }.start()

    }

    override fun onDetach() {
        super.onDetach()
        model.savePref(currentDay.toString(), exerciseCounter - 1)
        timer?.cancel()
    }

    companion object {
        @JvmStatic
        fun newInstance() = ExercisesFragment()

    }
}