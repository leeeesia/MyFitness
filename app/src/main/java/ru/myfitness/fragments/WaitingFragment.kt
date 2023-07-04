package ru.myfitness.fragments

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ru.myfitness.databinding.WaitingFragmentBinding
import ru.myfitness.utils.FragmentManager
import ru.myfitness.utils.TimeUtils

const val COUNT_DOWN_TIME = 11000L

class WaitingFragment : Fragment() {
    private lateinit var binding: WaitingFragmentBinding
    private var actionBar: ActionBar? = null
    private lateinit var timer: CountDownTimer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = WaitingFragmentBinding.inflate(inflater, container, false)
        actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.title = "Приготовьтесь"
        return binding.root
    }

    private fun startTimer() = with(binding) {
        timer = object : CountDownTimer(COUNT_DOWN_TIME, 1) {
            override fun onTick(restTime: Long) {
                tvTimer.text = TimeUtils.getTime(restTime)
                pbStart.progress = restTime.toInt()
            }

            override fun onFinish() {
                FragmentManager.setFragment(ExercisesFragment.newInstance(), activity as AppCompatActivity)
            }

        }.start()

    }

    override fun onDetach() {
        super.onDetach()
        timer.cancel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.pbStart.max = COUNT_DOWN_TIME.toInt()
        startTimer()
    }


    companion object {
        @JvmStatic
        fun newInstance() = WaitingFragment()

    }
}