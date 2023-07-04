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
import ru.myfitness.adapters.PlanAdapter
import ru.myfitness.adapters.PlanModel
import ru.myfitness.databinding.FragmentMainBinding
import ru.myfitness.utils.FragmentManager
import ru.myfitness.utils.MainViewModel

class MainFragment(array: Array<String>) : Fragment(), PlanAdapter.Listener {
    private lateinit var binding: FragmentMainBinding
    private lateinit var adapter: PlanAdapter
    private var actionBar: ActionBar? = null
    private val model: MainViewModel by activityViewModels()
    private lateinit var context: Context
    private lateinit var generator: WorkoutGenerator
    var array: Array<String> = array


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context = requireContext()
        generator = WorkoutGenerator(context)
        actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.title = "Главная"
        model.currentPlan = 0
        var standart = resources.getStringArray(R.array.day_exercises)
        if (model.planArray.isEmpty()) model.planArray.add(
            PlanModel(
                name = "Стандартный",
                array = standart,
                planNumber = 1
            )
        )
        model.currentPlan++
        initRcView()
        init()
    }

    private fun initRcView() = with(binding) {
        adapter = PlanAdapter(this@MainFragment)
        rcView.layoutManager = LinearLayoutManager(activity as AppCompatActivity)
        rcView.adapter = adapter
        adapter.submitList(model.planArray)
    }


    private fun init() = with(binding) {
        goExercise.setOnClickListener() {
            FragmentManager.setFragment(
                QuestionsFragment.newInstance(),
                activity as AppCompatActivity
            )
        }
    }




    companion object {
        @JvmStatic
        fun newInstance(array: Array<String>) = MainFragment(array)

    }

    override fun onClick(plan: PlanModel) {
        model.currentPlan = plan.planNumber
        FragmentManager.setFragment(
            PlanFragment.newInstance(plan.array),
            activity as AppCompatActivity
        )

    }
}
