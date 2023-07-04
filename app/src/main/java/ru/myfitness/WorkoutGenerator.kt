package ru.myfitness

import android.content.Context
import org.apache.commons.math3.optim.linear.LinearConstraint
import org.apache.commons.math3.optim.linear.LinearConstraintSet
import org.apache.commons.math3.optim.linear.LinearObjectiveFunction
import org.apache.commons.math3.optim.linear.Relationship
import org.apache.commons.math3.optim.linear.SimplexSolver
import org.apache.commons.math3.optim.linear.UnboundedSolutionException
import ru.myfitness.adapters.ExerciseModel
import java.util.Random

open class WorkoutGenerator(private val context: Context) {
    val random = Random()
    val resources = context.resources
    var time = 900.0
    var day = 0
    internal lateinit var array: ArrayList<String>
    var maxPar = mutableListOf(
        100.0,
        100.0,
        100.0,
        100.0,
        100.0,
        100.0,
        100.0,
        100.0,
        100.0,
        100.0,
        100.0,
        100.0,
        100.0
    )


    var triceps = 90.0
    var breast = 90.0
    var pres = 90.0
    var booty = 90.0
    var legs = 90.0
    var back = 90.0
    var par = mutableListOf(
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0
    )


    //List<DayModel>
    fun generateTraining(): ArrayList<String> {
        val exercises: MutableList<ExerciseModel> = mutableListOf()
        val exerciseArray: MutableList<List<String>> = mutableListOf()
        resources.getStringArray(R.array.exercise).toList().forEachIndexed { index, s ->
            exerciseArray.add(s.split("|"))
        }
        exerciseArray.forEachIndexed { index, strings ->
            exercises.add(
                ExerciseModel(
                    strings[0],
                    time = strings[1],
                    muscle = strings[3].split("-"),
                    image = strings[2],
                    kkal = strings[4]
                )
            )
        }


        val objectiveCoefficients = doubleArrayOf(
            exerciseArray[0][4].toDouble(),
            exerciseArray[1][4].toDouble(),
            exerciseArray[2][4].toDouble(),
            exerciseArray[3][4].toDouble(),
            exerciseArray[4][4].toDouble(),
            exerciseArray[5][4].toDouble(),
            exerciseArray[6][4].toDouble(),
            exerciseArray[7][4].toDouble(),
            exerciseArray[8][4].toDouble(),
            exerciseArray[9][4].toDouble(),
            exerciseArray[10][4].toDouble(),
            exerciseArray[11][4].toDouble(),
            exerciseArray[12][4].toDouble(),
            exerciseArray[13][4].toDouble()
        )
        // Коэффициенты целевой функции
        val constraintCoefficients = arrayOf(
            doubleArrayOf(
                exercises.get(0).muscle[0].toDouble(),
                exercises.get(1).muscle[0].toDouble(),
                exercises.get(2).muscle[0].toDouble(),
                exercises.get(3).muscle[0].toDouble(),
                exercises.get(4).muscle[0].toDouble(),
                exercises.get(5).muscle[0].toDouble(),
                exercises.get(6).muscle[0].toDouble(),
                exercises.get(7).muscle[0].toDouble(),
                exercises.get(8).muscle[0].toDouble(),
                exercises.get(9).muscle[0].toDouble(),
                exercises.get(10).muscle[0].toDouble(),
                exercises.get(11).muscle[0].toDouble(),
                exercises.get(12).muscle[0].toDouble(),
                exercises.get(13).muscle[0].toDouble()
            ),  // Коэффициенты ограничений
            doubleArrayOf(
                exercises.get(0).muscle[1].toDouble(),
                exercises.get(1).muscle[1].toDouble(),
                exercises.get(2).muscle[1].toDouble(),
                exercises.get(3).muscle[1].toDouble(),
                exercises.get(4).muscle[1].toDouble(),
                exercises.get(5).muscle[1].toDouble(),
                exercises.get(6).muscle[1].toDouble(),
                exercises.get(7).muscle[1].toDouble(),
                exercises.get(8).muscle[1].toDouble(),
                exercises.get(9).muscle[1].toDouble(),
                exercises.get(10).muscle[1].toDouble(),
                exercises.get(11).muscle[1].toDouble(),
                exercises.get(12).muscle[1].toDouble(),
                exercises.get(13).muscle[1].toDouble()
            ),
            doubleArrayOf(
                exercises.get(0).muscle[2].toDouble(),
                exercises.get(1).muscle[2].toDouble(),
                exercises.get(2).muscle[2].toDouble(),
                exercises.get(3).muscle[2].toDouble(),
                exercises.get(4).muscle[2].toDouble(),
                exercises.get(5).muscle[2].toDouble(),
                exercises.get(6).muscle[2].toDouble(),
                exercises.get(7).muscle[2].toDouble(),
                exercises.get(8).muscle[2].toDouble(),
                exercises.get(9).muscle[2].toDouble(),
                exercises.get(10).muscle[2].toDouble(),
                exercises.get(11).muscle[2].toDouble(),
                exercises.get(12).muscle[2].toDouble(),
                exercises.get(13).muscle[2].toDouble()
            ),
            doubleArrayOf(
                exercises.get(0).muscle[3].toDouble(),
                exercises.get(1).muscle[3].toDouble(),
                exercises.get(2).muscle[3].toDouble(),
                exercises.get(3).muscle[3].toDouble(),
                exercises.get(4).muscle[3].toDouble(),
                exercises.get(5).muscle[3].toDouble(),
                exercises.get(6).muscle[3].toDouble(),
                exercises.get(7).muscle[3].toDouble(),
                exercises.get(8).muscle[3].toDouble(),
                exercises.get(9).muscle[3].toDouble(),
                exercises.get(10).muscle[3].toDouble(),
                exercises.get(11).muscle[3].toDouble(),
                exercises.get(12).muscle[3].toDouble(),
                exercises.get(13).muscle[3].toDouble()
            ),
            doubleArrayOf(
                exercises.get(0).muscle[4].toDouble(),
                exercises.get(1).muscle[4].toDouble(),
                exercises.get(2).muscle[4].toDouble(),
                exercises.get(3).muscle[4].toDouble(),
                exercises.get(4).muscle[4].toDouble(),
                exercises.get(5).muscle[4].toDouble(),
                exercises.get(6).muscle[4].toDouble(),
                exercises.get(7).muscle[4].toDouble(),
                exercises.get(8).muscle[4].toDouble(),
                exercises.get(9).muscle[4].toDouble(),
                exercises.get(10).muscle[4].toDouble(),
                exercises.get(11).muscle[4].toDouble(),
                exercises.get(12).muscle[4].toDouble(),
                exercises.get(13).muscle[4].toDouble()
            ),
            doubleArrayOf(
                exercises.get(0).muscle[5].toDouble(),
                exercises.get(1).muscle[5].toDouble(),
                exercises.get(2).muscle[5].toDouble(),
                exercises.get(3).muscle[5].toDouble(),
                exercises.get(4).muscle[5].toDouble(),
                exercises.get(5).muscle[5].toDouble(),
                exercises.get(6).muscle[5].toDouble(),
                exercises.get(7).muscle[5].toDouble(),
                exercises.get(8).muscle[5].toDouble(),
                exercises.get(9).muscle[5].toDouble(),
                exercises.get(10).muscle[5].toDouble(),
                exercises.get(11).muscle[5].toDouble(),
                exercises.get(12).muscle[5].toDouble(),
                exercises.get(13).muscle[5].toDouble()
            ),

            doubleArrayOf(
                exercises.get(0).time.toDouble(),
                exercises.get(1).time.toDouble(),
                exercises.get(2).time.toDouble(),
                exercises.get(3).time.toDouble(),
                exercises.get(4).time.toDouble(),
                exercises.get(5).time.toDouble(),
                exercises.get(6).time.toDouble(),
                exercises.get(7).time.toDouble(),
                exercises.get(8).time.toDouble(),
                exercises.get(9).time.toDouble(),
                exercises.get(10).time.toDouble(),
                exercises.get(11).time.toDouble(),
                exercises.get(12).time.toDouble(),
                exercises.get(13).time.toDouble()

            ),
            doubleArrayOf(1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
            doubleArrayOf(0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
            doubleArrayOf(0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
            doubleArrayOf(0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
            doubleArrayOf(0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
            doubleArrayOf(0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
            doubleArrayOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
            doubleArrayOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
            doubleArrayOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0),
            doubleArrayOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0),
            doubleArrayOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0),
            doubleArrayOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0),
            doubleArrayOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0),
            doubleArrayOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0),


            doubleArrayOf(1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
            doubleArrayOf(0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
            doubleArrayOf(0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
            doubleArrayOf(0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
            doubleArrayOf(0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
            doubleArrayOf(0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
            doubleArrayOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
            doubleArrayOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
            doubleArrayOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0),
            doubleArrayOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0),
            doubleArrayOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0),
            doubleArrayOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0),
            doubleArrayOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0),
            doubleArrayOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0)

        )
        val constraintRelationships =
            arrayOf(
                Relationship.GEQ,
                Relationship.GEQ,
                Relationship.GEQ,
                Relationship.GEQ,
                Relationship.GEQ,
                Relationship.GEQ,

                Relationship.EQ,

                Relationship.GEQ,
                Relationship.GEQ,
                Relationship.GEQ,
                Relationship.GEQ,
                Relationship.GEQ,
                Relationship.GEQ,
                Relationship.GEQ,
                Relationship.GEQ,
                Relationship.GEQ,
                Relationship.GEQ,
                Relationship.GEQ,
                Relationship.GEQ,
                Relationship.GEQ,
                Relationship.GEQ,

                Relationship.LEQ,
                Relationship.LEQ,
                Relationship.LEQ,
                Relationship.LEQ,
                Relationship.LEQ,
                Relationship.LEQ,
                Relationship.LEQ,
                Relationship.LEQ,
                Relationship.LEQ,
                Relationship.LEQ,
                Relationship.LEQ,
                Relationship.LEQ,
                Relationship.LEQ,
                Relationship.LEQ,
            ) // Отношения ограничений

        val constraintValues =
            doubleArrayOf(
                triceps,
                breast,
                pres,
                booty,
                legs,
                back,
                time,

                5.0,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,

                5.0,
                maxPar[0],
                maxPar[1],
                maxPar[2],
                maxPar[3],
                maxPar[4],
                maxPar[5],
                maxPar[6],
                maxPar[7],
                maxPar[8],
                maxPar[9],
                maxPar[10],
                maxPar[11],
                maxPar[12]
            ) // Правые части ограничений
        val solution = maximizeObjectiveFunction(
            objectiveCoefficients,
            constraintCoefficients,
            constraintRelationships,
            constraintValues
        )

        if (solution != null) {
            par.forEachIndexed { index, i ->
                par[index] = solution[index].toInt()
            }
            println("Оптимальное решение:")
            for (i in 0 until solution.size) {
                println("Оптимальное решение: x${i + 1} = ${solution[i].toInt()}")
            }
            array = fillPlan()
        } else {
            println("Не удалось найти оптимальное решение.")
        }
        //array[0] = "5,6,9,0,12,13,5,0,11,12"
        //array[1] = "12,13,5,0,5,6,9,0,11"
        //array[2] = "5,6,9,0,12,13,5,0,11,12,13"
        //array[3] = "12,13,5,0,5,6,9,0,10,6,5,0,10"
        //array[4] = "6,5,9,0,12,13,5,0,11,0,6,5"
        //array[5] = "5,6,10,0,5,6,9,0,10,6,5,9,11"

        return array
    }


