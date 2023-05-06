package ru.myfitness.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ru.myfitness.R
import ru.myfitness.databinding.DayFinishBinding
import ru.myfitness.utils.FragmentManager

class DayFinishFragment : Fragment() {
    private lateinit var binding: DayFinishBinding
    private var actionBar: ActionBar? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DayFinishBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.title = "Готово"
        binding.bDone.setOnClickListener(){
            FragmentManager.setFragment(MainFragment.newInstance(), activity as AppCompatActivity)
        }

    }


    companion object {
        @JvmStatic
        fun newInstance() = DayFinishFragment()

    }
}