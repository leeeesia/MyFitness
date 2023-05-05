package ru.myfitness.fragments

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ru.myfitness.R
import ru.myfitness.adapters.DayModel
import ru.myfitness.adapters.ExerciseAdapter
import ru.myfitness.adapters.ExerciseModel
import ru.myfitness.databinding.ExerciseBinding
import ru.myfitness.databinding.FragmentExercisesListBinding
import ru.myfitness.utils.FragmentManager
import ru.myfitness.utils.MainViewModel
import ru.myfitness.utils.TimeUtils

class ExercisesFragment : Fragment() {
    private lateinit var binding: ExerciseBinding
    private var timer: CountDownTimer? = null
    private var exerciseCounter = 0
    private var exList: ArrayList<ExerciseModel>? = null
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
        model.mutableListExercise.observe(viewLifecycleOwner) {
            exList = it
            nextExercise()
        }
        binding.bNext.setOnClickListener(){
            nextExercise()
        }

    }

    private fun nextExercise() {
        if (exerciseCounter < exList?.size!!) {
            val exercise = exList?.get(exerciseCounter++) ?: return
            showExercise(exercise)
            setExerciseType(exercise)
        } else {
            Toast.makeText(activity,"Done", Toast.LENGTH_LONG).show()
        }
    }

    private fun showExercise(exercise: ExerciseModel) = with(binding) {
        tvName.text = exercise.name
    }

    private fun setExerciseType(exercise: ExerciseModel) {
        if (exercise.time.startsWith("x")){
            binding.tvTime.text = exercise.time
        }else{
            startTimer(exercise)
        }
    }
    private fun startTimer(exercise: ExerciseModel) = with(binding) {
        pbMain.max = exercise.time.toInt()*1000
        timer?.cancel()
        timer = object : CountDownTimer(exercise.time.toLong()*1000, 1) {
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
        timer?.cancel()
    }

    companion object {
        @JvmStatic
        fun newInstance() = ExercisesFragment()

    }
}