    fun maximizeObjectiveFunction(
        objectiveCoefficients: DoubleArray,
        constraintCoefficients: Array<DoubleArray>,
        constraintRelationships: Array<Relationship>,
        constraintValues: DoubleArray,
    ): DoubleArray? {


        // Создание целевой функции
        val objectiveFunction = LinearObjectiveFunction(objectiveCoefficients, 0.0)

        // Создание ограничений
        val constraints = Array(constraintCoefficients.size) { index ->
            LinearConstraint(
                constraintCoefficients[index],
                constraintRelationships[index],
                constraintValues[index]
            )
        }

        // Решение задачи линейного программирования методом симплекса
        val solver = SimplexSolver()
        val constraintSet = LinearConstraintSet(*constraints)
        val solution = try {
            solver.optimize(objectiveFunction, constraintSet)
        } catch (e: UnboundedSolutionException) {
            null
        }

        // Получение оптимального решения (значений переменных)
        return solution?.point
    }

    private fun fillPlan(): ArrayList<String> {
        val planArray = ArrayList<String>()

        while (day > 0) {
            planArray.add(fillEx())
            day--
        }
        return planArray
    }

    private fun fillEx(): String {
        var ex = ""
        var p1 = par[0]
        var p2 = par[1]
        var p3 = par[2]
        var p4 = par[3]
        var p5 = par[4]
        var p6 = par[5]
        var p7 = par[6]
        var p8 = par[7]
        var p9 = par[8]
        var p10 = par[9]
        var p11 = par[10]
        var p12 = par[11]
        var p13 = par[12]
        var p14 = par[13]

        val exArray = mutableListOf(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14)


        while (exArray.isNotEmpty()) {
            var item = random.nextInt(exArray.size - 1)
            if (exArray[item] > 0) {
                ex = "$ex$item,"
                exArray[item]--
            }
            var count = 0
            for (i in exArray) {
                if (i == 0)
                    count++
            }
            if (count == exArray.size) {
                ex = ex.dropLast(1)
                exArray.clear()
            }
        }
        return ex
    }
}
