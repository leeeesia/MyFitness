package ru.myfitness.adapters

data class ExerciseModel(
    var name: String,
    var time: String = "0",
    var isDone: Boolean = false,
    var favorite: Boolean = true,
    var kkal: String,
    var muscle: List<String>,
    var image: String,
    var exNumber: Int = 0,
)