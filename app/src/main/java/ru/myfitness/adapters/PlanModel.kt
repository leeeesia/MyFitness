package ru.myfitness.adapters

data class PlanModel(
    var name: String = "",
    var last: Boolean = true,
    var isDone: Boolean = false,
    var planNumber: Int = 0,
    var array: Array<String>
)
