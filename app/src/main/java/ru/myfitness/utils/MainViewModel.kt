package ru.myfitness.utils

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.myfitness.R
import ru.myfitness.adapters.DayModel
import ru.myfitness.adapters.ExerciseModel
import ru.myfitness.adapters.PlanModel

class MainViewModel :ViewModel(){
    val mutableListExercise = MutableLiveData<ArrayList<ExerciseModel>>()
    var pref: SharedPreferences? = null
    var currentDay = 0
    var currentPlan = 0
    var planArray = ArrayList<PlanModel>()

    fun savePref(key: String, value: Int){
        pref?.edit()?.putInt(key,value)?.apply()
    }

    fun getExerciseCount(): Int{
        return pref?.getInt(currentDay.toString(),0) ?: 0
    }
}