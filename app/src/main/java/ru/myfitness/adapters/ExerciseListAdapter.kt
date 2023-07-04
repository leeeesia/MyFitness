package ru.myfitness.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import pl.droidsonroids.gif.GifDrawable
import ru.myfitness.R

import ru.myfitness.databinding.ExerciseItemBinding


class ExerciseListAdapter(var listener: ExerciseListAdapter.Listener) :
    ListAdapter<ExerciseModel, ExerciseListAdapter.ExerciseListHolder>(MyComparator()) {
    class ExerciseListHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ExerciseItemBinding.bind(view)
        fun setData(exercise: ExerciseModel, listener: ExerciseListAdapter.Listener) = with(binding) {
            exName.text = exercise.name
            exCheckBox.isChecked = exercise.favorite
            exImage.setImageDrawable(GifDrawable(root.context.assets, exercise.image))

            exCheckBox.setOnClickListener {
                listener.onClick(exercise.copy(favorite = exCheckBox.isChecked, exNumber = adapterPosition - 1))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseListHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.exercise_item, parent, false)
        return ExerciseListHolder(view)
    }

    override fun onBindViewHolder(holder: ExerciseListHolder, position: Int) {
        holder.setData(getItem(position), listener)
    }

    class MyComparator : DiffUtil.ItemCallback<ExerciseModel>() {
        override fun areItemsTheSame(oldItem: ExerciseModel, newItem: ExerciseModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ExerciseModel, newItem: ExerciseModel): Boolean {
            return oldItem == newItem
        }

    }
    interface Listener {
        fun onClick(ex: ExerciseModel)
    }
}