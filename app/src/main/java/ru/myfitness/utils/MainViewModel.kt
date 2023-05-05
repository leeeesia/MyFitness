package ru.myfitness.utils

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.myfitness.adapters.DayModel
import ru.myfitness.adapters.ExerciseModel

class MainViewModel :ViewModel(){
    val mutableListExercise = MutableLiveData<ArrayList<ExerciseModel>>()

}